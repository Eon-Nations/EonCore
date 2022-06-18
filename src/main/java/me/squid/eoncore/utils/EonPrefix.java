package me.squid.eoncore.utils;

import static me.squid.eoncore.utils.Utils.translateHex;

public enum EonPrefix {
    NATIONS,
    ADMIN,
    MODERATION;

    public static String getPrefix(EonPrefix prefix) {
        String translated = "Invalid";
        switch (prefix) {
            case NATIONS -> translated = translateHex("#7f7f7f[#66b2ffEon Nations#7f7f7f] ");
            case ADMIN -> translated = translateHex("#7f7f7f[#66b2ffEon Admin#7f7f7f] ");
            case MODERATION -> translated = translateHex("#7f7f7f[#66b2ffEon Moderation#7f7f7f] ");
        }
        return translated;
    }

    public static String bukkitPrefix(EonPrefix prefix) {
        String translated = "";
        switch (prefix) {
            case NATIONS -> translated = "§8[§9Eon Nations§8] ";
            case ADMIN -> translated = "§8[§9Eon Admin§8] ";
            case MODERATION -> translated = "§8[§9Eon Moderation§8] ";
        }
        return translated;
    }
}
