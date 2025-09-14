package DAT250.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import DAT250.model.Poll;
import DAT250.model.User;
import DAT250.model.Vote;
import DAT250.model.VoteOption;

@Component
public class PollManager {

    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, Poll> polls = new HashMap<>();
    private final Map<Long, Vote> votes = new HashMap<>();

    private Long userIdCounter = 1L;
    private Long pollIdCounter = 1L;
    private Long voteIdCounter = 1L;
    private Long voteOptionIdCounter = 1L;  // IDs for options

    // users

    public User addUser(User user) {
        user.setId(userIdCounter++);
        users.put(user.getId(), user);
        return user;
    }

    public Optional<User> getUser(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public Map<Long, User> getAllUsers() {
        return users;
    }

    // polls

    public Poll addPoll(Poll poll) {
        poll.setId(pollIdCounter++);
        if (poll.getOptions() != null) {
            for (int i = 0; i < poll.getOptions().size(); i++) {
                VoteOption opt = poll.getOptions().get(i);
                if (opt.getId() == null) {
                    opt.setId(voteOptionIdCounter++);
                }
                // Sett rekkefølge hvis ikke satt (0 brukes ofte som default-verdi)
                if (opt.getPresentationOrder() == 0) {
                    opt.setPresentationOrder(i);
                }
            }
        }
        polls.put(poll.getId(), poll);
        return poll;
    }

    public Map<Long, Poll> getAllPolls() {
        return polls;
    }

    public Poll getPoll(Long id) {
        return polls.get(id);
    }

    public void removePoll(Long id) {
        Poll removed = polls.remove(id);
        if (removed != null && removed.getOptions() != null) {
            // Fjern alle votes som peker på options i denne pollen
            Set<Long> optionIds = new HashSet<>();
            for (VoteOption opt : removed.getOptions()) {
                if (opt.getId() != null) {
                    optionIds.add(opt.getId());
                }
            }
            votes.values().removeIf(v ->
                v.getVoteOption() != null &&
                v.getVoteOption().getId() != null &&
                optionIds.contains(v.getVoteOption().getId())
            );
        }
    }

    // votes

    public Vote addVote(Vote vote) {
        vote.setId(voteIdCounter++);
        votes.put(vote.getId(), vote);
        return vote;
    }

    public Optional<Vote> getVote(Long id) {
        return Optional.ofNullable(votes.get(id));
    }

    public Map<Long, Vote> getAllVotes() {
        return votes;
    }

    public Vote updateVote(Long voteId, Vote updatedVote) {
        Vote currentVote = votes.get(voteId);
        if (currentVote != null) {
            currentVote.setVoteOption(updatedVote.getVoteOption());
            return currentVote;
        }
        return null;
    }
}

