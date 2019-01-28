package commands;

import main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
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
        if (args.length != 0) {
            cs.sendMessage(ChatColor.RED + "Too many arguments!");
            return false;
        }

        boolean isOn = plugin.getConfig().getBoolean("isOn", true);
        cs.sendMessage(ChatColor.GOLD + "Warps are currently " + (isOn ? "enabled" : "disabled"));
        return true;
    }

}
