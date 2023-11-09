package me.squid.eoncore.messaging;

import com.google.gson.Gson;
import me.squid.eoncore.EonCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Collectors;

public enum EonPrefix {
    NATIONS,
    MODERATION,
    INFO,
    CURRENCY,
    COMMANDSPY;

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
        try (InputStream stream = EonCore.class.getResourceAsStream("/prefixes.json")) {
            return new String(stream.readAllBytes());
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
