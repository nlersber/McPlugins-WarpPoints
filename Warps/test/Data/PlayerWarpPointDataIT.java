/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Nick
 */
public class PlayerWarpPointDataIT {

    private PlayerWarpPointData data;
    private Map<String, Location> serialized;
    private Location testLoc;

    @Before
    public void setup() {
        testLoc = new Location(null, 0, 0, 0);
        serialized = new HashMap<>();
        serialized.put("test", testLoc);
        data = new PlayerWarpPointData(serialized, true);
    }

    public PlayerWarpPointDataIT() {
    }

    /**
     * Test of getLocationByName method, of class PlayerWarpPointData.
     */
    @Test
    public void testGetLocationByName() {
        String name = "test";

        Location result = data.getLocationByName(name);
        assertEquals(testLoc, result);
    }

    /**
     * Test of addWarp method, of class PlayerWarpPointData.
     */
    @Test
    public void testAddWarp() {
        String name = "test";
        PlayerWarpPointData instance = new PlayerWarpPointData();
        instance.addWarp(name, testLoc);
        assertEquals(data.getSize(), instance.getSize());
    }

    /**
     * Test of removeWarp method, of class PlayerWarpPointData.
     */
    @Test
    public void testRemoveWarp() {
        String name = "test";
        boolean result = data.removeWarp(name);
        assertTrue(data.getSize() == 0);

    }

    /**
     * Test of getWarpNames method, of class PlayerWarpPointData.
     */
    @Test
    public void testGetWarpNames() {
        assertTrue(data.getWarpNames() != null && !data.getWarpNames().trim().isEmpty());
    }

    /**
     * Test of getSize method, of class PlayerWarpPointData.
     */
    @Test
    public void testGetSize() {
        assertEquals(1, data.getSize());
    }

    /**
     * Test of changeName method, of class PlayerWarpPointData.
     */
    @Test
    public void testChangeName() {
        data.changeName("test", "test2");
        assertTrue(data.getLocationByName("test2") != null);
    }
}
