package xyz.xcrownn.fraymccore.Utils.MenuManager;

import org.bukkit.entity.Player;

public class PlayerMenuUtil {

    private Player owner;
    private int amount;

    public PlayerMenuUtil(Player player) {
        this.owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    public int getAmount() {
        return amount;
    }


}
