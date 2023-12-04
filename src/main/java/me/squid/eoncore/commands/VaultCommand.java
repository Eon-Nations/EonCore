package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.eonnations.eonpluginapi.api.Alias;
import org.eonnations.eonpluginapi.api.Command;
import org.eonnations.eonpluginapi.api.database.Database;

@Command(name = "vault",
        usage = "/vault <player>",
        aliases = {@Alias(name = "pvault")})
public class VaultCommand extends EonCommand {
    Database database;

    public VaultCommand(EonCore plugin) {
        super(plugin);
        this.database = plugin.getService(Database.class);
    }

    @Override
    protected void execute(Player player, String[] args) {
        String vaultResult = database.playerVault(player.getUniqueId())
                .map(vault -> "Iron: " + vault.iron() + " Gold: " + vault.gold() + " Diamonds: " + vault.diamonds() + " Emeralds: " + vault.emeralds())
                .getOrElseGet(e -> "Error Message: " + e.getMessage() + " SQL Error Code: " + e.getErrorCode());
        player.sendMessage(Component.text(vaultResult));
    }
}
