package net.griefergames.reloaded.utils.permissionssystem.impl;

import org.jetbrains.annotations.NotNull;
import net.griefergames.reloaded.utils.permissionssystem.IPermissionsSystem;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.concurrent.TimeUnit;

public class PermissionsExImpl implements IPermissionsSystem {

    @Override
    public String getPrefix( @NotNull Player player ) {
        final var user = PermissionsEx.getUser( player );
        return user.getPrefix();
    }

    @Override
    public void setGroup( Player player, @NotNull Player targetPlayer, @NotNull String groupName, long duration, @NotNull TimeUnit timeUnit ) {

    }
}
