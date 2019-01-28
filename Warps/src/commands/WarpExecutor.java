/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import Utils.DataManager;
import exceptions.NoWarpsException;
import java.util.Arrays;
import main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 *
 * @author Nick
 */
public class WarpExecutor implements CommandExecutor, Listener {

    private Main plugin;

    public WarpExecutor(Main plugin) {
        this.plugin = plugin;
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
        if (!plugin.getConfig().getBoolean("isOn")) {
            p.sendMessage(ChatColor.RED + "Warps are currently disabled!");
            return true;
        }
        if (arguments.length < 1) {//If no arguments were given
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Please specify a warp"));
            return false;
        }
        if (arguments.length > 1) {//If too many arguments were given
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You only need 1 argument for this command. I only need a name, darling"));
            return false;
        }

        try {
            p.teleport(DataManager.getWarpLocation(p.getUniqueId(), arguments[0]));
        } catch (Exception e) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("%s%s", "&4", e.getMessage())));//Catches all the exceptions caused by a bad argument as defined by the DataManager class
            return e instanceof NoWarpsException;
        }

        p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("%s [%s]", "&2You have arrived at ", arguments[0])));//Sends a confirmation of success
        return true;
    }

}
