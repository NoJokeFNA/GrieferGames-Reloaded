package net.griefergames.reloaded.utils.permissionssystem.impl;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionManagement;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import lombok.NonNull;
import net.griefergames.reloaded.utils.chat.ChatUtil;
import net.griefergames.reloaded.utils.permissionssystem.IPermissionsSystem;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class CloudNetV3Impl implements IPermissionsSystem {

    @Override
    public String getPrefix( @NonNull Player player ) {
        final CloudNetDriver cloudNetDriver = CloudNetDriver.getInstance();

        final IPermissionManagement iPermissionManagement = cloudNetDriver.getPermissionManagement();
        final IPermissionUser iPermissionUser = iPermissionManagement.getUser( player.getUniqueId() );
        if ( iPermissionUser == null )
            return "User cannot be null";

        final IPermissionGroup iPermissionGroup = iPermissionManagement.getHighestPermissionGroup( iPermissionUser  );

        final String playerName = player.getName();

        switch ( "asd" ) {
            case "display":
                return ChatUtil.sendColoredMessage( iPermissionGroup.getDisplay() + playerName );

            case "prefix":
                return ChatUtil.sendColoredMessage( iPermissionGroup.getPrefix() + playerName );

            default:
                throw new UnsupportedOperationException( "§4Unsupported value in §cchat_settings.yml » chat.cloudnet.use » " + "" );
        }
    }

    @Override
    public void setGroup( Player player, @NonNull Player targetPlayer, @NonNull String groupName, long duration, @NonNull TimeUnit timeUnit ) {

    }
}
