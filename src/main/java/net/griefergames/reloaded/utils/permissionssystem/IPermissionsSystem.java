package net.griefergames.reloaded.utils.permissionssystem;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public interface IPermissionsSystem {

    /**
     * Get the prefix from the player
     * <ul>
     *      <li>LuckPerms</li>
     *      <li>PermissionsEx</li>
     *      <li>CloudNetV2</li>
     *      <li>CloudNetV3</li>
     * </ul>
     *
     * @param player the {@link Player} object
     */
    String getPrefix(@NotNull final Player player);

    void setGroup(final Player player, @NotNull final Player targetPlayer, @NotNull final String groupName, final long duration, @NotNull final TimeUnit timeUnit);
}
