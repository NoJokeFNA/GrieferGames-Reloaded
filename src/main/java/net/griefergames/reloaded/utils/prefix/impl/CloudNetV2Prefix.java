package net.griefergames.reloaded.utils.prefix.impl;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.player.permission.PermissionGroup;
import lombok.NonNull;
import net.griefergames.reloaded.utils.chat.ChatUtil;
import net.griefergames.reloaded.utils.prefix.IPrefix;
import org.bukkit.entity.Player;

public class CloudNetV2Prefix implements IPrefix {

    @Override
    public String getPrefix( @NonNull Player player ) {
        final PermissionGroup permissionEntity = CloudAPI
                .getInstance()
                .getOnlinePlayer( player.getUniqueId() )
                .getPermissionEntity()
                .getHighestPermissionGroup( CloudAPI.getInstance().getPermissionPool() );

        switch ( "asd" ) {
            case "display":
                return ChatUtil.sendColoredMessage( permissionEntity.getDisplay() + player.getName() );

            case "prefix":
                return ChatUtil.sendColoredMessage( permissionEntity.getPrefix() + player.getName() );

            default:
                throw new UnsupportedOperationException( "§4Unsupported value in §cchat_settings.yml » chat.cloudnet.use » " + "asd" );
        }
    }
}
