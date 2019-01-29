package commands;

import main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *  Used to enable/disable warps globally
 * @author Nick
 */
public class ActivateWarpExecutor implements CommandExecutor {

    private Main plugin;

    public ActivateWarpExecutor(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String label, String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(ChatColor.RED + "You're not a player!");
            return true;
        }

        Player p = (Player) cs;

        if (args.length != 0) {
            p.sendMessage(ChatColor.RED + "That's too many arguments!");
            return false;
        }

        if (!p.hasPermission("warps.configwarps")) {//Check if player has permissions. Permissions are found in plugin.yml. Default set to OP only
            cs.sendMessage(ChatColor.RED + "You don't have permission to do that!");
            return true;
        }

        boolean isOn = !plugin.getConfig().getBoolean("isOn");//Gets boolean from config file and flips it
        plugin.getServer().broadcastMessage(ChatColor.GOLD + "Warps are now " + (isOn ? "enabled" : "disabled"));//Global status announcement
        plugin.getConfig().set("isOn", isOn);//Store flipped value

        return true;
    }

}
