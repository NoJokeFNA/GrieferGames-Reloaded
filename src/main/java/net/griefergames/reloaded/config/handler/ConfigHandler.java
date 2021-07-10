package net.griefergames.reloaded.config.handler;

import lombok.Getter;
import net.griefergames.reloaded.config.PropertiesReader;

@Getter
public class ConfigHandler {
  private final PropertiesReader propertiesReader;

  public ConfigHandler() {
    this.propertiesReader = new PropertiesReader();
  }
}
