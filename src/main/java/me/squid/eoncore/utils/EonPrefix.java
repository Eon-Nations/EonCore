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
}
