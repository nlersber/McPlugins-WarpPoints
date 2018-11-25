package main;

import Utils.DataManager;
import commands.ChWarpExecutor;
import commands.RmWarpExecutor;
import commands.SetWarpExecutor;
import commands.WarpExecutor;
import commands.WarpsExecutor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import listeners.JoinListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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

        List<Player> players = new ArrayList<>();

        players.addAll(this.getServer().getOnlinePlayers());//Get list of all online players

        manager = new DataManager(this);//Initiates the DataManager

        Stream<UUID> temp = players.stream().map(s -> s.getUniqueId());

        manager.fillUpFromFiles(temp == null ? new ArrayList<>() : temp.filter(s -> s != null)
                .collect(Collectors.toList()));//Fills up the Data if the playerList isn't empty to avoid nullpointers

        new JoinListener(this);

        this.getCommand("setwarp").setExecutor(new SetWarpExecutor(this));
        this.getCommand("rmwarp").setExecutor(new RmWarpExecutor(this));
        this.getCommand("chwarp").setExecutor(new ChWarpExecutor(this));
        this.getCommand("warps").setExecutor(new WarpsExecutor(this));
        this.getCommand("warp").setExecutor(new WarpExecutor(this));

    }

    //TODO: test 
}
