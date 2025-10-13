package DAT250.manager;

import DAT250.model.Poll;
import DAT250.model.VoteOption;
import DAT250.model.User;

import java.util.HashMap;
import java.util.Map;

public class PollManager {
    // Store all polls in memory
    private Map<String, Poll> polls = new HashMap<>();

    // Create a new poll and store it
    public Poll createPoll(String question, User createdBy) {
        Poll poll = new Poll(question, createdBy);
        polls.put(question, poll);
        System.out.println("Created poll: " + poll.getQuestion());
        return poll;
    }

    // Register a vote for a given poll
    public void registerVote(String pollName, String optionText) {
        Poll poll = polls.get(pollName);
        if (poll != null) {
            // Find or create the vote option
            VoteOption option = poll.getOptions().stream()
                    .filter(opt -> opt.getCaption().equalsIgnoreCase(optionText))
                    .findFirst()
                    .orElseGet(() -> poll.addVoteOption(optionText));

            // Simulate vote count
            option.setVotes(option.getVotes() + 1);
            System.out.println("Vote registered: " + optionText + " (" + option.getVotes() + ")");
        } else {
            System.out.println("Poll not found: " + pollName);
        }
    }

    public Poll getPoll(String question) {
        return polls.get(question);
    }
}
//package DAT250.manager;

// import java.util.HashMap;
// import java.util.HashSet;
// import java.util.Map;
// import java.util.Optional;
// import java.util.Set;

// import org.springframework.stereotype.Component;

// import DAT250.model.Poll;
// import DAT250.model.User;
// import DAT250.model.Vote;
// import DAT250.model.VoteOption;

// @Component
//public class PollManager {
    // private final Map<Long, User> users = new HashMap<>();
    // private final Map<Long, Poll> polls = new HashMap<>();
    // private final Map<Long, Vote> votes = new HashMap<>();

    // private Long userIdCounter = 1L;
    // private Long pollIdCounter = 1L;
    // private Long voteIdCounter = 1L;
    // private Long voteOptionIdCounter = 1L;

    // public User addUser(User user) { ... }
    // public Optional<User> getUser(Long id) { ... }
    // public Map<Long, User> getAllUsers() { ... }

    // public Poll addPoll(Poll poll) { ... }
    // public Map<Long, Poll> getAllPolls() { ... }
    // public Poll getPoll(Long id) { ... }
    // public void removePoll(Long id) { ... }

    // public Vote addVote(Vote vote) { ... }
    // public Optional<Vote> getVote(Long id) { ... }
    // public Map<Long, Vote> getAllVotes() { ... }
    // public Vote updateVote(Long voteId, Vote updatedVote) { ... }
//}
