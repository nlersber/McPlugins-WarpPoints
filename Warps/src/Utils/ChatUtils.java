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

    public static String chat(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);//Adds a color to the chat text. Deprecated in this project, use 'ChatColor.ENUMVALUE + "String" instead for cleaner code
    }
}
