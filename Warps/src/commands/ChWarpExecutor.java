/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import Utils.DataManager;
import java.util.Arrays;
import main.Main;
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
public class ChWarpExecutor implements CommandExecutor, Listener {

    private Main plugin;

    public ChWarpExecutor(Main aThis) {
        plugin = aThis;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] arguments) {
        if (!(cs instanceof Player)) {//If the command sender isn't a player
            cs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Only players can use this command"));
            return false;
        }
        Player p = (Player) cs;
        if (arguments.length < 2) {//If no arguments were given
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Please specify the current and new name of the warp"));
            return false;
        }
        if (arguments.length > 2) {//If too many arguments were given
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4You only need 2 arguments for this command. I only need the names, darling"));
            return false;
        }

        try {
            DataManager.changeName(p.getUniqueId(), arguments[0], arguments[1]);//Changes the name of the chosen warp. Checks if both names are valid and if the current name corresponds with a location
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("%s%s", "&4", e.getMessage())));//Catches all the exceptions caused by a bad argument as defined by the DataManager class
            return false;
        }

        p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("%s '%s' %s %s", "&2Warp location", arguments[0], "was successfully renamed to", arguments[1])));//Sends a confirmation of success
        return true;
    }

}
