package net.griefergames.reloaded.utils.permissionssystem.impl;

import de.dytanic.cloudnet.api.CloudAPI;
import net.griefergames.reloaded.utils.chat.ChatUtil;
import net.griefergames.reloaded.utils.permissionssystem.IPermissionsSystem;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class CloudNetV2Impl implements IPermissionsSystem {

    @Override
    public String getPrefix(@NotNull Player player) {
        final var permissionEntity = CloudAPI
                .getInstance()
                .getOnlinePlayer(player.getUniqueId())
                .getPermissionEntity()
                .getHighestPermissionGroup(CloudAPI.getInstance().getPermissionPool());

        final var playerName = player.getName();

        switch ("asd") {
            case "display":
                return ChatUtil.sendColoredMessage(permissionEntity.getDisplay() + playerName);

            case "prefix":
                return ChatUtil.sendColoredMessage(permissionEntity.getPrefix() + playerName);

            default:
                throw new UnsupportedOperationException("§4Unsupported value in §cchat_settings.yml » chat.cloudnet.use » " + "asd");
        }
    }

    @Override
    public void setGroup(Player player, @NotNull Player targetPlayer, @NotNull String groupName, long duration, @NotNull TimeUnit timeUnit) {

    }
}
