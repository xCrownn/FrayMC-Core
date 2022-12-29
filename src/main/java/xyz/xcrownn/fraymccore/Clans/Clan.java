package xyz.xcrownn.fraymccore.Clans;

import java.util.ArrayList;
import java.util.UUID;

public class Clan {

    private UUID clanUUID;
    private String name;
    private UUID owner;
    private ArrayList<UUID> userList;
    private int totalPlayersInClan;
    private int maxPlayersInClan;
    private int clanLevel;

    public Clan(UUID clanUUID, String name, UUID owner, ArrayList<UUID> userList, int totalPlayersInClan, int maxPlayersInClan, int clanLevel) {
        this.clanUUID = clanUUID;
        this.name = name;
        this.owner = owner;
        this.userList = userList;
        this.totalPlayersInClan = totalPlayersInClan;
        this.maxPlayersInClan = maxPlayersInClan;
        this.clanLevel = clanLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public ArrayList<UUID> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<UUID> userList) {
        this.userList = userList;
    }

    public int getTotalPlayersInClan() {
        return totalPlayersInClan;
    }

    public void setTotalPlayersInClan(int totalPlayersInClan) {
        this.totalPlayersInClan = totalPlayersInClan;
    }

    public int getMaxPlayersInClan() {
        return maxPlayersInClan;
    }

    public void setMaxPlayersInClan(int maxPlayersInClan) {
        this.maxPlayersInClan = maxPlayersInClan;
    }

    public int getClanLevel() {
        return clanLevel;
    }

    public void setClanLevel(int clanLevel) {
        this.clanLevel = clanLevel;
    }

    public UUID getClanUUID() {
        return clanUUID;
    }

    public void setClanUUID(UUID clanUUID) {
        this.clanUUID = clanUUID;
    }

    @Override
    public String toString() {
        return "Clan{" +
                "clanUUID=" + clanUUID +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", userList=" + userList +
                ", totalPlayersInClan=" + totalPlayersInClan +
                ", maxPlayersInClan=" + maxPlayersInClan +
                ", clanLevel=" + clanLevel +
                '}';
    }
}
