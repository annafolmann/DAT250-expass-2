package DAT250;

import DAT250.manager.PollManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisSubscriber {
    private PollManager pollManager;

    public RedisSubscriber(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    public void subscribeToPoll(String pollName) {
        new Thread(() -> {
            try (Jedis jedis = new Jedis("localhost", 6379)) {
                jedis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        System.out.println("Received message: " + message + " on " + channel);
                        pollManager.registerVote(channel, message);
                    }
                }, pollName);
            }
        }).start();
    }
}
