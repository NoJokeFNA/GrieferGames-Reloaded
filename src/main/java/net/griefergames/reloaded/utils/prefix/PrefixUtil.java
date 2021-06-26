package net.griefergames.reloaded.utils.prefix;

import lombok.val;
import org.jetbrains.annotations.NotNull;

public class PrefixUtil {

    private static int i = 0;

    public static String setPlayerPrefix(@NotNull final String playerName, final char firstColor, final char secondColor) {
        val stringBuilder = new StringBuilder();

        val playerNameCharArray = playerName.toCharArray();
        for (char c : playerNameCharArray) {
            i++;

            switch (i) {
                case 1, 2 -> stringBuilder.append("§").append(firstColor).append(c).append(" ");
                case 3 -> stringBuilder.append("§").append(secondColor).append(c).append(" ");
                case 4 -> {
                    stringBuilder.append("§").append(secondColor).append(c).append(" ");
                    i = 0;
                }
            }
        }

        i = 0;

        return stringBuilder.toString();
    }
}
