package net.griefergames.reloaded.listener;

import net.ess3.api.events.UserBalanceUpdateEvent;
import net.griefergames.reloaded.build.ScoreboardBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.NumberFormat;

public class UserBalanceUpdateListener implements Listener {

    @EventHandler
    public void onUserBalanceUpdate( final UserBalanceUpdateEvent event ) {
        final Player player = event.getPlayer();

        final String numberFormat = NumberFormat.getNumberInstance().format( event.getNewBalance() );
        ScoreboardBuilder.updateTeam( player, "playerMoney", "§f" + numberFormat + "$" );
    }
}
