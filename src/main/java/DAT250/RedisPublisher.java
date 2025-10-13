package DAT250;

import redis.clients.jedis.Jedis;

public class RedisPublisher {
    private Jedis jedis = new Jedis("localhost", 6379);

    public void publishVote(String pollName, String message) {
        jedis.publish(pollName, message);
        System.out.println("Published vote: " + message + " on poll: " + pollName);
    }
}
