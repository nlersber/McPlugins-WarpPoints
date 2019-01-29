package tabCompleters;

import Utils.DataManager;
import java.util.Arrays;
import java.util.List;
import main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

/**
 *
 * @author Nick
 */
public class WarpTabCompleter implements TabCompleter {

    private Main plugin;

    public WarpTabCompleter(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (!(cs instanceof Player)) {
            cs.sendMessage("You're not a player!");
            return null;
        }

        Player p = (Player) cs;
        return Arrays.asList(DataManager.getWarpNames(p.getUniqueId()).split("\n"));//Returns a list of the given player's warps
    }

}
