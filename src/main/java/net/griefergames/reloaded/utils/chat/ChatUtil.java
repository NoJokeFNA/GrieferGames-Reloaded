package net.griefergames.reloaded.utils.chat;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class ChatUtil {
    public static String sendColoredMessage(@NotNull final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
