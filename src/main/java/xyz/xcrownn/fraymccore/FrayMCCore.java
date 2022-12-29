package xyz.xcrownn.fraymccore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.xcrownn.fraymccore.Clans.Commands.ClanCommands;
import xyz.xcrownn.fraymccore.Currency.CoinsCommand;
import xyz.xcrownn.fraymccore.Placeholders.WzCoreExpansion;
import xyz.xcrownn.fraymccore.PlayerStorage.JoinEvent;
import xyz.xcrownn.fraymccore.Utils.MenuManager.MenuEvents;

public final class FrayMCCore extends JavaPlugin {

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {

            ConfigBuilder.buildMessageFile();

            new WzCoreExpansion().register();

            getCommand("clan").setExecutor(new ClanCommands());
            getCommand("coins").setExecutor(new CoinsCommand());
            getServer().getPluginManager().registerEvents(new MenuEvents(), this);
            getServer().getPluginManager().registerEvents(new JoinEvent(this), this);


        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
