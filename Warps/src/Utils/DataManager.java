/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Data.PlayerWarpPointData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Location;

/**
 *
 * @author Nick
 */
public class DataManager {

    private static Map<UUID, PlayerWarpPointData> points = new HashMap<>();
    private static Main plugin;
    private static File dataFolder;

    public DataManager(Main main) {
        plugin = main;
        dataFolder = plugin.getDataFolder();
    }

    /**
     * Fills up the Map with the data stored on files. Each player has his own
     * file with a name equal to the toString() if its UUID. This method takes
     * in that data, connects each UUID to a file via the name and reads the
     * PlayerWarpPointData. If a UUID does not have a file, it adds an empty
     * PlayerWarpPointData and uses this to write to a file.
     *
     * @param players List of the UUID's by all players
     */
    public void fillUpFromFiles(List<UUID> players) {
        File[] files = dataFolder.listFiles();

        if (files != null)//To prevent a NullPointer
            Arrays.stream(files).forEach(s -> {
                UUID temp = UUID.fromString(s.getName());//Contains the player's UUID, removes the extension
                if (players.contains(temp)) {//Checks if the UUID corresponds with a player
                    try (ObjectInputStream i = new ObjectInputStream(new FileInputStream(s))) {

                        Map<String, Map<String, Object>> tempMap = (Map<String, Map<String, Object>>) i.readObject();//Reads the Map used to serialize the object
                        PlayerWarpPointData data = null;

                        if (tempMap != null)
                            data = new PlayerWarpPointData(tempMap);//Makes a PWPD with the Map

                        if (data != null)//If the Map was found
                            points.put(temp, data);
                    } catch (Exception e) {
                    }
                }
            });//End of stream
        /*
        Checks which players didn't have a file yet and gives those their file wrapper
         */
        Set<UUID> keys = points.keySet();//Stores the keyset of points
        players.forEach(s -> {//Iterates over the players and checks if they have a data wrapper already
            if (!keys.contains(s))
                createFileAndDataWrapper(s);//Gives them a data wrapper and file if they don't
        });

    }

    /**
     * Creates a file and a entry in this.points for the given UUID
     *
     * @param id UUID of a player without file and PlayerWarpPointData
     */
    private void createFileAndDataWrapper(UUID id) {
        points.putIfAbsent(id, new PlayerWarpPointData());//IfAbsent is redundant, extra check
        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(new File(dataFolder, id.toString()), false))) {//Makes an outputstream, places it in the datafolder in a file named after the UUID of the player. False added to avoid appending and instead overwriting the file should there be one after all
            o.writeObject(new PlayerWarpPointData().serialize());//Makes a file with an empty wrapper, just to have a file available
        } catch (IOException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addPlayerAndFile(UUID id, PlayerWarpPointData data) {
        points.putIfAbsent(id, new PlayerWarpPointData());//putIfAbsent to allow the method to be used when a player hasn't gotten a file yet but needs an entry
        writeToFile(id, data);
    }

    /**
     * Updates the data file for the given player. Checks if the player has a
     * file. If not, makes a file with the data stored. IF the player has one,
     * overwrites the files with the stored data.
     *
     * @param id UUID of the player
     */
    private static void updatePlayerAndFile(UUID id) {
        File file = new File(dataFolder, id.toString());
//        if (file != null) {//Checks if the file exists
//            addPlayerAndFile(id, points.get(id));//If not, adds a file for the player. Should the player already have an entry in the map, uses that. Adds a new entry if the player hasn't got one
//            return;//File is updated for the player, method is complete
//        }
        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(new File(dataFolder, id.toString()), false))) {//Opens stream
            PlayerWarpPointData data = points.get(id);
            if (data == null) {
                addPlayerAndFile(id, new PlayerWarpPointData());
                return;
            }
            o.writeObject(data.serialize());//Overwrites the file
            o.close();//Closes it to prevent possible appending
        } catch (IOException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Writes the PlayerWarpPointData object to a file. Uses the toString() of
     * the UUID as name.
     *
     * @param id UUID of the player
     * @param data Map containing the warp points
     */
    private static void writeToFile(UUID id, PlayerWarpPointData data) {
        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(new File(dataFolder, id.toString()), false))) {//Makes an outputstreams
            o.writeObject(data.serialize());//Makes a file with an wrapper
        } catch (IOException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Adds a warp point to the Map of a player and updates its corresponding
     * file.
     *
     * @param id UUID of the player
     * @param name Name of the warp to be added
     * @param loc Location of the warp
     */
    public static void addWarpPoint(UUID id, String name, Location loc) {
        PlayerWarpPointData data = points.get(id);
        data.addWarp(name, loc);
        updatePlayerAndFile(id);
    }

    /**
     * Removes a warp point and updates its corresponding file.
     *
     * @param id UUID of the player
     * @param name Name of the warp to be removed
     */
    public static void removeWarpPoint(UUID id, String name) {
        PlayerWarpPointData data = points.get(id);
        data.removeWarp(name);
        updatePlayerAndFile(id);
    }

    /**
     * Changes the name of a warp in the Map
     *
     * @param id UUID of the player
     * @param name Current name of the warp to be changed
     * @param replace New name of the warp
     */
    public static void changeName(UUID id, String name, String replace) {
        PlayerWarpPointData data = points.get(id);
        data.changeName(name, replace);
        updatePlayerAndFile(id);
    }

    /**
     * Gives a list of the names of all the warps of a player
     *
     * @param id UUID of the player
     * @return String with all the names of a player's warps
     */
    public static String getWarpNames(UUID id) {
        PlayerWarpPointData data = points.get(id);
        String names = data.getWarpNames();
        if (names == null || names.trim().isEmpty())
            throw new IllegalArgumentException("You have no warps");
        return names;
    }

    public static Location getWarpLocation(UUID id, String name) {
        return points.get(id).getLocationByName(name);
    }

    public static void loadData(UUID id) {
        File[] files = dataFolder.listFiles((File dir, String name) -> {
            return (name.startsWith(id.toString()) && FilenameUtils.getBaseName(name).endsWith(id.toString()));
        });
        File file = null;
        if (files != null || files.length == 0)
            file = files[0];
        if (file != null)
            try (ObjectInputStream i = new ObjectInputStream(new FileInputStream(file))) {//Maakt een InputStream aan voor het object
                Map<String, Map<String, Object>> tempMap = (Map<String, Map<String, Object>>) i.readObject();//Leest het object
                PlayerWarpPointData data = null;
                if (tempMap != null)
                    data = new PlayerWarpPointData(tempMap);
                if (data != null)//Indien het object kon gemaakt worden
                    points.put(id, data);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        points.putIfAbsent(id, new PlayerWarpPointData());
    }
}
