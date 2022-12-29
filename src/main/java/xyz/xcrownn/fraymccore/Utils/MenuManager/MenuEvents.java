package xyz.xcrownn.fraymccore.Utils.MenuManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import java.io.IOException;

public class MenuEvents implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) throws IOException, ClassNotFoundException {
        InventoryHolder inventoryHolder = event.getView().getTopInventory().getHolder();

        if (inventoryHolder instanceof Menu) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) {
                return;
            }
        }


        Menu menu = (Menu) inventoryHolder;
        menu.handleMenu(event);

    }

}
