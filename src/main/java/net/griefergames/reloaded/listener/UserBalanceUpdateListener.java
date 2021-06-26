package net.griefergames.reloaded.listener;

import lombok.val;
import net.ess3.api.events.UserBalanceUpdateEvent;
import net.griefergames.reloaded.build.ScoreboardBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;

public class UserBalanceUpdateListener implements Listener {
    @EventHandler
    public void onUserBalanceUpdate(@NotNull final UserBalanceUpdateEvent event) {
        val player = event.getPlayer();

        val numberFormat = NumberFormat.getNumberInstance().format(event.getNewBalance());
        ScoreboardBuilder.updateTeam(player, "playerMoney", "§f" + numberFormat + "$");
    }
}
