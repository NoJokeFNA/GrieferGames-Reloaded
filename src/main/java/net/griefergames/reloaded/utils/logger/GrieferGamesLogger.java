package net.griefergames.reloaded.utils.logger;

import net.griefergames.reloaded.GrieferGamesReloaded;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GrieferGamesLogger {

    private static final Logger LOGGER = GrieferGamesReloaded.PLUGIN.getPlugin().getLogger();

    public static void log( final Level level, final String message, final Object... params ) {
        LOGGER.log( level, message, params );
    }
}
