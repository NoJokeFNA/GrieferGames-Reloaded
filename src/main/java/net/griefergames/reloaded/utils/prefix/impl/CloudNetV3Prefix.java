package net.griefergames.reloaded.utils.prefix.impl;

import lombok.NonNull;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import net.griefergames.reloaded.utils.chat.ChatUtil;
import net.griefergames.reloaded.utils.prefix.IPrefix;
import org.bukkit.entity.Player;

public class CloudNetV3Prefix implements IPrefix {

    @Override
    public String getPrefix( @NonNull Player player ) {
        final IPermissionGroup iPermissionGroup = CloudNetDriver
                .getInstance()
                .getPermissionManagement()
                .getHighestPermissionGroup( CloudNetDriver.getInstance().getPermissionManagement().getUser( player.getUniqueId() ) );

        switch ( "asd" ) {
            case "display":
                return ChatUtil.sendColoredMessage( iPermissionGroup.getDisplay() + player.getName() );

            case "prefix":
                return ChatUtil.sendColoredMessage( iPermissionGroup.getPrefix() + player.getName() );

            default:
                throw new UnsupportedOperationException( "§4Unsupported value in §cchat_settings.yml » chat.cloudnet.use » " + "" );
        }
    }
}
