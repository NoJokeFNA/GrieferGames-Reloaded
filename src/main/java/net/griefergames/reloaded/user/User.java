package net.griefergames.reloaded.user;

import lombok.Getter;
import lombok.Setter;

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