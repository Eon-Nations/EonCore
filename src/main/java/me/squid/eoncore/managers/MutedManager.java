package me.squid.eoncore.managers;

import me.squid.eoncore.EonCore;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.MetaNode;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MutedManager implements Listener {

    EonCore plugin;
    LuckPerms luckPerms;
    CooldownManager cooldownManager = new CooldownManager();

    public MutedManager(EonCore plugin) {
        this.plugin = plugin;
        this.luckPerms = LuckPermsProvider.get();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void savePlayer(UUID uuid) {
        if (hasMute(uuid)) {
            Cooldown cooldown = cooldownManager.getCooldown(uuid);
            luckPerms.getUserManager().modifyUser(uuid, user -> {
                MetaNode mutedNode = MetaNode.builder("muted", cooldown.toString()).build();
                user.data().clear(NodeType.META.predicate(node -> node.getMetaKey().equals("muted")));
                user.data().add(mutedNode);
            });
        }
    }

    public void loadPlayer(UUID uuid) {
        if (hasMute(uuid)) {
            luckPerms.getUserManager().loadUser(uuid).thenAcceptAsync(user -> {
                 MetaNode mutedNode = user.getNodes(NodeType.META).stream().filter(node -> node.getMetaKey().equals("muted"))
                         .findFirst().orElseThrow();
                 String[] cooldownString = mutedNode.getMetaValue().split(";");
                 Cooldown cooldown = new Cooldown(uuid, Long.parseLong(cooldownString[0]), Long.parseLong(cooldownString[1]));
                 cooldownManager.add(cooldown);
            });
        }
    }

    public void addCooldown(Cooldown cooldown) {
        cooldownManager.add(cooldown);
    }

    public void removePlayer(UUID uuid) {
        cooldownManager.remove(uuid);
        luckPerms.getUserManager().modifyUser(uuid,
                user -> user.data().clear(NodeType.META.predicate(node -> node.getMetaKey().equals("muted"))));
    }

    public Cooldown getCooldown(UUID uuid) {
        return cooldownManager.getCooldown(uuid);
    }

    public boolean hasCooldown(UUID uuid) {
        return cooldownManager.hasCooldown(uuid);
    }

    public List<UUID> getAllUUIDs() {
        return cooldownManager.getUUIDsFromCooldownMap();
    }

    public boolean hasMute(UUID uuid) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (player.isOnline()) {
            return cooldownManager.hasCooldown(uuid);
        } else {
            CompletableFuture<Boolean> boolFuture = luckPerms.getUserManager().loadUser(uuid)
                    .thenApplyAsync(user -> user.getNodes(NodeType.META).stream().anyMatch(node -> node.getMetaKey().equals("mute")));
            return boolFuture.join();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        loadPlayer(p.getUniqueId());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        Cooldown cooldown = getCooldown(uuid);
        if (cooldown != null && hasCooldown(uuid)) {
            savePlayer(uuid);
        } else if (cooldown != null && cooldown.isExpired()) {
            cooldownManager.remove(uuid);
        }
    }
}
