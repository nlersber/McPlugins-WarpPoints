/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import Data.PlayerWarpPointData;
import Utils.DataManager;
import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author Nick
 */
public class JoinListener implements Listener {

    private Main plugin;

    public JoinListener(Main plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    /**
     * This method sends a custom message whenever a player joins the server.
     * Depending on whether the player has joined the server before, a different
     * message will be displayed. A static method is used to allow for custom
     * colours. This uses the method chat() from the Utils class.
     *
     * @param e The event that triggered this method
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPlayedBefore()) {
            DataManager.loadData(p.getUniqueId());
            return;
        }
        DataManager.addPlayerAndFile(p.getUniqueId(), new PlayerWarpPointData());
    }
}
