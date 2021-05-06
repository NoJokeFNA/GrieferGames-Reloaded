package net.griefergames.reloaded.cache;

import net.griefergames.reloaded.GrieferGamesReloaded;
import org.bukkit.entity.Player;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class CacheMethods {

    public static void updateCache() {
        final Timer timer = new Timer( true );
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                // Execute methods
                System.out.println( "Updating cache..." );

                // After executing queries into the database
                clearCache();
            }
        };

        timer.schedule( timerTask, 1000L * 60L * 60L, 1000L * 60L * 60L );
    }

    private static void clearCache() {
        for ( UUID cacheUuid : CacheHandler.USER_MAP.keySet() ) {
            final CacheUser user = CacheHandler.getUserByUuid( cacheUuid );
            final Player player = GrieferGamesReloaded.PLUGIN.getPlugin().getServer().getPlayer( user.getPlayerUuid() );
            if ( player == null ) {
                CacheHandler.USER_MAP.remove( user.getPlayerUuid() );
            }

            System.out.println( "Current cached players: " + CacheHandler.USER_MAP.size() );
        }
    }
}
