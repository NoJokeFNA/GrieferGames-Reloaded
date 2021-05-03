package net.griefergames.reloaded;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.Arrays;

/**
 * This class will handle every initializing - from startup to the ending
 */
@Getter
public enum GrieferGamesReloaded {

    PLUGIN;

    private GrieferGamesReloadedPlugin plugin;

    /**
     * Start method for the plugin - {@link GrieferGamesReloadedPlugin}
     *
     * @param plugin the plugin instance
     */
    public void start( final GrieferGamesReloadedPlugin plugin ) {
        this.plugin = plugin;

        assert plugin != null : "Something went wrong! Plugin was null.";

        this.init();
    }

    /**
     * Stop method for the plugin - {@link GrieferGamesReloadedPlugin}
     *
     * @param plugin the plugin instance
     */
    public void stop( final GrieferGamesReloadedPlugin plugin ) {
        this.plugin = plugin;

        assert plugin != null : "Something went wrong! Plugin was null.";
    }

    /**
     * Initialize everything
     */
    private void init() {
        this.registerCommands();
        this.registerListener();
    }

    /**
     * Register all commands
     */
    private void registerCommands() {

    }

    /**
     * Register all listener
     */
    private void registerListener() {
        final Listener[] listeners = new Listener[] {

        };

        Arrays.stream( listeners ).forEach( listener -> Bukkit.getPluginManager().registerEvents( listener, this.plugin ) );
    }
}
