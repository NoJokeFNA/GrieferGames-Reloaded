package net.griefergames.reloaded.cache;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CacheUser {
  private final UUID playerUuid;

  /* Bank */
  private int bankMoney;

  /**
   * Instantiates a new Cache user.
   *
   * @param playerUuid the player uuid
   */
  public CacheUser(UUID playerUuid) {
    this.playerUuid = playerUuid;
  }
}
