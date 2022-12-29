package xyz.xcrownn.fraymccore.Clans.Commands;

import de.leonhard.storage.Yaml;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.xcrownn.fraymccore.Clans.Clan;
import xyz.xcrownn.fraymccore.Clans.ClanUtil;
import xyz.xcrownn.fraymccore.Clans.Menus.ClanCreateMenu;
import xyz.xcrownn.fraymccore.Clans.Menus.ClanRegularMenu;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayer;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayerUtil;
import xyz.xcrownn.fraymccore.Utils.MenuManager.PlayerMenuUtil;

public class ClanCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // clan - opens clan menu.
        // clan invite <player> - invites a player.
        // clan accept - accepts an invitation.
        // clan create - opens the clan builder menu
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Yaml messages = new Yaml("Messages", "plugins/FrayMC-Core");
            WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(player.getUniqueId());
            if (!label.equalsIgnoreCase("clan")) return true;
            if (args.length > 0) {
                System.out.println("poop");

                switch (args[0]) {
                    case "invite" -> {
                        if (!wzPlayer.isInClan()) break;
                        if (Bukkit.getPlayer(args[1]) == null) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Clan.INVALID_PLAYER_INVITE")));
                            break;
                        }
                        Player invTarget = Bukkit.getPlayer(args[1]);
                        WzPlayer wzInvTarget = WzPlayerUtil.getWzPlayer(invTarget.getUniqueId());
                        if (wzInvTarget.isInClan()) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Clan.PLAYER_IS_ALREADY_IN_CLAN")));
                            break;
                        }
                        invTarget.sendMessage(PlaceholderAPI.setPlaceholders(invTarget, messages.getString("Clan.INVITE_RECEIVED")));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Clan.INVITE_SENT")));
                        wzInvTarget.setInvite(player.getUniqueId());
                        WzPlayerUtil.savePlayer(wzInvTarget);
                    }
                    case "accept" -> {
                        if (!wzPlayer.isInClan()) break;
                        WzPlayer wzPlayerAccept = WzPlayerUtil.getWzPlayer(player.getUniqueId());
                        if (wzPlayerAccept.getInvite() != null) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Clan.NO_INVITE")));
                            break;
                        }
                        Clan clan1 = ClanUtil.getClan(wzPlayerAccept.getClanUUID());
                        ClanUtil.addPlayerToClan(clan1, player.getUniqueId());
                        player.sendMessage(PlaceholderAPI.setPlaceholders(player, messages.getString("Clan.CLAN_ACCEPT_MESSAGE")));
                    }
                    case "create" -> {
                        if (wzPlayer.isInClan()) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Clan.CANT_CREATE_CLAN_IN_CLAN")));
                            break;
                        }
                        if (wzPlayer.getCoins() < ClanUtil.getCreationCost()) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Clan.CANT_CREATE_CLAN_NOT_ENOUGH_COINS")));
                            break;
                        }
                        // player is not in a clan and has enough coins. open the creation inventory
                        new ClanCreateMenu(new PlayerMenuUtil(player)).open();
                    }
                }
            } else {
                if (!wzPlayer.isInClan()) player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.getString("Clan.NOT_IN_A_CLAN")));// open clan menu
                else new ClanRegularMenu(new PlayerMenuUtil(player)).open();
            }
        } else sender.sendMessage("This is an in game command only!");
        return true;
    }
}