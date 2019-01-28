package commands;

import java.util.List;
import main.Main;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Nick
 */
public class AddWarpWorldExecutor implements CommandExecutor {

    private Main plugin;

    public AddWarpWorldExecutor(Main plugin) {
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

        if (args.length > 1) {
            p.sendMessage(ChatColor.RED + "That's too many arguments!");
            return false;
        }
//        String world = null;
//        if (args.length == 0)
//            world = p.getWorld().getUID().toString();
//        else
//            world = plugin.getServer().getWorld(args[0]).getUID().toString();
        World givenWorld = plugin.getServer().getWorld(args[0]);

        if (givenWorld == null) {
            cs.sendMessage(ChatColor.RED + "That world doesn't exist. Did you spell it correctly?");
            return true;
        }
        String world = (args.length == 0)
                ? p.getWorld().getUID().toString()//If no world is given, use the current world
                : givenWorld.getUID().toString();//Try to find 

        List<String> list = plugin.getConfig().getStringList("worlds");

        if (list.contains(world)) {
            p.sendMessage(ChatColor.RED + "Warps are already enabled on this world!");
            return true;
        }
        list.add(world);

        plugin.getConfig().set("worlds", list);
        plugin.saveConfig();
        return true;
    }

}
