package main;

import Utils.DataManager;
import commands.ActivateWarpExecutor;
import commands.ChWarpExecutor;
import commands.RemoveWarpWorldExecutor;
import commands.RmWarpExecutor;
import commands.SetWarpExecutor;
import commands.WarpExecutor;
import commands.WarpsExecutor;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import listeners.JoinListener;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import tabCompleters.WarpTabCompleter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
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

        loadConfig();

        List<Player> players = new ArrayList<>();

        players.addAll(this.getServer().getOnlinePlayers());//Get list of all online players

        manager = new DataManager(this);//Initiates the DataManager

        Stream<UUID> temp = players.stream().map(s -> s.getUniqueId());

        manager.fillUpFromFiles(temp == null ? new ArrayList<>() : temp.filter(s -> s != null)
                .collect(Collectors.toList()));//Fills up the Data if the playerList isn't empty to avoid nullpointers

        new JoinListener(this);

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

        this.getCommand("activatewarpworld").setExecutor(new ActivateWarpExecutor(this));

        this.getCommand("removewarpworld").setExecutor(new RemoveWarpWorldExecutor(this));

        this.getCommand("activatewarps").setExecutor(new ActivateWarpExecutor(this));

    }

    /*
    commands:
    setwarp:
        usage: /<command> [warp name]
        description: Sets a warp point  
    removewarp:
        usage: /<command> [warp name]
        description: Removes a warp point
        aliases: [rmwarp, remwarp, rwarp, rmvwarp]
    changewarp:
        usage: /<command> [current warp name] [new warp name]
        description: Changes the name of a set warp
        aliases: [chwarp, changewarpname, cwarp]
    warplist:
        usage: /<command>
        description: Gives a list of all the warps you have set
        aliases: [warps, listwarp, getwarps]
    warp:
        usage: /<command> [warp name]
        description: Teleports you to the specified warp
     */
    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

//    public void setupPermissions(Player player) {
//        PermissionAttachment attachment = player.addAttachment(this);
//        this.permissions.put(player.getUniqueId(), attachment);
//        permissionSetter(player.getUniqueId());
//    }
//    
//    private void permissionSetter(UUID id) {
//        PermissionAttachment attachment = this.permissions.get(id);
//        
//        this.getConfig().getConfigurationSection("groups").getKeys(false).forEach(s -> {
//            this.getConfig().getStringList("groups." + s + ".permissions").forEach(t -> attachment.setPermission(t, true));
//        });
//    }
}
