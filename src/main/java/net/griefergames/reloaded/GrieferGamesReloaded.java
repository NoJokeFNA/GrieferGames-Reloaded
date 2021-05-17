package net.griefergames.reloaded;

import lombok.Getter;
import net.griefergames.reloaded.config.PropertiesReader;
import net.griefergames.reloaded.config.handler.ConfigHandler;
import net.griefergames.reloaded.database.builder.DatabaseBuilder;
import net.griefergames.reloaded.database.handler.DatabaseHandler;
import net.griefergames.reloaded.listener.PlayerJoinListener;
import net.griefergames.reloaded.listener.UserBalanceUpdateListener;
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

    private ConfigHandler configHandler;
    private DatabaseHandler databaseHandler;

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
        this.configHandler = new ConfigHandler();
        this.databaseHandler = new DatabaseHandler();

        new PropertiesReader().loadDataSourceProperties();
        new DatabaseBuilder().createTable();

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
        final var listeners = new Listener[] {
                new PlayerJoinListener(), new UserBalanceUpdateListener()
        };

        Arrays.stream( listeners ).forEach( listener -> Bukkit.getPluginManager().registerEvents( listener, this.plugin ) );
    }
}
