/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import main.Main;
import Utils.DataManager;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 *
 * @author Nick
 */
public class SetWarpExecutor implements CommandExecutor, Listener {

    private Main plugin;

    public SetWarpExecutor(Main aThis) {
        plugin = aThis;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] arguments) {
        if (!(cs instanceof Player)) {//If the command sender isn't a player
            cs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Only players can use this command"));
            return false;
        }

        Player p = (Player) cs;

        if (!plugin.getConfig().getStringList("worlds").contains(p.getWorld().getUID().toString())) {
            p.sendMessage(ChatColor.RED + "Warps are not allowed here!");
            return true;
        }
        if (arguments.length == 0) {//If no arguments were given and thus no name for the warp was given
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Please specify a name for the warp"));
            return false;
        }
        if (arguments.length > 1) {//If too many arguments were given
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You only need 1 argument for this command. I only need a name, darling"));
            return false;
        }
        if (!p.isOnGround()) {//If the player is not standing on solid ground, warping could result in death by fall
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You're not standing on solid ground!"));
            return false;
        }

        Location loc = p.getLocation();

        try {
            DataManager.addWarpPoint(p.getUniqueId(), arguments[0], loc);
        } catch (Exception e) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("%s%s", "&4", e.getMessage())));//Catches all the exceptions caused by a bad argument as defined by the DataManager class
            return false;
        }

        p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("%s '%s' %s", "&2Warp location", arguments[0], "was successfully created")));//Sends a confirmation of success
        return true;
    }

}
