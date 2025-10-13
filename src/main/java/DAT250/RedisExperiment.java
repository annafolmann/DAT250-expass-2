package DAT250;

import DAT250.manager.PollManager;
import DAT250.model.Poll;
import DAT250.model.User;

public class RedisExperiment {

    public static void main(String[] args) throws InterruptedException {
        PollManager pollManager = new PollManager();
        RedisSubscriber subscriber = new RedisSubscriber(pollManager);
        RedisPublisher publisher = new RedisPublisher();

        User dummyUser = new User(); // just a placeholder user

        // create a new poll
        Poll poll = pollManager.createPoll("BestCoffee", dummyUser);

        // subscribe to that poll’s topic in Redis
        subscriber.subscribeToPoll(poll.getQuestion());

        // simulate some votes being published
        Thread.sleep(1000); // small delay so subscriber connects first
        publisher.publishVote(poll.getQuestion(), "Espresso");
        publisher.publishVote(poll.getQuestion(), "Latte");
        publisher.publishVote(poll.getQuestion(), "Espresso");

        Thread.sleep(2000);
        System.out.println("✅ Experiment finished for poll: " + poll.getQuestion());
    }
}
