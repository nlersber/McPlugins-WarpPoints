package commands;

import java.util.List;
import main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Nick
 */
public class RemoveWarpWorldExecutor implements CommandExecutor {

    private Main plugin;

    public RemoveWarpWorldExecutor(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String label, String[] args) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(ChatColor.RED + "You're not a player!");
            return true;
        }
        Player p = (Player) cs;

        if (!p.hasPermission("warps.configwarps")) {
            cs.sendMessage(ChatColor.RED + "You don't have permission to do that!");
            return true;
        }

        String world = p.getWorld().getUID().toString();

        List<String> list = plugin.getConfig().getStringList("worlds");

        if (!list.contains(world)) {
            p.sendMessage(ChatColor.RED + "Warps are already disabled on this world!");
            return true;
        }
        list.remove(world);

        plugin.getConfig().set("worlds", list);
        plugin.saveConfig();
        return true;
    }
}
