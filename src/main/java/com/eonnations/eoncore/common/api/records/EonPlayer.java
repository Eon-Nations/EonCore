package com.eonnations.eoncore.common.api.records;

import java.util.UUID;

import io.vavr.control.Option;

public record EonPlayer(UUID uuid, int level, String donorRank, Option<String> townName, Vault vault) {

}
