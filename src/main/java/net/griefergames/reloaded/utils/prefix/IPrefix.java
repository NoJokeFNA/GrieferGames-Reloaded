package net.griefergames.reloaded.utils.prefix;

import lombok.NonNull;
import org.bukkit.entity.Player;

public interface IPrefix {

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
}
