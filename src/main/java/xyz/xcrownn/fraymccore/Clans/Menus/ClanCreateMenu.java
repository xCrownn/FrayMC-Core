package xyz.xcrownn.fraymccore.Clans.Menus;

import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
import dev.dbassett.skullcreator.SkullCreator;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.sound.Sound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.C;
import xyz.xcrownn.fraymccore.Clans.Clan;
import xyz.xcrownn.fraymccore.Clans.ClanUtil;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayer;
import xyz.xcrownn.fraymccore.PlayerStorage.WzPlayerUtil;
import xyz.xcrownn.fraymccore.Utils.MenuManager.Menu;
import xyz.xcrownn.fraymccore.Utils.MenuManager.PlayerMenuUtil;

import java.io.IOException;
import java.util.*;

public class ClanCreateMenu extends Menu {

    private int slotOne;
    private int slotTwo;
    private int slotThree;
    private int slotFour;
    private int slotFive;

    private ItemStack slotOneItem;
    private ItemStack slotTwoItem;
    private ItemStack slotThreeItem;
    private ItemStack slotFourItem;
    private ItemStack slotFiveItem;

    public ClanCreateMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', "&7Enter clan name!");
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

        Player player = playerMenuUtil.getOwner();

        switch (event.getSlot()) {


            // up slot one
            case 2 -> {
                if (slotOne == 25) slotOne = 0;
                else slotOne++;
                checkNameUsage(player);
                inventory.clear(11);
                inventory.setItem(11, SkullCreator.itemWithBase64(slotOneItem, getHeadDataFromList(slotOne)));
                player.updateInventory();
            }

            // down slot one
            case 20 -> {
                if (slotOne == 0) slotOne = 25;
                else slotOne--;
                checkNameUsage(player);
                inventory.clear(11);
                inventory.setItem(11, SkullCreator.itemWithBase64(slotOneItem, getHeadDataFromList(slotOne)));
                player.updateInventory();
            }

            // up slot two
            case 3 -> {
                if (slotTwo == 25) slotTwo = 0;
                else slotTwo++;
                checkNameUsage(player);
                inventory.clear(12);
                inventory.setItem(12, SkullCreator.itemWithBase64(slotTwoItem, getHeadDataFromList(slotTwo)));
                player.updateInventory();
            }

            // down slot two
            case 21 -> {
                if (slotTwo == 0) slotTwo = 25;
                else slotTwo--;
                checkNameUsage(player);
                inventory.clear(12);
                inventory.setItem(12, SkullCreator.itemWithBase64(slotTwoItem, getHeadDataFromList(slotTwo)));
                player.updateInventory();
            }

            // up slot three
            case 4 -> {
                if (slotThree == 25) slotThree = 0;
                else slotThree++;
                checkNameUsage(player);
                inventory.clear(13);
                inventory.setItem(13, SkullCreator.itemWithBase64(slotThreeItem, getHeadDataFromList(slotThree)));
                player.updateInventory();
            }

            // down slot three
            case 22 -> {
                if (slotThree == 0) slotThree = 25;
                else slotThree--;
                checkNameUsage(player);
                inventory.clear(13);
                inventory.setItem(13, SkullCreator.itemWithBase64(slotThreeItem, getHeadDataFromList(slotThree)));
                player.updateInventory();
            }

            // up slot four
            case 5 -> {
                if (slotFour == 25) slotFour = 0;
                else slotFour++;
                checkNameUsage(player);
                inventory.clear(14);
                inventory.setItem(14, SkullCreator.itemWithBase64(slotFourItem, getHeadDataFromList(slotFour)));
                player.updateInventory();
            }

            // down slot four
            case 23 -> {
                if (slotFour == 0) slotFour = 25;
                else slotFour--;
                checkNameUsage(player);
                inventory.clear(14);
                inventory.setItem(14, SkullCreator.itemWithBase64(slotFourItem, getHeadDataFromList(slotFour)));
                player.updateInventory();
            }

            // up slot five
            case 6 -> {
                if (slotFive == 25) slotFive = 0;
                else slotFive++;
                checkNameUsage(player);
                inventory.clear(15);
                inventory.setItem(15, SkullCreator.itemWithBase64(slotFiveItem, getHeadDataFromList(slotFive)));
                player.updateInventory();
            }

            // down slot five
            case 24 -> {
                if (slotFive == 0) slotFive = 25;
                else slotFive--;
                checkNameUsage(player);
                inventory.clear(15);
                inventory.setItem(15, SkullCreator.itemWithBase64(slotFiveItem, getHeadDataFromList(slotFive)));
                player.updateInventory();

            }

            case 10, 16 -> {
                System.out.println("Clicked 10 or 16 --- Below is the item the player clicked");
                // accept
                System.out.println(event.getCurrentItem());
                if (Objects.requireNonNull(event.getCurrentItem()).hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
                    System.out.println("Name is available and it caught the flag");
                    ArrayList<UUID> playerList = new ArrayList<>();
                    playerList.add(player.getUniqueId());
                    Clan clan = new Clan(UUID.randomUUID(), returnString(slotOne, slotTwo, slotThree, slotFour, slotFive), player.getUniqueId(), playerList, 1, 16, 0);
                    ClanUtil.addClanToCacheList(clan.getClanUUID(), clan.getName());
                    ClanUtil.saveClan(clan);

                    WzPlayer wzPlayer = WzPlayerUtil.getWzPlayer(player.getUniqueId());
                    wzPlayer.setInClan(true);
                    wzPlayer.setClanUUID(clan.getClanUUID());
                    wzPlayer.setInvite(null);
                    WzPlayerUtil.savePlayer(wzPlayer);




                    player.closeInventory();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have successfully created &7'&f" + clan.getName() + "&7'&a! "));
                }
            }

            // accept right
        }

    }

    @Override
    public void setMenuItems() {

        // Selection stuff
        ItemStack upArrow = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmNjYmY5ODgzZGQzNTlmZGYyMzg1YzkwYTQ1OWQ3Mzc3NjUzODJlYzQxMTdiMDQ4OTVhYzRkYzRiNjBmYyJ9fX0=");
        ItemMeta upMeta = upArrow.getItemMeta();
        upMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aUp"));
        upArrow.setItemMeta(upMeta);

        ItemStack downArrow = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzI0MzE5MTFmNDE3OGI0ZDJiNDEzYWE3ZjVjNzhhZTQ0NDdmZTkyNDY5NDNjMzFkZjMxMTYzYzBlMDQzZTBkNiJ9fX0=");
        ItemMeta downMeta = downArrow.getItemMeta();
        downMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aDown"));
        downArrow.setItemMeta(downMeta);

        for (int top = 2; top < 7; top++) {
            inventory.setItem(top, upArrow);
        }

        for (int pop = 20; pop < 25; pop++) {
            inventory.setItem(pop, downArrow);
        }

        // Default clan not available

        ItemStack red = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmViNTg4YjIxYTZmOThhZDFmZjRlMDg1YzU1MmRjYjA1MGVmYzljYWI0MjdmNDYwNDhmMThmYzgwMzQ3NWY3In19fQ==");
        ItemMeta redMeta = red.getItemMeta();
        redMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aThat name isn't available!"));
        red.setItemMeta(redMeta);

        inventory.setItem(10, red);
        inventory.setItem(16, red);

        // Alpha Selector

        List<Character> fastList = getAlphabet();

        slotOneItem = SkullCreator.itemFromBase64(getHeadDataFromList(slotOne));
        ItemMeta slotOneMeta = slotOneItem.getItemMeta();
        slotOneMeta.setDisplayName(String.valueOf(fastList.get(slotOne)));
        slotOneItem.setItemMeta(slotOneMeta);

        slotTwoItem = SkullCreator.itemFromBase64(getHeadDataFromList(slotTwo));
        ItemMeta slotTwoMeta = slotTwoItem.getItemMeta();
        slotTwoMeta.setDisplayName(String.valueOf(fastList.get(slotTwo)));
        slotTwoItem.setItemMeta(slotTwoMeta);

        slotThreeItem = SkullCreator.itemFromBase64(getHeadDataFromList(slotThree));
        ItemMeta slotThreeMeta = slotThreeItem.getItemMeta();
        slotThreeMeta.setDisplayName(String.valueOf(fastList.get(slotThree)));
        slotThreeItem.setItemMeta(slotThreeMeta);

        slotFourItem = SkullCreator.itemFromBase64(getHeadDataFromList(slotFour));
        ItemMeta slotFourMeta = slotFourItem.getItemMeta();
        slotFourMeta.setDisplayName(String.valueOf(fastList.get(slotFour)));
        slotFourItem.setItemMeta(slotFourMeta);

        slotFiveItem = SkullCreator.itemFromBase64(getHeadDataFromList(slotFive));
        ItemMeta slotFiveMeta = slotFiveItem.getItemMeta();
        slotFiveMeta.setDisplayName(String.valueOf(fastList.get(slotFive)));
        slotFiveItem.setItemMeta(slotFiveMeta);

        inventory.setItem(11, slotOneItem);
        inventory.setItem(12, slotTwoItem);
        inventory.setItem(13, slotThreeItem);
        inventory.setItem(14, slotFourItem);
        inventory.setItem(15, slotFiveItem);

        // Fillers

        List<Integer> fills = Arrays.asList(0,1,7,8,9,17,18,19,25,26);
        for (Integer integer : fills) {
            inventory.setItem(integer, makePaneFiller(Material.BLACK_STAINED_GLASS_PANE));
        }

    }


    public String getHeadDataFromList(int i) {
        LinkedHashMap<Character, String> list = getHeads();
        List<Character> alpha = getAlphabet();
        return list.get(alpha.get(i));
    }


    public String returnString(int one, int two, int three, int four, int five) {
        List<Character> fastList = getAlphabet();
        return fastList.get(one).toString() + fastList.get(one).toString() + fastList.get(two).toString() + fastList.get(three).toString() + fastList.get(four).toString() + fastList.get(five).toString();
    }

    public boolean isNameAvailable() {
        boolean isAvail = true;
        String clanName = returnString(slotOne, slotTwo, slotThree, slotFour, slotFive);
        HashMap<UUID, String> caList = ClanUtil.getAllClans();
        for (Map.Entry<UUID, String> entry : caList.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(clanName)) {
                isAvail = false;
                break;
            }
        }
        return isAvail;
    }

    public List<Character> getAlphabet() {
        return Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z');
    }

    public LinkedHashMap<Character, String> getHeads() {
        LinkedHashMap<Character, String> selectorItems = new LinkedHashMap<>();
        selectorItems.put('a', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTdkZDM0OTI0ZDJiNmEyMTNhNWVkNDZhZTU3ODNmOTUzNzNhOWVmNWNlNWM4OGY5ZDczNjcwNTk4M2I5NyJ9fX0=");
        selectorItems.put('b', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWVjYTk4YmVmZDBkN2VmY2E5YjExZWJmNGIyZGE0NTljYzE5YTM3ODExNGIzY2RkZTY3ZDQwNjdhZmI4OTYifX19");
        selectorItems.put('c', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTZiMTQ4NmUxZjU3NmJjOTIxYjhmOWY1OWZlNjEyMmNlNmNlOWRkNzBkNzVlMmM5MmZkYjhhYjk4OTdiNSJ9fX0=");
        selectorItems.put('d', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTlhYTY5MjI5ZmZkZmExODI4ODliZjMwOTdkMzIyMTVjMWIyMTU5ZDk4NzEwM2IxZDU4NDM2NDZmYWFjIn19fQ==");
        selectorItems.put('e', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2VkOWY0MzFhOTk3ZmNlMGQ4YmUxODQ0ZjYyMDkwYjE3ODNhYzU2OWM5ZDI3OTc1MjgzNDlkMzdjMjE1ZmNjIn19fQ==");
        selectorItems.put('f', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWQ3MTRiYWZiMGI1YWI5Y2ZhN2RiMDJlZmM4OTI3YWVkMWVmMjk3OTdhNTk1ZGEwNjZlZmM1YzNlZmRjOSJ9fX0=");
        selectorItems.put('g', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThjMzM2ZGVkZmUxOTdiNDM0YjVhYjY3OTg4Y2JlOWMyYzlmMjg1ZWMxODcxZmRkMWJhNDM0ODU1YiJ9fX0=");
        selectorItems.put('h', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmRlNGE4OWJlMjE5N2Y4NmQyZTYxNjZhMGFjNTQxY2NjMjFkY2UyOGI3ODU0Yjc4OGQzMjlhMzlkYWVjMzIifX19");
        selectorItems.put('i', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzE0OGE4ODY1YmM0YWZlMDc0N2YzNDE1MTM4Yjk2YmJiNGU4YmJiNzI2MWY0NWU1ZDExZDcyMTlmMzY4ZTQifX19");
        selectorItems.put('j', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMThjOWRjM2QzOGE1NjI4MmUxZDkyMzM3MTk4ZmIxOWVhNjQxYjYxYThjNGU1N2ZiNGUyN2MxYmE2YTRiMjRjIn19fQ==");
        selectorItems.put('k', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTJiZmViMjQ2ZjY0OWI4NmYyMTJmZWVhODdhOWMyMTZhNjU1NTY1ZDRiNzk5MmU4MDMyNmIzOTE4ZDkyM2JkIn19fQ==");
        selectorItems.put('l', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2M1ODMyMWQ0YmZmYmVjMmRkZjY2YmYzOGNmMmY5ZTlkZGYzZmEyZjEzODdkYzdkMzBjNjJiNGQwMTBjOCJ9fX0=");
        selectorItems.put('m', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTAzNzZkYzVlM2M5ODFiNTI5NjA1NzhhZmU0YmZjNDFjMTc3ODc4OWJjZDgwZWMyYzJkMmZkNDYwZTVhNTFhIn19fQ==");
        selectorItems.put('n', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjEyYzdhZmVhNDhlNTMzMjVlNTEyOTAzOGE0NWFlYzUxYWZlMjU2YWJjYTk0MWI2YmM4MjA2ZmFlMWNlZiJ9fX0=");
        selectorItems.put('o', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWMyNzIzNWRlM2E1NTQ2NmI2Mjc0NTlmMTIzMzU5NmFiNmEyMmM0MzVjZmM4OWE0NDU0YjQ3ZDMyYjE5OTQzMSJ9fX0=");
        selectorItems.put('p', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzU4NGRjN2VjZjM2YjRmMDQ0ZjgyNjI1Mjc5ODU3MThiZjI0YTlkYWVmMDEyZGU5MmUxZTc2ZDQ1ODZkOTYifX19");
        selectorItems.put('q', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmY3MmNjZWI0YTU2NTQ3OGRlNWIwYjBlNzI3OTQ2ZTU0OTgzNGUzNmY2ZTBlYzhmN2RkN2Y2MzI3YjE1YSJ9fX0=");
        selectorItems.put('r', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2NiODgyMjVlZTRhYjM5ZjdjYmY1ODFmMjJjYmYwOGJkY2MzMzg4NGYxZmY3NDc2ODkzMTI4NDE1MTZjMzQ1In19fQ==");
        selectorItems.put('s', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWYyMmQ3Y2Q1M2Q1YmZlNjFlYWZiYzJmYjFhYzk0NDQzZWVjMjRmNDU1MjkyMTM5YWM5ZmJkYjgzZDBkMDkifX19");
        selectorItems.put('t', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmMyZmNiYzI0ZTczODJhYzExMmJiMmMwZDVlY2EyN2U5ZjQ4ZmZjYTVhMTU3ZTUwMjYxN2E5NmQ2MzZmNWMzIn19fQ==");
        selectorItems.put('u', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWZkYzRmMzIxYzc4ZDY3NDg0MTM1YWU0NjRhZjRmZDkyNWJkNTdkNDU5MzgzYTRmZTlkMmY2MGEzNDMxYTc5In19fQ==");
        selectorItems.put('v', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmRkMDE0M2Q4ZTQ0OWFkMWJhOTdlMTk4MTcxMmNlZTBmM2ZjMjk3ZGJjMTdjODNiMDVlZWEzMzM4ZDY1OSJ9fX0=");
        selectorItems.put('w', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzljYmM0NjU1MjVlMTZhODk0NDFkNzg5YjcyZjU1NGU4ZmY0ZWE1YjM5MzQ0N2FlZjNmZjE5M2YwNDY1MDU4In19fQ==");
        selectorItems.put('x', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzM4YWIxNDU3NDdiNGJkMDljZTAzNTQzNTQ5NDhjZTY5ZmY2ZjQxZDllMDk4YzY4NDhiODBlMTg3ZTkxOSJ9fX0=");
        selectorItems.put('y', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTcxMDcxYmVmNzMzZjQ3NzAyMWIzMjkxZGMzZDQ3ZjBiZGYwYmUyZGExYjE2NWExMTlhOGZmMTU5NDU2NyJ9fX0=");
        selectorItems.put('z', "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzk5MmM3NTNiZjljNjI1ODUzY2UyYTBiN2IxNzRiODlhNmVjMjZiYjVjM2NjYjQ3M2I2YTIwMTI0OTYzMTIifX19");
        return selectorItems;
    }

    public void checkNameUsage(Player player) {

        ItemStack green = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta greenMeta = green.getItemMeta();
        greenMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aThat name is available! Click to create clan!"));
        green.setItemMeta(greenMeta);

        ItemStack red = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmViNTg4YjIxYTZmOThhZDFmZjRlMDg1YzU1MmRjYjA1MGVmYzljYWI0MjdmNDYwNDhmMThmYzgwMzQ3NWY3In19fQ==");
        ItemMeta redMeta = red.getItemMeta();
        redMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aThat name isn't available!"));
        red.setItemMeta(redMeta);

        ItemStack itemStack = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTkyZTMxZmZiNTljOTBhYjA4ZmM5ZGMxZmUyNjgwMjAzNWEzYTQ3YzQyZmVlNjM0MjNiY2RiNDI2MmVjYjliNiJ9fX0=");
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aClick to create your clan!"));
        itemStack.setItemMeta(itemMeta);

        System.out.println(ClanUtil.getAllClans());
        if (ClanUtil.getAllClans().size() < 1) {
            inventory.clear(10);
            inventory.setItem(10, itemStack);

            inventory.clear(16);
            inventory.setItem(16, itemStack);
        }
        for (String s : ClanUtil.getAllClans().values()) {
            if (!s.equalsIgnoreCase(returnString(slotOne, slotTwo, slotThree, slotFour, slotFive))) {
                inventory.clear(10);
                inventory.setItem(10, itemStack);

                inventory.clear(16);
                inventory.setItem(16, itemStack);
            } else {
                inventory.clear(10);
                inventory.setItem(10, red);

                inventory.clear(16);
                inventory.setItem(16, red);
            }
        }
        player.updateInventory();
    }

}
