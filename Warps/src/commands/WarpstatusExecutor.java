package commands;

import main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Used to check if warps are currently available to you. Checks both the global
 * status and the status of your world.
 *
 * @author Nick
 */
public class WarpstatusExecutor implements CommandExecutor {

    private Main plugin;

    public WarpstatusExecutor(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage("You're not a player!");
            return true;
        }
        Player p = (Player) cs;
        if (args.length != 0) {
            p.sendMessage(ChatColor.RED + "Too many arguments!");
            return false;
        }

        boolean isOn = plugin.getConfig().getBoolean("isOn", true);//Gets the global status
        boolean isOnWorld = plugin.getConfig().getStringList("worlds").contains(p.getWorld().getUID().toString());//Gets the status of the current world. True if the current world is in the list
        cs.sendMessage(ChatColor.GOLD + "Warps are currently " + ((isOn && isOnWorld) ? "enabled" : "disabled") + "for you");
        return true;
    }

}
