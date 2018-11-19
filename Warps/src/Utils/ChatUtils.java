/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import org.bukkit.ChatColor;

/**
 *
 * @author Nick
 */
public class ChatUtils {

    public static String translateAlternateColorCodes(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
