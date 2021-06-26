package net.griefergames.reloaded;

import org.bukkit.plugin.java.JavaPlugin;

public class GrieferGamesReloadedPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        GrieferGamesReloaded.PLUGIN.start(this);
    }

    @Override
    public void onDisable() {
        GrieferGamesReloaded.PLUGIN.stop(this);
    }
}