package net.griefergames.reloaded.cache;

import net.griefergames.reloaded.GrieferGamesReloaded;

import java.util.Timer;
import java.util.TimerTask;

public class CacheMethods {

    public static void updateCache() {
        final var timer = new Timer( true );
        final var timerTask = new TimerTask() {
            @Override
            public void run() {
                // Execute methods
                System.out.println( "Updating cache..." );

                // After executing queries into the database
                clearCache();
            }
        };

        timer.schedule( timerTask, 1000L * 60L * 15L, 1000L * 60L * 15L );
    }

    private static void clearCache() {
        for ( final var cacheUuid : CacheHandler.USER_MAP.keySet() ) {
            final var user = CacheHandler.getUserByUuid( cacheUuid );
            final var player = GrieferGamesReloaded.PLUGIN.getPlugin().getServer().getPlayer( user.getPlayerUuid() );
            if ( player == null )
                CacheHandler.USER_MAP.remove( user.getPlayerUuid() );

            System.out.println( "Current cached players: " + CacheHandler.USER_MAP.size() );
        }
    }
}
