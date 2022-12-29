package xyz.xcrownn.fraymccore.Clans;

import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
import xyz.xcrownn.fraymccore.FrayMCCore;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayer;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayerUtil;

import java.io.File;
import java.util.*;

public class ClanUtil {

    private static FrayMCCore plugin;

    public ClanUtil(FrayMCCore plugin) {
        ClanUtil.plugin = plugin;
    }

    public static void saveClan(Clan clan) {
        Json clanFile = new Json(clan.getClanUUID().toString(), "plugins/FrayMC-Core/ClanStorage");
        clanFile.set("Clan_Name", clan.getName());
        clanFile.set("Clan_Owner", clan.getOwner());
        clanFile.set("Clan_Members", clan.getUserList());
        clanFile.set("Clan_Max_Players", clan.getMaxPlayersInClan());
        clanFile.set("Clan_Current_Player_Count", clan.getTotalPlayersInClan());
        clanFile.set("Clan_Level", clan.getClanLevel());
    }

    public static void deleteClan(Clan clan) {
        File file = new File(plugin.getDataFolder().getAbsolutePath() + clan.getClanUUID().toString() + ".json");
        // unset all players
        ArrayList<UUID> players = clan.getUserList();
        for (UUID s : players) {
            WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(s);
            wzPlayer.setInClan(false);
            wzPlayer.setClanUUID(null);
            wzPlayer.setInvite(null);
            WzPlayerUtil.savePlayer(wzPlayer);
        }

        ClanUtil.removeClanFromCacheList(clan.getClanUUID());

        // delete the file
        String cName = clan.getName();
        if (file.delete()) {
            plugin.getLogger().info("Clan '" + cName + "' has been deleted!");
        } else plugin.getLogger().warning("An error has occurred while trying to delete a clan!");
    }

    public static Clan getClan(UUID uuid) {

        Json clanFile = new Json(uuid.toString(), "plugins/FrayMC-Core/ClanStorage");
        ArrayList<String> stringArrayList = new ArrayList<>(clanFile.getStringList(uuid + "Clan_Members"));
        ArrayList<UUID> playerList = new ArrayList<>();
        for (String s : stringArrayList) {
            playerList.add(UUID.fromString(s));
        }

        return new Clan(
                uuid,
                clanFile.getString(uuid + "Clan_Name"),
                UUID.fromString(clanFile.getString("Clan_Owner")),
                playerList,
                clanFile.getInt(uuid + "Clan_Max_Players"),
                clanFile.getInt(uuid + ".Clan_Curent"),
                clanFile.getInt(uuid + "Clan_Level")
        );
    }

    public static void addPlayerToClan(Clan clan, UUID uuid) {
        // check if the clan is full
        if (clan.getTotalPlayersInClan() < clan.getMaxPlayersInClan()) {

            // there is space in the clan, get the list and add the player
            ArrayList<UUID> list = clan.getUserList();
            list.add(uuid);

            // get the wzPlayer and update their stats
            WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(uuid);
            wzPlayer.setClanUUID(clan.getClanUUID());
            wzPlayer.setInvite(null);
            wzPlayer.setInClan(true);

            // save the clan and player
            saveClan(clan);
            WzPlayerUtil.savePlayer(wzPlayer);

        }
    }

    public static void removePlayerFromClan(Clan clan, UUID uuid) {
        ArrayList<UUID> list = clan.getUserList();


        // check if the player is in the clan
        if (list.contains(uuid)) {

            // if the player leaving is the last player in the clan
            if (clan.getTotalPlayersInClan() > 1) {

                // if the player leaving is the clan owner transfer ownership
                if (clan.getOwner() == uuid) {

                    UUID rand = randomClanPlayer(clan);

                    if (randomClanPlayer(clan) == clan.getOwner()) rand = randomClanPlayer(clan);

                    transferOwnership(clan, rand);
                }

            } else deleteClan(clan);

            // there is space in the clan, get the list and add the player
            list.remove(uuid);

            // get the wzPlayer and update their stats
            WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(uuid);
            wzPlayer.setClanUUID(null);
            wzPlayer.setInvite(null);
            wzPlayer.setInClan(false);

            // save the clan and player
            saveClan(clan);
            WzPlayerUtil.savePlayer(wzPlayer);

        }
    }

    public static void transferOwnership(Clan clan, UUID newOwner) {
        UUID ownerUUID = clan.getOwner();
        WzPlayer newOwnerWz = WzPlayerUtil.getWzPlayer(newOwner);

        if (clan.getTotalPlayersInClan() == 1) return;
        if (clan.getOwner() != ownerUUID) return;
        if (newOwnerWz.getClanUUID() != clan.getClanUUID()) return;
        if (newOwner == clan.getOwner()) return;

        clan.setOwner(newOwner);
        saveClan(clan);

    }

    public static UUID randomClanPlayer(Clan clan) {
        ArrayList<UUID> playerList = clan.getUserList();
        Random random = new Random();
        return playerList.get(random.nextInt(playerList.size()));
    }

    public static int getCreationCost() {
        Yaml config = new Yaml("Settings", "plugins/FrayMC-Core");
        return config.getInt("Clans.Clan_Creation_Cost");
    }

    public static HashMap<UUID, String> getAllClans() {
        Json cache = new Json("cache", "plugins/FrayMC-Core");
        HashMap<String, String> clans = (HashMap<String, String>) cache.getMap("MainClanList");
        HashMap<UUID, String> conversion = new HashMap<>();
        for (Map.Entry<String, String> poop : clans.entrySet()) {
            conversion.put(UUID.fromString(poop.getKey()), poop.getValue());
        }
        return conversion;
    }

    public static void removeClanFromCacheList(UUID clanUUID) {
        Json cache = new Json("cache", "plugins/FrayMC-Core");
        HashMap<UUID, String> clans = getAllClans();
        clans.remove(clanUUID);
        cache.set("MainClanList", clans);
    }

    public static void addClanToCacheList(UUID clanUUID, String name) {
        Json cache = new Json("cache", "plugins/FrayMC-Core");
        HashMap<UUID, String> clans = getAllClans();
        HashMap<String, String> conversion = new HashMap<>();
        for (Map.Entry<UUID, String> pop : clans.entrySet()) {
            conversion.put(pop.getKey().toString(), pop.getValue());
        }
        conversion.put(clanUUID.toString(), name);
        cache.set("MainClanList", conversion);
    }
}
