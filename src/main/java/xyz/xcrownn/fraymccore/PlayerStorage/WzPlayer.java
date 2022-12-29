package xyz.xcrownn.fraymccore.PlayerStorage;

import java.util.UUID;

public class WzPlayer {

    private UUID uuid;
    private long coins;
    private boolean inClan;
    private UUID clanUUID;
    private UUID invite;

    public WzPlayer(UUID uuid, long coins, boolean inClan, UUID clanUUID, UUID invite) {
        this.uuid = uuid;
        this.coins = coins;
        this.inClan = inClan;
        this.clanUUID = clanUUID;
        this.invite = invite;
    }

    public WzPlayer() {

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public boolean isInClan() {
        return inClan;
    }

    public void setInClan(boolean inClan) {
        this.inClan = inClan;
    }

    public UUID getClanUUID() {
        return clanUUID;
    }

    public void setClanUUID(UUID clanUUID) {
        this.clanUUID = clanUUID;
    }

    public UUID getInvite() {
        return invite;
    }

    public void setInvite(UUID invite) {
        this.invite = invite;
    }

    public void addCoins(long amountToAdd) {
        setCoins(getCoins() + amountToAdd);
    }

    public void removeCoins(long amountToRemove) {
        setCoins(getCoins() - amountToRemove);
    }

    @Override
    public String toString() {
        return "WzPlayer{" +
                "uuid=" + uuid +
                ", coins=" + coins +
                ", inClan=" + inClan +
                ", clanUUID=" + clanUUID +
                ", invite=" + invite +
                '}';
    }
}
