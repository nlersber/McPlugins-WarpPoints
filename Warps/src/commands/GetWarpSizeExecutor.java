package commands;

import Data.PlayerWarpPointData;
import main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Used to get the max amount of warps available.
 *
 * @author Nick
 */
public class GetWarpSizeExecutor implements CommandExecutor {

    private Main plugin;

    public GetWarpSizeExecutor(Main plugin) {
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
            p.sendMessage(ChatColor.RED + "Too many arguments!");
            return false;
        }
        p.sendMessage(ChatColor.BLUE + "Max amount of warps is currently " + PlayerWarpPointData.getMaxSize());//DelegationF

        return true;
    }

}
