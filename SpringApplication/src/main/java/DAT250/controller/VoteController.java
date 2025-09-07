package DAT250.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DAT250.manager.PollManager;
import DAT250.model.Vote;


@RestController
@RequestMapping("/votes")
public class VoteController {
    
    private final PollManager pollManager;

    public VoteController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @PostMapping
    public Vote createVote(@RequestBody Vote vote) {
        return pollManager.addVote(vote);
    }

    @GetMapping
    public Collection<Vote> getAllVotes() {
        return pollManager.getAllVotes().values();
    }

    @GetMapping("/{id}")
    public Vote getVote(@PathVariable Long id) {
        return pollManager.getVote(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Vote updateVote(@PathVariable Long id, @RequestBody Vote vote) {
        return pollManager.updateVote(id, vote);
    }
}

