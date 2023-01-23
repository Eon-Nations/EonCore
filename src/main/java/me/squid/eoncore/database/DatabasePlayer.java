package me.squid.eoncore.database;

import java.util.UUID;

public record DatabasePlayer(UUID uuid, double exp, int votes, double balance, String job) {

    public static DatabasePlayer defaultPlayer(UUID uuid) {
        return new DatabasePlayer(uuid, 0.0, 0, 0.0, "miner");
    }
}
