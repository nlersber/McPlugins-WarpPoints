/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Nick
 */
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        File dataFolder = this.getDataFolder();
        if (!dataFolder.exists())
            dataFolder.mkdir();
        
        
    }

}
