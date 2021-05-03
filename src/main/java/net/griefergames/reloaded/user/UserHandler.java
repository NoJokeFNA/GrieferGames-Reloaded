package net.griefergames.reloaded.user;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class UserHandler {

    private static final Map<UUID, User> USER_MAP = Maps.newHashMap();

    /**
     * Get the user who is cached by the {@link UUID} object
     *
     * @param playerUuid Enter the {@code playerUuid} you want to get from the cache
     * @return returns from the {@link #USER_MAP} the <code>value</code>
     */
    public static User getUserByUuid( UUID playerUuid ) {
        if ( !USER_MAP.containsKey( playerUuid ) )
            USER_MAP.put( playerUuid, new User( playerUuid ) );

        return USER_MAP.get( playerUuid );
    }

    /**
     * Get the user who is cached by the {@code player} object
     *
     * @param player The {@code player} you want to get from the cache
     * @return returns {@link #getUserByUuid(UUID)}
     */
    public static User getUser( Player player ) {
        return getUserByUuid( player.getUniqueId() );
    }

    /**
     * Get the user who is cached by the {@code player} name
     *
     * @param playerName The {@code player} you want to get from the cache
     * @return returns {@link #getUser(Player)}
     */
    public static User getUserByName( String playerName ) {
        return getUser( Bukkit.getPlayer( playerName ) );
    }

    /**
     * Get all players from the cache
     *
     * @return returns the cached players
     */
    public static User[] getUsers() {
        final User[] users = new User[USER_MAP.size()];

        int count = 0;
        for ( Map.Entry<UUID, User> userEntry : USER_MAP.entrySet() ) {
            users[count] = userEntry.getValue();
            count++;
        }

        return users;
    }

    /**
     * Delete the {@code player} object from the cache
     *
     * @param player Define the player you want to delete from the cache
     */
    public static void deleteUser( Player player ) {
        USER_MAP.remove( player.getUniqueId() );
    }
}