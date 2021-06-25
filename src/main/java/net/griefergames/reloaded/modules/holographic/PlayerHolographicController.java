package net.griefergames.reloaded.modules.holographic;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import net.griefergames.reloaded.GrieferGamesReloaded;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class PlayerHolographicController {

    public void createHolographic(@NotNull final Player player, final int holographicId, @NotNull final String text) {
        //TODO: Set the players holographic -> Database

        final var hologram = HologramsAPI.createHologram(GrieferGamesReloaded.PLUGIN.getPlugin(), player.getLocation());
        hologram.appendTextLine(text);
    }

    public void listHolographs(@NotNull final Player player) {
        //TODO: Get holographs from the player -> Database
    }

    public void addLine(@NotNull final Player player, final int holographicId, @NotNull final String text) {
        //TODO: Add a line from the players holograms line -> Database

        final var hologramCollection = this.getHolograms();
        hologramCollection.forEach(hologram -> {
            //TODO: Do some stuff
        });
    }

    public void removeLine(@NotNull final Player player, final int holographicId, @NotNull final String text) {
        //TODO: Remove a line from the players holograms line -> Database

        final var hologramCollection = this.getHolograms();
        hologramCollection.forEach(hologram -> {
            //TODO: Do some stuff
            hologram.removeLine(holographicId);
        });
    }

    public void editLine(@NotNull final Player player, final int holographicId, final int lineNumber, @NotNull final String text) {
        //TODO: Edit the players holographic -> Database

        final var hologramCollection = this.getHolograms();
        hologramCollection.forEach(hologram -> {
            //TODO: Do some stuff
        });
    }

    public void moveHolographic(@NotNull final Player player, final int holographicId) {
        //TODO: Move the players holographic -> Database

        final var hologramCollection = this.getHolograms();
        hologramCollection.forEach(hologram -> {
            //TODO: Do some stuff
        });
    }

    public void deleteHolographic(@NotNull final Player player, final int holographicId) {
        //TODO: Delete the players holographic -> Database

        final var hologramCollection = this.getHolograms();
        hologramCollection.forEach(hologram -> {
            //TODO: Do some stuff
            hologram.delete();
        });
    }

    public void sendHelpMessage(@NotNull final Player player) {
        //TODO: Send help message to the player
    }

    private Collection<Hologram> getHolograms() {
        return HologramsAPI.getHolograms(GrieferGamesReloaded.PLUGIN.getPlugin());
    }
}
