package net.griefergames.reloaded.utils.prefix.permissionssystem.impl;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import lombok.val;
import net.griefergames.reloaded.utils.chat.ChatUtil;
import net.griefergames.reloaded.utils.prefix.permissionssystem.IPermissionsSystem;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class CloudNetV3Impl implements IPermissionsSystem {

    @Override
    public String getPrefix(@NotNull Player player) {
        val cloudNetDriver = CloudNetDriver.getInstance();

        val iPermissionManagement = cloudNetDriver.getPermissionManagement();
        val iPermissionUser = iPermissionManagement.getUser(player.getUniqueId());
        if (iPermissionUser == null)
            return "User cannot be null";

        val iPermissionGroup = iPermissionManagement.getHighestPermissionGroup(iPermissionUser);
        val playerName = player.getName();

        return switch ("asd") {
            case "display" -> ChatUtil.sendColoredMessage(iPermissionGroup.getDisplay() + playerName);
            case "prefix" -> ChatUtil.sendColoredMessage(iPermissionGroup.getPrefix() + playerName);
            default -> throw new UnsupportedOperationException("§4Unsupported value in §cchat_settings.yml » chat.cloudnet.use » " + "");
        };
    }

    @Override
    public void setGroup(Player player, @NotNull Player targetPlayer, @NotNull String groupName, long duration, @NotNull TimeUnit timeUnit) {

    }
}
