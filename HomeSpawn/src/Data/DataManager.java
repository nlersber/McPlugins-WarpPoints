/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.File;
import main.Main;
import org.bukkit.Location;
import org.bukkit.World;

/**
 *
 * @author Nick
 */
public class DataManager {

    private static Main plugin;
    private static File dataFolder;
    private static Location spawn;
    private static World world;

    public DataManager(Main plugin) {
        this.plugin = plugin;
        dataFolder = plugin.getDataFolder();
        world = plugin.getServer().getWorlds().get(0);
        spawn = world.getSpawnLocation();

    }

    public static Location getSpawn() {
        return spawn;
    }

    public static void setSpawn(Location loc) {
        if (loc == null)
            throw new IllegalArgumentException("Something went wrong. Please contact the operators of this server");
        if (loc.equals(spawn))
            throw new IllegalArgumentException("New spawn location can't be the same as the old spawn location");
        spawn = loc;
        world.setSpawnLocation(loc);
    }

    public static void setSpawn(int x, int y, int z) {
        plugin.getServer().getWorlds().get(0).setSpawnLocation(x, y, z);
    }

    public static String getSpawnLocation() {
        return String.format("%s%n"
                + "%s%d%n"
                + "%s%d%n"
                + "%s%d",
                "Spawn point is set at: "
                );
    }
}
