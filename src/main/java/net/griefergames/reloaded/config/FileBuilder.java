package net.griefergames.reloaded.config;

import net.griefergames.reloaded.GrieferGamesReloaded;
import net.griefergames.reloaded.exception.ExceptionHandler;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class FileBuilder extends YamlConfiguration {
    private final String fileName;

    private final File file;
    private final YamlConfiguration configuration;

    /**
     * Create a new custom file with the given {@code fileName}
     * The file will be created automatically afterwards
     *
     * @param fileName the name of the file
     */
    public FileBuilder(@NotNull String fileName) {
        this.fileName = fileName;

        this.file = new File(GrieferGamesReloaded.PLUGIN.getPlugin().getDataFolder().getAbsolutePath(), this.fileName + ".yml");
        this.configuration = YamlConfiguration.loadConfiguration(this.file);

        GrieferGamesReloaded.PLUGIN.getPlugin().saveResource(fileName, false);
    }

    /**
     * Set a specific value in the config
     *
     * @param path  the path
     * @param value the message to the path
     */
    public void set(@NotNull String path, Object value) {
        this.configuration.set(path, value);

        this.reloadConfig();
        this.saveConfig();
    }

    /**
     * Save the config
     */
    public void saveConfig() {
        try {
            this.configuration.save(this.file);
        } catch (IOException exception) {
            ExceptionHandler.handleException(exception, "Config '" + this.fileName + "' cannot be saved!", true);
        }
    }

    /**
     * Reload the config
     */
    public void reloadConfig() {
        final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(this.file);
        this.configuration.setDefaults(defConfig);
    }
}
