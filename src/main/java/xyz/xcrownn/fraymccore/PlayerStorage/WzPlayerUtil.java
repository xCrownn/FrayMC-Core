package xyz.xcrownn.fraymccore.PlayerStorage;

import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Objects;
import java.util.UUID;

public class WzPlayerUtil {

    private static final Yaml messages = new Yaml("Messages", "plugins/FrayMC-Core");

    public static void savePlayer(WzPlayer wzPlayer) {
        Json json = new Json(wzPlayer.getUuid().toString(), "plugins/FrayMC-Core/PlayerStorage");
        json.set("Coins", wzPlayer.getCoins());
        json.set("InClan", wzPlayer.isInClan());
        if (wzPlayer.getClanUUID() == null) json.set("ClanUUID", "null");
        else json.set("ClanUUID", wzPlayer.getClanUUID());
        if (wzPlayer.getInvite() == null) json.set("InviteFrom", "null");
        else json.set("InviteFrom", wzPlayer.getInvite());
    }

    public static WzPlayer getWzPlayer(UUID uuid) {
        WzPlayer wzPlayer = new WzPlayer();
        Json json = new Json(uuid.toString(), "plugins/FrayMC-Core/PlayerStorage");

        if (Objects.equals(json.getString("ClanUUID"), "null")) wzPlayer.setClanUUID(null);
        else wzPlayer.setClanUUID(UUID.fromString(json.getString("ClanUUID")));

        if (Objects.equals(json.getString("InviteFrom"), "null")) wzPlayer.setInvite(null);
        else wzPlayer.setInvite(UUID.fromString(json.getString("InviteFrom")));

        wzPlayer.setCoins(json.getLong("Coins"));
        wzPlayer.setInClan(json.getBoolean("InClan"));
        wzPlayer.setUuid(uuid);

        System.out.println(wzPlayer);

        return wzPlayer;
    }

    public static void addCoins(UUID uuid, long amountToAdd) {
        WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(uuid);
        wzPlayer.addCoins(amountToAdd);
        savePlayer(wzPlayer);
        Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lCoins&7: &6" + amountToAdd + " &fcoins have been added to your account&7!"));
    }

    public static void removeCoins(UUID uuid, long amountToRemove) {
        WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(uuid);
        if (wzPlayer.getCoins() >= amountToRemove) {
            wzPlayer.removeCoins(amountToRemove);
            savePlayer(wzPlayer);
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lCoins&7: &6" + amountToRemove + " &fcoins have been taken from your account&7!"));
        } else {
            Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lCoins&7: &fYou do not have enough coins to complete this transaction&7!"));
        }
    }
}
