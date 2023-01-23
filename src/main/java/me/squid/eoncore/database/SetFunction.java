package me.squid.eoncore.database;

import java.util.UUID;
import java.util.function.Function;

@FunctionalInterface
public interface SetFunction {
    <T> void setPlayer(UUID uuid, T value, Function<T, String> toString);

    static SetFunction makeCommonSet(RedisClient client, String key) {
        return new SetFunction() {
            @Override
            public <T> void setPlayer(UUID uuid, T value, Function<T, String> toString) {
                client.setValue(uuid, key, value, toString);
            }
        };
    }
}
