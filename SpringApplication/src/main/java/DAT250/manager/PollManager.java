package DAT250.manager;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import DAT250.model.Poll;
import DAT250.model.User;
import DAT250.model.Vote;

@Component
public class PollManager {
    
    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, Poll> polls = new HashMap<>();
    private final Map<Long, Vote> votes = new HashMap<>();

    private Long userIdCounter = 1L;
    private Long pollIdCounter = 1L;
    private Long voteIdCounter = 1L;

    // user
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

    // poll
    public Poll addPoll(Poll poll) {
        poll.setId(pollIdCounter++);
        polls.put(poll.getId(), poll);
        return poll;
    }

    public Poll getPoll(Long id) {
        return (polls.get(id));
    }

    public Map<Long, Poll> getAllPolls() {
        return polls;
    }

    // vote
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

    public void removePoll(Long id) {
        Poll removed = polls.remove(id);

        if (removed != null) {
            votes.values().removeIf(vote ->
            vote.getVoteOption() != null && removed.getOptions().stream().anyMatch(opt -> 
                opt.getCaption().equals(vote.getVoteOption().getCaption()))
            );
        }
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


