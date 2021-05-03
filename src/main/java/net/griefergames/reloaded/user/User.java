package net.griefergames.reloaded.user;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class User {

    private final UUID playerUuid;

    /**
     * Instantiates a new Cache user.
     *
     * @param playerUuid the player uuid
     */
    public User( UUID playerUuid ) {
        this.playerUuid = playerUuid;
    }
}