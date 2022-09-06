package me.squid.eoncore.messaging;

import com.google.gson.Gson;
import me.squid.eoncore.EonCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Collectors;

import static me.squid.eoncore.utils.Utils.translateHex;

public enum EonPrefix {
    NATIONS,
    MODERATION,
    INFO;

    public static Map<EonPrefix, Component> mapping() {
        String prefixesString = prefixesString();
        Prefixes prefixes = new Gson().fromJson(prefixesString, Prefixes.class);
        Map<String, String> unprocessedPrefixes = prefixes.prefixes();
        return unprocessedPrefixes.entrySet().stream()
                .collect(Collectors.toMap(entry -> fromString(entry.getKey()),
                        entry -> transformPrefix(entry.getValue())));
    }

    private static EonPrefix fromString(String prefix) {
        return EonPrefix.valueOf(prefix.toUpperCase());
    }

    private static Component transformPrefix(String prefix) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(prefix);
    }

    private static String prefixesString() {
        try( InputStream stream = EonCore.class.getResourceAsStream("/prefixes.json") ) {
            return new String(stream.readAllBytes());
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public static String getPrefix(EonPrefix prefix) {
        String translated = "Invalid";
        switch (prefix) {
            case NATIONS -> translated = translateHex("#7f7f7f[#66b2ffEon Nations#7f7f7f] ");
            case MODERATION -> translated = translateHex("#7f7f7f[#66b2ffEon Moderation#7f7f7f] ");
        }
        return translated;
    }

    public static String bukkitPrefix(EonPrefix prefix) {
        String translated = "";
        switch (prefix) {
            case NATIONS -> translated = "§8[§9Eon Nations§8] ";
            case MODERATION -> translated = "§8[§9Eon Moderation§8] ";
        }
        return translated;
    }
}
