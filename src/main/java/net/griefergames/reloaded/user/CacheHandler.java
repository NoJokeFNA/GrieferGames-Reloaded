package net.griefergames.reloaded.user;

import com.google.common.collect.Maps;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class CacheHandler {

    private static final Map<UUID, CacheUser> USER_MAP = Maps.newHashMap();

    /**
     * Get the user who is cached by the {@link UUID} object
     *
     * @param playerUuid enter the {@code playerUuid} you want to get from the cache
     *
     * @return returns from the {@link #USER_MAP} the <code>value</code>
     */
    public static CacheUser getUserByUuid( @NonNull UUID playerUuid ) {
        if ( !USER_MAP.containsKey( playerUuid ) )
            USER_MAP.put( playerUuid, new CacheUser( playerUuid ) );

        return USER_MAP.get( playerUuid );
    }

    /**
     * Get the user who is cached by the {@code player} object
     *
     * @param player the {@code player} you want to get from the cache
     *
     * @return returns {@link #getUserByUuid(UUID)}
     */
    public static CacheUser getUser( @NonNull Player player ) {
        return getUserByUuid( player.getUniqueId() );
    }

    /**
     * Get the user who is cached by the {@code player} name
     *
     * @param playerName the {@code player} you want to get from the cache
     *
     * @return returns {@link #getUser(Player)}
     */
    public static CacheUser getUserByName( @NonNull String playerName ) {
        return getUser( Bukkit.getPlayer( playerName ) );
    }

    /**
     * Get all players from the cache
     *
     * @return returns the cached players
     */
    public static CacheUser[] getUsers() {
        final CacheUser[] cacheUsers = new CacheUser[USER_MAP.size()];

        int count = 0;
        for ( Map.Entry<UUID, CacheUser> userEntry : USER_MAP.entrySet() ) {
            cacheUsers[count] = userEntry.getValue();
            count++;
        }

        return cacheUsers;
    }

    /**
     * Delete the {@code player} object from the cache
     *
     * @param player define the player you want to delete from the cache
     */
    public static void deleteUser( @NonNull Player player ) {
        USER_MAP.remove( player.getUniqueId() );
    }
}