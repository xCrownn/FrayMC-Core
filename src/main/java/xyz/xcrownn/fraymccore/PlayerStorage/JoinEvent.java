package xyz.xcrownn.fraymccore.PlayerStorage;

import de.leonhard.storage.Json;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.xcrownn.fraymccore.FrayMCCore;

import java.io.File;

public class JoinEvent implements Listener {

    private final FrayMCCore plugin;

    public JoinEvent(FrayMCCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        //    private UUID uuid;
        //    private long coins;
        //    private boolean inClan;
        //    private UUID clanUUID;
        //    private UUID invite;

        Player player = event.getPlayer();
        File file = new File(plugin.getDataFolder().getAbsolutePath() + "plugins/FrayMC-Core/" + player.getUniqueId());
        System.out.println(player.getName() + " - " + file.exists());
        if (!file.exists()) {
            Json json = new Json(player.getUniqueId().toString(), "plugins/FrayMC-Core/PlayerStorage");
            json.getOrSetDefault("Coins", 0);
            json.getOrSetDefault("InClan", false);
            json.getOrSetDefault("ClanUUID", "null");
            json.getOrSetDefault("InviteFrom", "null");
            json.getOrSetDefault("UUID", player.getUniqueId());
        }

    }

}
