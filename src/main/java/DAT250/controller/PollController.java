package DAT250.controller;

import java.util.Collection;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DAT250.manager.PollManager;
import DAT250.model.Poll;

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/polls")
public class PollController {
    
    private final PollManager pollManager;

    public PollController(PollManager pollManager) {
        this.pollManager = pollManager;
    }
    @PostMapping
    public Poll createPoll(@RequestBody Poll poll) {
        return pollManager.addPoll(poll);
    }

    @GetMapping
    public Collection<Poll> getAllPolls() {
        return pollManager.getAllPolls().values();
    }

    @GetMapping("/{id}") 
    public Poll getPoll(@PathVariable Long id) {
        return pollManager.getPoll(id);
    }

    @DeleteMapping("/{id}")
    public void removePoll(@PathVariable Long id) {
        pollManager.removePoll(id);
    }
}