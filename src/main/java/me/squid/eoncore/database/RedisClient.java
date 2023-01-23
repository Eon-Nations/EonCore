package me.squid.eoncore.database;

import me.squid.eoncore.EonCore;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;
import java.util.function.Function;

public class RedisClient {
    private final JedisPool jedisPool;

    public RedisClient(EonCore plugin) {
        this.jedisPool = EconomySetup.setupPool(plugin);
        Runtime.getRuntime().addShutdownHook(new Thread(jedisPool::close));
    }

    public String stringKey(UUID uuid, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(uuid + key);
        }
    }

    public <T> T getKey(UUID uuid, String key, Function<String, T> transform) {
        try (Jedis jedis = jedisPool.getResource()) {
            String value = jedis.get(uuid + key);
            return transform.apply(value);
        }
    }

    public void setString(UUID uuid, String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(uuid + key, value);
        }
    }

    public <T> void setValue(UUID uuid, String key, T value, Function<T, String> transform) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(uuid + key, transform.apply(value));
        }
    }
}
