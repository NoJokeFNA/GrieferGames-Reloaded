package net.griefergames.reloaded.cache;

import net.griefergames.reloaded.GrieferGamesReloaded;
import net.griefergames.reloaded.utils.logger.GrieferGamesLogger;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class CacheMethods {

    public static void updateCache() {
        final Runnable cacheRunnable = () -> {
            // Execute methods
            GrieferGamesLogger.log(Level.INFO, "Updating cache", null);

            // After executing queries into the database
            clearCache();
        };

        final var scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(cacheRunnable, 15L, 15L, TimeUnit.MINUTES);
    }

    private static void clearCache() {
        for (final var cacheUuid : CacheHandler.USER_MAP.keySet()) {
            final var user = CacheHandler.getUserByUuid(cacheUuid);
            final var player = GrieferGamesReloaded.PLUGIN.getPlugin().getServer().getPlayer(user.getPlayerUuid());
            if (player == null)
                CacheHandler.USER_MAP.remove(user.getPlayerUuid());

            GrieferGamesLogger.log(Level.INFO, "Current cached players: {0}", new Object[]{CacheHandler.USER_MAP.size()});
        }
    }
}
