import { useEffect, useMemo, useState } from "react";

const BASE = "http://localhost:8080";

export default function App() {
  const [polls, setPolls] = useState([]);
  const [votes, setVotes] = useState([]); // GET /votes
  const [active, setActive] = useState(0);
  const [loading, setLoading] = useState(true);

  // load data
  async function loadPolls() {
    const r = await fetch(`${BASE}/polls`);
    if (!r.ok) throw new Error(`GET /polls -> ${r.status}`);
    const data = await r.json();
    setPolls(Array.isArray(data) ? data : []);
    if (data.length && active >= data.length) setActive(0);
  }

  async function loadVotes() {
    const r = await fetch(`${BASE}/votes`);
    if (!r.ok) throw new Error(`GET /votes -> ${r.status}`);
    const data = await r.json();
    setVotes(Array.isArray(data) ? data : []);
  }

  async function reloadAll() {
    setLoading(true);
    try {
      await Promise.all([loadPolls(), loadVotes()]);
    } catch (e) {
      console.error(e);
      alert("Kunne ikke hente data fra serveren");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    reloadAll();
  }, []);

  // create poll
  async function addPoll(question, optionTexts) {
    try {
      const r = await fetch(`${BASE}/polls`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          question,
          options: optionTexts
            .map((t) => t.trim())
            .filter(Boolean)
            .map((t, i) => ({ caption: t, presentationOrder: i })), // <-- caption!
        }),
      });
      if (!r.ok) {
        const msg = await r.text();
        throw new Error(`POST /polls -> ${r.status}\n${msg}`);
      }
      await reloadAll(); // some servers return 201/204 with no body
      setActive(0);
    } catch (e) {
      console.error(e);
      alert("Feil ved oppretting");
    }
  }

  // vote
  async function vote(pollIndex, optionIndex) {
    const poll = polls[pollIndex];
    const option = poll?.options?.[optionIndex];
    if (!poll || !option) return;

    try {
      const r = await fetch(`${BASE}/votes`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ voteOption: { id: option.id } }), // backend expects Vote with nested VoteOption
      });
      if (!r.ok) {
        const txt = await r.text();
        throw new Error(`POST /votes -> ${r.status}\n${txt}`);
      }
      await loadVotes(); // refresh counts
    } catch (e) {
      console.error(e);
      alert("Feil ved stemming");
    }
  }

  if (loading) return <p style={{ padding: 16 }}>Laster…</p>;
  const activePoll = polls[active] ?? null;

  // Build counts: optionId -> number of votes
  const counts = useMemo(() => {
    const m = new Map();
    for (const v of votes) {
      const id = v?.voteOption?.id;
      if (id == null) continue;
      m.set(id, (m.get(id) || 0) + 1);
    }
    return m;
  }, [votes]);

  return (
    <div style={{ padding: 16, fontFamily: "system-ui, sans-serif" }}>
      <h1>Polls</h1>

      <h2>Create poll</h2>
      <CreatePoll onCreate={addPoll} />

      <h2>Vote</h2>
      <label>
        Choose poll:{" "}
        <select value={active} onChange={(e) => setActive(Number(e.target.value))}>
          {polls.map((p, i) => (
            <option key={p.id ?? i} value={i}>
              {p.question}
            </option>
          ))}
        </select>
      </label>

      {activePoll && (
        <>
          <VotePoll poll={activePoll} onVote={(i) => vote(active, i)} />
          <h3>Results</h3>
          <ul>
            {activePoll.options?.map((o, i) => (
              <li key={o.id ?? i}>
                {(o.caption ?? "").toString()}: {counts.get(o.id) || 0}
              </li>
            ))}
          </ul>
        </>
      )}
    </div>
  );
}

/* create poll */
function CreatePoll({ onCreate }) {
  const [question, setQuestion] = useState("");
  const [options, setOptions] = useState(["", ""]);
  const canSubmit = question.trim() && options.filter((o) => o.trim()).length >= 2;

  const submit = (e) => {
    e.preventDefault();
    if (!canSubmit) return;
    onCreate(question, options);
    setQuestion("");
    setOptions(["", ""]);
  };

  return (
    <form onSubmit={submit} style={{ display: "grid", gap: 8, maxWidth: 520 }}>
      <input
        placeholder="Question…"
        value={question}
        onChange={(e) => setQuestion(e.target.value)}
      />
      <div>
        <div style={{ marginBottom: 6 }}>Options:</div>
        {options.map((o, i) => (
          <div key={i} style={{ display: "flex", gap: 6, marginBottom: 6 }}>
            <input
              placeholder={`Option ${i + 1}`}
              value={o}
              onChange={(e) =>
                setOptions(options.map((x, idx) => (idx === i ? e.target.value : x)))
              }
              style={{ flex: 1 }}
            />
            {options.length > 2 && (
              <button
                type="button"
                onClick={() => setOptions(options.filter((_, idx) => idx !== i))}
              >
                ✕
              </button>
            )}
          </div>
        ))}
        <button type="button" onClick={() => setOptions([...options, ""])}>
          + Add option
        </button>
      </div>
      <button type="submit" disabled={!canSubmit}>Create poll</button>
    </form>
  );
}

/* vote poll */
function VotePoll({ poll, onVote }) {
  const [selected, setSelected] = useState(null);

  const submit = (e) => {
    e.preventDefault();
    if (selected == null) return;
    onVote(selected);
    setSelected(null);
  };

  return (
    <form onSubmit={submit} style={{ marginTop: 12 }}>
      <p style={{ fontWeight: 600 }}>{poll.question}</p>
      {poll.options?.map((o, i) => (
        <label key={o.id ?? i} style={{ display: "block", marginBottom: 4 }}>
          <input
            type="radio"
            name="vote"
            checked={selected === i}
            onChange={() => setSelected(i)}
          />{" "}
          {(o.caption ?? "").toString()}
        </label>
      ))}
      <button type="submit" disabled={selected == null}>Vote</button>
    </form>
  );
}
