/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Location;

/**
 * Class that contains the warp points per player
 *
 * @author Nick
 */
public class PlayerWarpPointData implements Serializable {

    private Map<String, Location> warps = new HashMap<>();
    private static int maxSize = 10;

    public PlayerWarpPointData() {
    }

    public PlayerWarpPointData(Map<String, Map<String, Object>> serialized) {
        serialized.forEach((key, val) -> warps.put(key, Location.deserialize(val)));
    }

    public Map<String, Map<String, Object>> serialize() {
        Map<String, Map<String, Object>> temp = new HashMap<>();
        warps.forEach((key, val) -> temp.put(key, val.serialize()));
        return temp;
    }
    

    /**
     * Takes in the name of a possible Location and returns that locations if
     * the HashMap contains a location with that name. If not, throws an
     * exception.
     *
     * @param name The name of the to be returned locations. Is used as key in
     * the Map.
     * @return The location if it was found. Will throw an exceptions if it
     * wasn't found.
     */
    public Location getLocationByName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Please specify a warp");
        Location loc = warps.get(name);
        if (loc == null)
            throw new IllegalArgumentException("You have no warp with that name");
        return loc;
    }

    /**
     * Adds a new warp location to the HashMap. Checks if the name is null or
     * empty and whether the Map already contains a location with the same name.
     * Adds the location to the Map if it's a new location.
     *
     * @param name Name of the location
     * @param loc The location itself
     */
    public void addWarp(String name, Location loc) {
        if (warps.size() == maxSize)
            throw new IllegalArgumentException("You have no more free warps available");
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Please specify a warp name");
        if (warps.containsKey(name))
            throw new IllegalArgumentException("A warp with that name already exists");
        warps.put(name, loc);
    }

    /**
     * Removes a warp location.Checks if the name is valid and if there is a
     * warp location with that name. Throws IllegalArgumentExceptions if not. If
     * the location is valid and exists in the Map, removes the location.
     *
     * @param name Name of the location to be removed.
     * @return Returns true if the removal was successful and there weren't any
     * previous exceptions, false if the element wasn't removed.
     */
    public boolean removeWarp(String name) {
        if (warps.isEmpty())
            throw new IllegalArgumentException("You have no warps");
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Please specify a warp name");
        if (!warps.containsKey(name))
            throw new IllegalArgumentException("You have no warp with that name");
        warps.remove(name);
        return !warps.containsKey(name);
    }

    /**
     * Concatinates all the names of all the warp points. Returns null if it's
     * empty. Also sorts the names alphabetically.
     *
     * @return
     */
    public String getWarpNames() {
        return warps.isEmpty()
                ? null
                : warps.keySet().stream().sorted().collect(Collectors.joining("\n"));
    }

    /**
     * Static method to get the max amount of warps. This is the same for all
     * players, thus is static as well.
     *
     * @return Max amount of warps
     */
    public static int getMaxSize() {
        return maxSize;
    }

    /**
     * Shows the amount of warps stored. Counts the amount of entries in the
     * map.
     *
     * @return Size of the Map
     */
    public int getSize() {
        return warps.size();
    }

    /**
     * Changes the name of an existing warp. Checks if the names are valid, the
     * warp exists and the name can be changed. Change can't happen when the new
     * name is the same as the old name or there is already a warp with the new
     * name.
     *
     * @param name Old name of the warp
     * @param replace New name of the warp
     * @return Boolean if they change was successful
     */
    public boolean changeName(String name, String replace) {
        if (warps.isEmpty())
            throw new IllegalArgumentException("You have no warps");
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Please specify a warp to change");
        if (replace == null || replace.trim().isEmpty())
            throw new IllegalArgumentException("Please provide a new name for the warp");
        if (!warps.containsKey(name))
            throw new IllegalArgumentException("You have no warp with that name");
        if (warps.containsKey(replace))
            throw new IllegalArgumentException("You already have a warp with that name");
        if (name.equalsIgnoreCase(replace))
            throw new IllegalArgumentException("Old name and new name have to be different");

        warps.put(replace, warps.remove(name));//Removes the old entry and uses the value to save it under the new name

        return warps.containsKey(replace);
    }

}