package net.griefergames.reloaded.utils.permissionssystem.impl;

import lombok.NonNull;
import net.griefergames.reloaded.utils.permissionssystem.IPermissionsSystem;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.concurrent.TimeUnit;

public class PermissionsExImpl implements IPermissionsSystem {

    @Override
    public String getPrefix( @NonNull Player player ) {
        final PermissionUser user = PermissionsEx.getUser( player );
        return user.getPrefix();
    }

    @Override
    public void setGroup( Player player, @NonNull Player targetPlayer, @NonNull String groupName, long duration, @NonNull TimeUnit timeUnit ) {

    }
}
