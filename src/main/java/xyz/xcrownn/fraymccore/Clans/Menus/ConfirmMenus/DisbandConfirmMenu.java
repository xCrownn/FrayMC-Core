package xyz.xcrownn.fraymccore.Clans.Menus.ConfirmMenus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.xcrownn.fraymccore.Clans.Clan;
import xyz.xcrownn.fraymccore.Clans.ClanUtil;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayer;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayerUtil;
import xyz.xcrownn.fraymccore.Utils.MenuManager.Menu;
import xyz.xcrownn.fraymccore.Utils.MenuManager.PlayerMenuUtil;

public class DisbandConfirmMenu extends Menu {

    public DisbandConfirmMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', "&cDelete Clan? &7*&cCANNOT BE UNDONE&7*");
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(playerMenuUtil.getOwner().getUniqueId());
        Clan clan = ClanUtil.getClan(wzPlayer.getClanUUID());

        switch (event.getSlot()) {
            case 0,1,2,3 -> playerMenuUtil.getOwner().closeInventory();
            case 5,6,7,8 -> ClanUtil.deleteClan(clan);
        }

    }

    @Override
    public void setMenuItems() {

        ItemStack red = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta redMeta = red.getItemMeta();
        redMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cCancel"));
        red.setItemMeta(redMeta);

        ItemStack green = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta greenMeta = green.getItemMeta();
        greenMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aConfirm"));
        green.setItemMeta(greenMeta);

        for (int i = 0; i < 4; i++) {
            inventory.setItem(i, red);
        }

        for (int i = 5; i < 9; i++) {
            inventory.setItem(i, green);
        }

        inventory.setItem(4, makePaneFiller(Material.BLACK_STAINED_GLASS_PANE));

    }


}
