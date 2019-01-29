package main;

import Data.PlayerWarpPointData;
import Utils.DataManager;
import commands.ActivateWarpExecutor;
import commands.AddWarpWorldExecutor;
import commands.ChWarpExecutor;
import commands.GetWarpSizeExecutor;
import commands.RemoveWarpWorldExecutor;
import commands.RmWarpExecutor;
import commands.SetWarpExecutor;
import commands.SetWarpSizeExecutor;
import commands.WarpExecutor;
import commands.WarpsExecutor;
import commands.WarpstatusExecutor;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import listeners.JoinListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tabCompleters.WarpTabCompleter;

/**
 * Main class of the plugin. 'onEnable' gets called when the plugin is activated
 * (usually at server startup).
 *
 * @author Nick
 */
public class Main extends JavaPlugin {

    private DataManager manager;

    @Override
    public void onEnable() {

        File dataFolder = getDataFolder();//Checks if datafolder already exists and makes it if it doesn't
        if (!dataFolder.exists())
            dataFolder.mkdir();

        loadConfig();//Loads the config file

        PlayerWarpPointData.setMaxWarps(getConfig().getInt("max_size"));//Sets the amount of warps based on the config file

        List<Player> players = new ArrayList<>(this.getServer().getOnlinePlayers());

        manager = new DataManager(this);//Initiates the DataManager

        Stream<UUID> temp = players.stream().map(s -> s.getUniqueId());//Gets a stream of the UUID of all the online players

        manager.fillUpFromFiles(temp == null ? new ArrayList<>() : temp.filter(s -> s != null)
                .collect(Collectors.toList()));//Fills up the Data if the playerList isn't empty to avoid nullpointers

        new JoinListener(this);//Makes an instance of the JoinListener. This also registers the Listener (see JoinListener).

        //Start of the command registration
        //First the aliases of the commands are set
        //Then the Executor attached to the command is set. This executor contains all the code executed whenever the command is called
        //Finally a TabCompleter might be set. This causes autocomplete to give a certain set of things to chose from 
        this.getCommand("setwarp").setExecutor(new SetWarpExecutor(this));

        this.getCommand("removewarp").setAliases(Arrays.asList("rmwarp", "remwarp", "rwarp", "rmvwarp"));
        this.getCommand("removewarp").setExecutor(new RmWarpExecutor(this));
        this.getCommand("removewarp").setTabCompleter(new WarpTabCompleter(this));

        this.getCommand("changewarp").setAliases(Arrays.asList("chwarp", "changewarpname", "cwarp"));
        this.getCommand("changewarp").setExecutor(new ChWarpExecutor(this));
        this.getCommand("changewarp").setTabCompleter(new WarpTabCompleter(this));

        this.getCommand("warplist").setAliases(Arrays.asList("warps", "listwarp", "getwarps"));
        this.getCommand("warplist").setExecutor(new WarpsExecutor(this));

        this.getCommand("warp").setExecutor(new WarpExecutor(this));
        this.getCommand("warp").setTabCompleter(new WarpTabCompleter(this));

        this.getCommand("activatewarpworld").setExecutor(new AddWarpWorldExecutor(this));

        this.getCommand("removewarpworld").setExecutor(new RemoveWarpWorldExecutor(this));

        this.getCommand("activatewarps").setExecutor(new ActivateWarpExecutor(this));

        this.getCommand("warpstatus").setExecutor(new WarpstatusExecutor(this));

        this.getCommand("setwarpsize").setExecutor(new SetWarpSizeExecutor(this));

        this.getCommand("warpsize").setExecutor(new GetWarpSizeExecutor(this));

    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);//Sets so the config should copy from the default
        saveConfig();
    }

}
