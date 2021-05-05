package net.griefergames.reloaded.utils.prefix.impl;

import lombok.NonNull;
import net.griefergames.reloaded.utils.prefix.IPrefix;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsExImpl implements IPrefix {

    @Override
    public String getPrefix( @NonNull Player player ) {
        final PermissionUser user = PermissionsEx.getUser( player );
        return user.getPrefix();
    }
}
