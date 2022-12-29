package xyz.xcrownn.fraymccore.Clans.Menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.xcrownn.fraymccore.Clans.Clan;
import xyz.xcrownn.fraymccore.Clans.ClanUtil;
import xyz.xcrownn.fraymccore.Clans.Menus.ConfirmMenus.DisbandConfirmMenu;
import xyz.xcrownn.fraymccore.Clans.Menus.OwnerSubMenus.KickPlayerMenu;
import xyz.xcrownn.fraymccore.Clans.Menus.OwnerSubMenus.TransferMenu;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayer;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayerUtil;
import xyz.xcrownn.fraymccore.Utils.MenuManager.Menu;
import xyz.xcrownn.fraymccore.Utils.MenuManager.PlayerMenuUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class ClanSettingsMenu extends Menu {

    public ClanSettingsMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', "&7Clan Management Panel");
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

        WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(playerMenuUtil.getOwner().getUniqueId());
        Clan clan = ClanUtil.getClan(wzPlayer.getClanUUID());

        switch (event.getSlot()) {

            case 0 -> new ClanRegularMenu(playerMenuUtil).open();

            case 11 -> {
                // kick player - open a menu with all the players and what ever player they click they confirm to remove from the clan
                if (clan.getTotalPlayersInClan() > 1) new KickPlayerMenu(playerMenuUtil).open();
            }


            case 13 -> {
                // transfer clan - open a menu with all the players and what ever player they click will become the owner of the clan
                if (clan.getTotalPlayersInClan() > 1) new TransferMenu(playerMenuUtil).open();
            }

            case 15 -> {
                // disband clan - open a confirm menu, delete the clan, and ya done.
                new DisbandConfirmMenu(playerMenuUtil).open();
            }
        }

    }

    @Override
    public void setMenuItems() {

        WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(playerMenuUtil.getOwner().getUniqueId());
        Clan clan = ClanUtil.getClan(wzPlayer.getClanUUID());

        ItemStack arrow = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta arrowMeta = arrow.getItemMeta();
        arrowMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "Back to clan menu"));
        arrow.setItemMeta(arrowMeta);

        inventory.setItem(0, makeItem(Material.ARROW, "&7Back to clan menu"));

        if (clan.getTotalPlayersInClan() > 1) inventory.setItem(11, makeItem(Material.DARK_OAK_DOOR, "&7Kick a player"));
        else {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&cThere aren't enough players to kick any!"));
            inventory.setItem(11, makeItemWithLore(Material.DARK_OAK_DOOR, "&7Kick a player", lore));
        }

        if (clan.getTotalPlayersInClan() > 1) inventory.setItem(13, makeItem(Material.MINECART, "&7Transfer your clan to another player."));
        else {
            ArrayList<String> lore1 = new ArrayList<>();
            lore1.add(ChatColor.translateAlternateColorCodes('&', "&cThere aren't enough players to transfer your clan!"));
            inventory.setItem(13, makeItemWithLore(Material.MINECART, "&7Transfer your clan to another player.", lore1));
        }
        inventory.setItem(15, makeItem(Material.TNT, "&7Disband the clan &c*CANNOT BE UNDONE!*"));

    }
}
