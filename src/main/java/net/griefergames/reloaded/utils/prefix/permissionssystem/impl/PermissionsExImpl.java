package net.griefergames.reloaded.utils.prefix.permissionssystem.impl;

import lombok.val;
import net.griefergames.reloaded.utils.prefix.permissionssystem.IPermissionsSystem;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.concurrent.TimeUnit;

public class PermissionsExImpl implements IPermissionsSystem {
  @Override
  public String getPrefix(@NotNull Player player) {
    val user = PermissionsEx.getUser(player);
    return user.getPrefix();
  }

  @Override
  public void setGroup(
      Player player,
      @NotNull Player targetPlayer,
      @NotNull String groupName,
      long duration,
      @NotNull TimeUnit timeUnit) {}
}
