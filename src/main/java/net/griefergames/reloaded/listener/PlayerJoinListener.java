package net.griefergames.reloaded.listener;

import com.earth2me.essentials.Essentials;
import net.ess3.api.IEssentials;
import net.griefergames.reloaded.GrieferGamesReloaded;
import net.griefergames.reloaded.GrieferGamesReloadedPlugin;
import net.griefergames.reloaded.build.ScoreboardBuilder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;

import java.text.NumberFormat;

public class PlayerJoinListener implements Listener {

    private final GrieferGamesReloadedPlugin plugin;

    private final IEssentials iEssentials;

    public PlayerJoinListener() {
        this.plugin = GrieferGamesReloaded.PLUGIN.getPlugin();

        this.iEssentials = (Essentials) this.plugin.getServer().getPluginManager().getPlugin("Essentials");
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final var player = event.getPlayer();

        final var essentialsUser = this.iEssentials.getUser(player.getUniqueId());
        final var numberFormat = NumberFormat.getNumberInstance().format(essentialsUser.getMoney());

        new ScoreboardBuilder("dummy", DisplaySlot.SIDEBAR, "§6§lGrieferGames", player)
                .addScore("§1", 14)
                .addScore("§7>> §3§lServer", 13)
                .addScore("§f" + this.plugin.getServer().getName(), 12)
                .addScore("§2", 11)
                .addScore("§7>> §3§lKontostand", 10)
                .addTeam("playerMoney", "§f" + numberFormat + "$", "§3", 9)
                .addScore("§4", 8)
                .addScore("§7>> §3§lOnline", 7)
                .addTeam("onlinePlayer", "§f" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(), "§5", 6)
                .addScore("§6", 6)
                .addScore("§7>> §3§lSpielzeit", 4)
                .addTeam("onlineTime", "§f" + "34 Stunden", "§7", 3)
                .addScore("§f" + "34 Stunden", 3)
                .addScore("§8", 2)
                .addScore("§7>> §3§lServer-Addresse", 1)
                .addScore("§fGrieferGames.net", 0);

        for (var onlinePlayer : Bukkit.getOnlinePlayers()) {
            ScoreboardBuilder.updateTeam(onlinePlayer, "onlinePlayer", "§f" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
        }
    }
}
