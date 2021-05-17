package net.griefergames.reloaded.modules.holographic;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import lombok.NonNull;
import net.griefergames.reloaded.GrieferGamesReloaded;
import org.bukkit.entity.Player;

public class PlayerHolographicController {

    public void createHolographic( @NonNull final Player player, final int holographicId, @NonNull final String text ) {
        //TODO: Set the players holographic -> Database

        final var hologram = HologramsAPI.createHologram( GrieferGamesReloaded.PLUGIN.getPlugin(), player.getLocation() );
        hologram.appendTextLine( text );
    }

    public void listHolographs( @NonNull final Player player ) {
        //TODO: Get holographs from the player -> Database
    }

    public void addLine( @NonNull final Player player, final int holographicId, @NonNull final String text ) {
        //TODO: Set the players holographic -> Database
    }

    public void editLine( @NonNull final Player player, final int holographicId, final int lineNumber, @NonNull final String text ) {

    }

    public void moveHolographic( @NonNull final Player player, final int holographicId ) {

    }

    public void deleteHolographic( @NonNull final Player player, final int holographicId ) {

    }

    public void sendHelpMessage( @NonNull final Player player ) {

    }
}
