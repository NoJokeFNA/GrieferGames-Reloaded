package net.griefergames.reloaded.modules.holographic;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import net.griefergames.reloaded.GrieferGamesReloaded;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerHolographicController {

    public void createHolographic( @NotNull final Player player, final int holographicId, @NotNull final String text ) {
        //TODO: Set the players holographic -> Database

        final var hologram = HologramsAPI.createHologram( GrieferGamesReloaded.PLUGIN.getPlugin(), player.getLocation() );
        hologram.appendTextLine( text );
    }

    public void listHolographs( @NotNull final Player player ) {
        //TODO: Get holographs from the player -> Database
    }

    public void addLine( @NotNull final Player player, final int holographicId, @NotNull final String text ) {
        //TODO: Set the players holographic -> Database
    }

    public void editLine( @NotNull final Player player, final int holographicId, final int lineNumber, @NotNull final String text ) {

    }

    public void moveHolographic( @NotNull final Player player, final int holographicId ) {

    }

    public void deleteHolographic( @NotNull final Player player, final int holographicId ) {

    }

    public void sendHelpMessage( @NotNull final Player player ) {

    }
}
