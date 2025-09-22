package DAT250.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant publishedAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "vote_option_id")
    private VoteOption votesOn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User votedBy;

    public Vote() {}

    public Vote(User user, VoteOption option) {
        this.votedBy = user;
        this.votesOn = option;
        this.publishedAt = Instant.now();
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Instant getPublishedAt() { return publishedAt; }
    public void setPublishedAt(Instant publishedAt) { this.publishedAt = publishedAt; }
    public VoteOption getVotesOn() { return votesOn; }
    public void setVotesOn(VoteOption votesOn) { this.votesOn = votesOn; }
    public User getVotedBy() { return votedBy; }
    public void setVotedBy(User votedBy) { this.votedBy = votedBy; }
}
