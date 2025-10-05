package DAT250;

import redis.clients.jedis.UnifiedJedis;
import java.util.Map;

public class RedisExperiment {

    public static void main(String[] args) {
        try (UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379")) {

            System.out.println("✅ Connected to Redis!");

            // basic key value
            jedis.set("user", "bob");
            System.out.println("user = " + jedis.get("user"));

            // set example: logged in users
            jedis.del("logged_in_users"); // clear old data
            jedis.sadd("logged_in_users", "alice", "bob");
            jedis.srem("logged_in_users", "alice");
            jedis.sadd("logged_in_users", "eve");
            System.out.println("Logged in users: " + jedis.smembers("logged_in_users"));

            // hash example: poll data 
            String pollKey = "poll:pineapple_pizza";
            jedis.del(pollKey);
            jedis.hset(pollKey, Map.of(
                "yes", "269",
                "no", "268",
                "neutral", "42"
            ));
            jedis.hincrBy(pollKey, "yes", 1); // one new "yes" vote
            System.out.println("Poll data: " + jedis.hgetAll(pollKey));

            // cache example
            String cacheKey = "poll_cache:pineapple_pizza";
            if (jedis.exists(cacheKey)) {
                System.out.println("Cache hit: " + jedis.hgetAll(cacheKey));
            } else {
                // simulate slow DB fetch
                Map<String, String> poll = Map.of("yes", "270", "no", "268", "neutral", "42");
                jedis.hset(cacheKey, poll);
                jedis.expire(cacheKey, 30); // cache expires in 30 seconds
                System.out.println("Cache miss → data loaded into cache: " + poll);
            }
        }
    }
}

