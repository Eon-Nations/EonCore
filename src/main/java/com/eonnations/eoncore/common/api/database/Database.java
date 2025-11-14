package com.eonnations.eoncore.common.api.database;

import com.eonnations.eoncore.modules.node.Resource;

import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import com.eonnations.eoncore.common.api.records.*;
import org.bukkit.Location;

import java.sql.SQLException;
import java.util.UUID;

public interface Database {
    Either<SQLException, Vault> retrieveVault(int vaultId);

    Option<SQLException> removeVault(int vaultId);

    Either<SQLException, Integer> playerVaultId(UUID uuid);

    Option<SQLException> addResourceToPlayer(UUID uuid, String resource, int amount);

    List<String> getAllResources();

    Either<SQLException, Integer> createSpawn(int x, int y, int z);

    Either<SQLException, Integer> createSpawn(int x, int y, int z, float yaw, float pitch);

    Option<SQLException> createPlayer(UUID uuid, String username);

    Either<SQLException, Integer> levelUpPlayer(UUID uuid);

    Either<SQLException, Vault> playerVault(UUID uuid);

    Either<SQLException, EonPlayer> retrievePlayer(UUID uuid);

    Either<SQLException, EonPlayer> retrievePlayer(String username);

    Either<SQLException, Vault> createTown(String name, UUID uuid, Spawn spawn);

    Either<SQLException, Town> retrieveTown(String name);

    Option<SQLException> changeTownSpawn(String name, Spawn newSpawn);

    boolean deleteTown(String name);

    Either<SQLException, Integer> levelUpTown(String name);

    Option<SQLException> createNation(String name, String ownerTown);

    boolean deleteNation(String name);

    Either<SQLException, Nation> retrieveNation(String name);

    Either<SQLException, Integer> levelUpNation(String name);

    boolean addTownToNation(String nation, String town);

    boolean removeTownFromNation(String town);

    Option<SQLException> addNode(Location location, Resource resource, int outputRate);

    Option<SQLException> addNode(Location location, Resource resource, String townName, int outputRate);

    Option<SQLException> claimNode(Location location, String townName);

    Option<SQLException> addVote(UUID uuid, String website);
}
