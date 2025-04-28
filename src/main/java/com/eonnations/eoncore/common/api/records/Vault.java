package com.eonnations.eoncore.common.api.records;

public record Vault(int id, int copper, int iron, int gold, int diamonds, int emeralds) {

    public static Vault getDefault() {
        return new Vault(-1, 0, 0, 0, 0, 0);
    }

    public int getAmount(String resource) {
        return switch (resource) {
            case "copper" -> copper;
            case "iron" -> iron;
            case "gold" -> gold;
            case "diamond" -> diamonds;
            case "emerald" -> emeralds;
            default -> 0;
        };
    }

    public int getAmountFromPlaceholder(String resource) {
        return getAmount(resource.replace("<", "").replace(">", ""));
    }

    public enum ResourceType {
        COPPER, IRON, GOLD, DIAMOND, EMERALD
    }
}
