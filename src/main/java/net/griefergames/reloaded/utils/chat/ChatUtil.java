package net.griefergames.reloaded.utils.chat;

import org.bukkit.ChatColor;

public class ChatUtil {

    public static String sendColoredMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
