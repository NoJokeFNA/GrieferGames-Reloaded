package net.griefergames.reloaded.utils.logger;

import net.griefergames.reloaded.GrieferGamesReloaded;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GrieferGamesLogger {

    private static final Logger LOGGER = GrieferGamesReloaded.PLUGIN.getPlugin().getLogger();

    /**
     * Log what you want easily without using {@link Logger} everytime
     *
     * @param level   the {@link Level} type
     * @param message the {@code message} you wanna log
     * @param params  the placeholder for the {@code message}
     */
    public static void log(@NotNull final Level level, @NotNull final String message, final Object[] params) {
        LOGGER.log(level, message, params);
    }
}
