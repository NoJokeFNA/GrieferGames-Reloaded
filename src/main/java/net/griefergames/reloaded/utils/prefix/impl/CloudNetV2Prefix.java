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

        final String playerName = player.getName();

        switch ( "asd" ) {
            case "display":
                return ChatUtil.sendColoredMessage( permissionEntity.getDisplay() + playerName );

            case "prefix":
                return ChatUtil.sendColoredMessage( permissionEntity.getPrefix() + playerName );

            default:
                throw new UnsupportedOperationException( "§4Unsupported value in §cchat_settings.yml » chat.cloudnet.use » " + "asd" );
        }
    }
}
