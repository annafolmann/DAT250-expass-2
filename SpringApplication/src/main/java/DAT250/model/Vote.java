package DAT250.model;

import java.time.Instant;
import DAT250.model.User;

public class Vote {
    private Long id;
    private Instant publishedAt;

    private VoteOption voteOption; // hver stemme har én option

    private User voter;

        public User getVoter() {
    return voter;
    }

    public void setVoter(User voter) {
    this.voter = voter;
    }

    public Vote() {}

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public VoteOption getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(VoteOption voteOption) {
        this.voteOption = voteOption;
    }
}
