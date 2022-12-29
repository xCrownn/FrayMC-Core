package xyz.xcrownn.fraymccore.Placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.xcrownn.fraymccore.Clans.Clan;
import xyz.xcrownn.fraymccore.Clans.ClanUtil;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayer;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayerUtil;

public class WzCoreExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "FrayMC-Core";
    }

    @Override
    public @NotNull String getAuthor() {
        return "xCrownn";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {

        if (player == null) return " ";

        if (params.equals("coins")) {
            return String.valueOf(WzPlayerUtil.getWzPlayer(player.getUniqueId()).getCoins());
        }

        if (params.equals("player_get_inviter")) {
            WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(player.getUniqueId());
            if (wzPlayer.getInvite() == null) return " ";
            return Bukkit.getPlayer(WzPlayerUtil.getWzPlayer(player.getUniqueId()).getInvite()).getName();
        }

        if (params.equals("player_get_clan_from_invite")) {
            WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(player.getUniqueId());
            if (wzPlayer.getInvite() == null) return " ";
            WzPlayer inviter = WzPlayerUtil.getWzPlayer(wzPlayer.getInvite());
            Clan clan = ClanUtil.getClan(inviter.getClanUUID());
            return clan.getName();
        }

        return null;
    }
}
