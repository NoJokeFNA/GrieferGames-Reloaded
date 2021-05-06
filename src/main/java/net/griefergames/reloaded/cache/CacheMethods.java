package net.griefergames.reloaded.cache;

import java.util.Timer;
import java.util.TimerTask;

public class CacheMethods {

    public static void updateCache() {
        final Timer timer = new Timer( true );
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                // Execute methods
                System.out.println( "Updating cache..." );
            }
        };

        timer.schedule( timerTask, 1000L * 60L * 60L );
    }
}
