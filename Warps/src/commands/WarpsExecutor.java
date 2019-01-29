/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import utils.DataManager;
import exceptions.NoWarpsException;
import main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * Used to get a list of all the warps a player has.
 *
 * @author Nick
 */
public class WarpsExecutor implements CommandExecutor, Listener {

    private Main plugin;

    public WarpsExecutor(Main aThis) {
        plugin = aThis;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] arguments) {
        if (!(cs instanceof Player)) {//If the command sender isn't a player
            cs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Only players can use this command"));
            return false;
        }
        Player p = (Player) cs;

        if (arguments.length != 0) {//If arguments were given
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4I don't want any arguments, honey"));
            return false;
        }

        try {
            p.sendMessage(DataManager.getWarpNames(p.getUniqueId()));//Retrieves a String containing all the names
        } catch (Exception e) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("%s%s", "&4", e.getMessage())));//Catches all the exceptions caused by a bad argument as defined by the DataManager class
            return e instanceof NoWarpsException;//If the error was self-thrown, return true to not show the correct usage of the command
        }

        return true;
    }

}
