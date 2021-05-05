package net.griefergames.reloaded.utils.permissionssystem;

import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public interface IPermissionsSystem {

    /**
     * Get the prefix from the player
     * <li>LuckPerms</li>
     * <li>PermissionsEx</li>
     * <li>CloudNetV2</li>
     * <li>CloudNetV3</li>
     *
     * @param player the {@link Player} object
     */
    String getPrefix( @NonNull final Player player );

    void setGroup( final Player player, @NonNull final Player targetPlayer, @NonNull final String groupName, final long duration, @NonNull final TimeUnit timeUnit );
}
