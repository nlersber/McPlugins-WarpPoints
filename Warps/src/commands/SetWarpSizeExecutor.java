package commands;

import Data.PlayerWarpPointData;
import java.util.InputMismatchException;
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
public class SetWarpSizeExecutor implements CommandExecutor {

    private Main plugin;

    public SetWarpSizeExecutor(Main plugin) {
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

        if (args.length != 1) {
            p.sendMessage(ChatColor.RED + "Wrong amount of arguments!");
            return false;
        }
        try {
            int max = Integer.parseInt(args[0]);
            PlayerWarpPointData.setMaxWarps(max);
            plugin.getServer().broadcastMessage(ChatColor.GOLD + "Warp size is now set to " + max);
        } catch (IllegalArgumentException e) {
            p.sendMessage(ChatColor.RED + e.getMessage());
        } catch (InputMismatchException e) {
            p.sendMessage(ChatColor.RED + "That is not a whole number!");
        }

        return true;
    }

}
