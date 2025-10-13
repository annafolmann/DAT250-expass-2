package DAT250.model;

import jakarta.persistence.*;

@Entity
public class VoteOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;
    private int presentationOrder;

    // keep track of votes
    private int votes = 0;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    public VoteOption() {}

    public VoteOption(String caption, int order, Poll poll) {
        this.caption = caption;
        this.presentationOrder = order;
        this.poll = poll;
    }

    //getters and setters for votes
    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    // existing getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public int getPresentationOrder() { return presentationOrder; }
    public void setPresentationOrder(int presentationOrder) { this.presentationOrder = presentationOrder; }

    public Poll getPoll() { return poll; }
    public void setPoll(Poll poll) { this.poll = poll; }
}

