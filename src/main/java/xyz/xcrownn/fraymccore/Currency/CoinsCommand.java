package xyz.xcrownn.fraymccore.Currency;

import de.leonhard.storage.Yaml;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayerUtil;

public class CoinsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Yaml messages = new Yaml("Messages", "plugins/FrayMC-Core");
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("coins")) {
            if (args.length == 3) {
                Player argPlayer;

                try {
                    argPlayer = Bukkit.getPlayer(args[1]);
                } catch (CommandException e) {
                    player.sendMessage(PlaceholderAPI.setPlaceholders(player, messages.getString("Coins.INVALID_PLAYER_ARGUMENT")));
                    return true;
                }

                long number;
                try {
                    number = Long.parseLong(args[2]);
                } catch (NumberFormatException e) {
                    player.sendMessage(PlaceholderAPI.setPlaceholders(player, messages.getString("Coins.INVALID_NUMBER_ARGUMENT")));
                    return true;
                }

                if (player.hasPermission("FrayCore.Admin") || player.isOp()) {
                    if (args[0].equalsIgnoreCase("add")) {

                        WzPlayerUtil.addCoins(argPlayer.getUniqueId(), number);

                    }
                    else if (args[0].equalsIgnoreCase("remove")) {

                        WzPlayerUtil.removeCoins(argPlayer.getUniqueId(), number);

                    }
                }

            } else player.sendMessage(PlaceholderAPI.setPlaceholders(player, messages.getString("Coins.CHECK_COIN_BALANCE")));
        }
        return true;
    }
}
