package com.eonnations.eoncore.common.api.records;


import java.util.UUID;

import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class EonPlayer {
    UUID uuid;
    int level;
    String donorRank;
    Option<String> townName;
    Vault vault; 
}
