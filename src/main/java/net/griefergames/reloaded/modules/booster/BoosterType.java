package net.griefergames.reloaded.modules.booster;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoosterType {
  BREAK("Break-Booster", 15),
  DROP("Drop-Booster", 30),
  FLY("Fly-Booster", 15),
  MOB("Mob-Booster", 15),
  XP("XP-Booster", 30);

  private final String boosterName;
  private final int defaultCooldown;
}
