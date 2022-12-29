package xyz.xcrownn.fraymccore.Clans.Menus.OwnerSubMenus;

import dev.dbassett.skullcreator.SkullCreator;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.xcrownn.fraymccore.Clans.Clan;
import xyz.xcrownn.fraymccore.Clans.ClanUtil;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayer;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayerUtil;
import xyz.xcrownn.fraymccore.Utils.MenuManager.Menu;
import xyz.xcrownn.fraymccore.Utils.MenuManager.PlayerMenuUtil;

import java.util.*;

public class TransferMenu extends Menu {


    HashMap<Integer, UUID> playerCache = new HashMap<>();

    public TransferMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', "&7Select the new owner.");
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

        int slot = 0;

        switch (event.getSlot()) {
            case 11,12,13,14,15,20,21,22,23,24,29,30,31,32,33 -> {

                slot = event.getSlot();

                ItemStack itemStack = event.getInventory().getItem(event.getSlot());
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemStack.setItemMeta(itemMeta);
                event.getClickedInventory().setItem(event.getSlot(), itemStack);
                playerMenuUtil.getOwner().updateInventory();

                if (itemStack.getItemMeta().hasEnchant(Enchantment.ARROW_DAMAGE)) {
                    // set the confirmation / cancel thing
                    event.getClickedInventory().clear(39);
                    event.getClickedInventory().setItem(event.getSlot(), makeItem(Material.RED_STAINED_GLASS_PANE, "&cCancel"));
                    event.getClickedInventory().clear(41);
                    event.getClickedInventory().setItem(event.getSlot(), makeItem(Material.GREEN_STAINED_GLASS_PANE, "&cConfirm"));
                }

            }

            case 39 -> {
                // cancel
                ItemStack itemStack = event.getInventory().getItem(event.getSlot());
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.removeEnchant(Enchantment.ARROW_DAMAGE);
                itemStack.setItemMeta(itemMeta);
                event.getClickedInventory().setItem(event.getSlot(), itemStack);
                event.getClickedInventory().setItem(39, makePaneFiller(Material.BLACK_STAINED_GLASS_PANE));
                event.getClickedInventory().setItem(41, makePaneFiller(Material.BLACK_STAINED_GLASS_PANE));
                playerMenuUtil.getOwner().updateInventory();



            }

            case 41 -> {

                if (slot == 0) return;
                WzPlayer player = WzPlayerUtil.getWzPlayer(playerCache.get(slot));
                Clan clan = ClanUtil.getClan(player.getClanUUID());
                ClanUtil.transferOwnership(clan, player.getUuid());

            }
        }

        // loop through each

    }

    @Override
    public void setMenuItems() {

        WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(playerMenuUtil.getOwner().getUniqueId());
        Clan clan = ClanUtil.getClan(wzPlayer.getClanUUID());

        List<Integer> black = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,16,17,18,19,25,26,27,28,34,35,36,37,38,40,42,43,44);
        for (Integer integer : black) {
            inventory.setItem(integer, makePaneFiller(Material.BLACK_STAINED_GLASS_PANE));
        }

        List<Integer> possibleSlots = Arrays.asList(11,12,13,14,15,20,21,22,23,24,29,30,31,32,33);
        if (clan.getTotalPlayersInClan() > 1) {
            ArrayList<UUID> playerList = clan.getUserList();

            for (Integer integer : possibleSlots) {
                for (UUID playerUUID : playerList) {
                    if (clan.getOwner() == playerUUID) continue;
                    Player playerFromList = Bukkit.getPlayer(playerUUID);

                    ItemStack playerThing = SkullCreator.itemFromUuid(wzPlayer.getUuid());
                    ItemMeta playerThingMeta = playerThing.getItemMeta();
                    assert playerFromList != null;
                    playerThingMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9" + playerFromList.getName()));
                    List<String> playerThingLore = Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&7Click to select this player."));
                    playerThingMeta.setLore(playerThingLore);
                    playerThing.setItemMeta(playerThingMeta);

                    playerCache.put(integer, playerUUID);
                    inventory.setItem(integer, playerThing);

                }
            }


        }
    }
}
