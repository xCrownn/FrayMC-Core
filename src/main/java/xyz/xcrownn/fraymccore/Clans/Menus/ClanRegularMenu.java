package xyz.xcrownn.fraymccore.Clans.Menus;

import dev.dbassett.skullcreator.SkullCreator;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.xcrownn.fraymccore.Clans.Clan;
import xyz.xcrownn.fraymccore.Clans.ClanUtil;
import xyz.xcrownn.fraymccore.Clans.Menus.ConfirmMenus.LeaveConfirmMenu;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayer;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayerUtil;
import xyz.xcrownn.fraymccore.Utils.MenuManager.Menu;
import xyz.xcrownn.fraymccore.Utils.MenuManager.PlayerMenuUtil;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class ClanRegularMenu extends Menu {

    public static void main(String[] args) {

    }

    Player player = playerMenuUtil.getOwner();

    public ClanRegularMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    @Override
    public String getMenuName() {
        WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(player.getUniqueId());
        Clan clan = ClanUtil.getClan(wzPlayer.getClanUUID());
        System.out.println(clan.getName());
        return "&7Clan: &f" + clan.getName() + " &7Level: &f" + clan.getClanLevel();
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

        WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(player.getUniqueId());
        Clan clan = ClanUtil.getClan(wzPlayer.getClanUUID());

        if (event.getSlot() == 53) {
            if (event.getCurrentItem().getType() == Material.DARK_OAK_DOOR) new LeaveConfirmMenu(playerMenuUtil).open();
            else new ClanSettingsMenu(playerMenuUtil).open();
        }

    }

    @Override
    public void setMenuItems() {

        WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(player.getUniqueId());
        Clan clan = ClanUtil.getClan(wzPlayer.getClanUUID());
        Player ownerOfClan = Bukkit.getPlayer(clan.getOwner());

        ItemStack settingsItem = new ItemStack(Material.LEVER);
        ItemMeta settingsItemMeta = settingsItem.getItemMeta();
        settingsItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5Owner menu"));
        settingsItem.setItemMeta(settingsItemMeta);

        ItemStack leaveItem = new ItemStack(Material.DARK_OAK_DOOR);
        ItemMeta leaveItemMeta = leaveItem.getItemMeta();
        leaveItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cLeave clan"));
        leaveItem.setItemMeta(leaveItemMeta);

        if (clan.getOwner() == wzPlayer.getUuid()) inventory.setItem(53, settingsItem);
        else inventory.setItem(53, leaveItem);

        // Owner head
        ItemStack ownerHead = SkullCreator.itemFromUuid(wzPlayer.getUuid());
        ItemMeta ownerHeadMeta = ownerHead.getItemMeta();
        assert ownerOfClan != null;
        ownerHeadMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eOwner&7: " + ownerOfClan.getName()));
        List<String> ownerHeadLore = Arrays.asList(
                PlaceholderAPI.setPlaceholders(ownerOfClan, "&7Global rank: %warzone_global_ranking%"),
                PlaceholderAPI.setPlaceholders(ownerOfClan, "&7Level: %warzone_level%"),
                PlaceholderAPI.setPlaceholders(ownerOfClan, "&7KD/R: %warzone_kdr%"),
                PlaceholderAPI.setPlaceholders(ownerOfClan, "&7WL/R: %warzone_wlr%"));
        ownerHeadMeta.setLore(ownerHeadLore);
        ownerHead.setItemMeta(ownerHeadMeta);
        inventory.setItem(13, ownerHead);

        List<Integer> black = Arrays.asList(10,11,12,14,15,16,19,25,28,34,37,43,46,47,48,49,50,51,52);
        List<Integer> gray = Arrays.asList(0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45);

        for (Integer integer : black) {
            inventory.setItem(integer, makePaneFiller(Material.BLACK_STAINED_GLASS_PANE));
        }

        for (Integer integer : gray) {
            inventory.setItem(integer, makePaneFiller(Material.GRAY_STAINED_GLASS_PANE));
        }

        // Populate other players
        if (clan.getTotalPlayersInClan() > 1) {
            ArrayList<UUID> playerList = clan.getUserList();
            for (UUID playerUUID : playerList) {
                if (clan.getOwner() == playerUUID) continue;
                Player playerFromList = Bukkit.getPlayer(playerUUID);

                ItemStack playerThing = SkullCreator.itemFromUuid(wzPlayer.getUuid());
                ItemMeta playerThingMeta = playerThing.getItemMeta();
                assert playerFromList != null;
                playerThingMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Member&7: " + playerFromList.getName()));
                List<String> playerThingLore = Arrays.asList(
                        PlaceholderAPI.setPlaceholders(playerFromList, "&7Global rank: %warzone_global_ranking%"),
                        PlaceholderAPI.setPlaceholders(playerFromList, "&7Level: %warzone_level%"),
                        PlaceholderAPI.setPlaceholders(playerFromList, "&7KD/R: %warzone_kdr%"),
                        PlaceholderAPI.setPlaceholders(playerFromList, "&7WL/R: %warzone_wlr%"));
                playerThingMeta.setLore(playerThingLore);
                playerThing.setItemMeta(playerThingMeta);

                inventory.addItem(playerThing);

            }
        }
    }
}
