package net.griefergames.reloaded.build;

import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author NoJokeFNA
 * @version 1.0.0
 */
public class ScoreboardBuilder {
    private final Scoreboard scoreboard;
    private final Objective objective;
    private final Player player;

    private Team team;

    /**
     * Create a new {@link Scoreboard}
     *
     * @param objectiveName the {@code objectiveName} of the new {@link Scoreboard} - {@link Scoreboard#registerNewObjective(String, String, String)}
     * @param displaySlot   the {@link DisplaySlot} you wanna use
     * @param displayName   the {@code displayName} of the {@code scoreboard}
     * @param player        the {@link Player} object
     */
    public ScoreboardBuilder(@NotNull final String objectiveName, @NotNull final DisplaySlot displaySlot, @NotNull final String displayName, @NotNull final Player player) {
        this.player = player;

        player.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());

        this.scoreboard = player.getScoreboard();
        this.objective = this.scoreboard.registerNewObjective(objectiveName, "dummy", coloredMessage(displayName));

        this.objective.setDisplaySlot(displaySlot);
    }

    /**
     * Remove the {@code entry} from the specified {@code team}
     *
     * @param player set the {@code player} from whom you want to get the {@code scoreboard}
     * @param team   specify the {@code team} from which you want to remove the {@code entry}
     * @param entry  set the {@code entry} from the {@code team}
     */
    public static void removeEntry(@NotNull final Player player, @NotNull final String team, @NotNull final String entry) {
        Objects.requireNonNull(player.getScoreboard().getTeam(team)).removeEntry(entry);
    }

    /**
     * Update a team with a {@code prefix}
     *
     * @param player set the {@code player} from whom you want to update the {@code scoreboard}
     * @param team   set the {@code team} you want to update
     * @param prefix set the {@code prefix} from the {@code team}
     */
    public static void updateTeam(@NotNull final Player player, @NotNull final String team, @NotNull final String prefix) {
        Objects.requireNonNull(player.getScoreboard().getTeam(team)).setPrefix(staticColoredMessage(prefix));
    }

    /**
     * Update a team with a {@code prefix} and {@code suffix}
     *
     * @param player set the {@code player} from whom you want to update the {@code scoreboard}
     * @param team   set the {@code team} you want to update
     * @param prefix set the {@code prefix} from the {@code team}
     * @param suffix set the {@code suffix} from the {@code team}
     */
    public static void updateTeam(@NotNull final Player player, @NotNull final String team, @NotNull final String prefix, @NotNull final String suffix) {
        val playerTeam = player.getScoreboard().getTeam(team);
        assert playerTeam != null : "Team cannot be null";

        playerTeam.setPrefix(staticColoredMessage(prefix));
        playerTeam.setSuffix(staticColoredMessage(suffix));
    }

    private static String staticColoredMessage(@NotNull final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Add a team to the {@code scoreboard}
     *
     * @param prefix set the {@code prefix} of the {@code score}
     * @param score  set the {@code score} of the {@code prefix}
     * @return returns the method
     */
    public ScoreboardBuilder addScore(@NotNull final String prefix, final int score) {
        this.objective.getScore(prefix).setScore(score);
        return this;
    }

    /**
     * Add a team to the {@code scoreboard}
     *
     * @param teamName set the {@code teamName} you want
     * @param prefix   set the {@code prefix} of the {@code team}
     * @param entry    set the {@code entry} of the {@code scoreboard}. Please use only §1, §2 (...), or {@link ChatColor}
     * @param score    set the {@code score} in the following order
     * @return returns the method
     */
    public ScoreboardBuilder addTeam(@NotNull final String teamName, @NotNull final String prefix, @NotNull final String entry, final int score) {
        this.team = this.scoreboard.registerNewTeam(teamName);
        this.team.setPrefix(coloredMessage(prefix));
        this.team.addEntry(entry);
        this.objective.getScore(entry).setScore(score);
        return this;
    }

    /**
     * Add a team to the {@code scoreboard}
     *
     * @param teamName set the {@code teamName} you want
     * @param prefix   set the {@code prefix} of the {@code team}
     * @param suffix   set the {@code suffix} of the {@code team}
     * @param entry    set the {@code entry} of the {@code scoreboard}. Please use only §1, §2, §3 (...), or {@link ChatColor}
     * @param score    set the {@code score} in the following order
     * @return returns the method
     */
    public ScoreboardBuilder addTeam(@NotNull final String teamName, @NotNull final String prefix, @NotNull final String suffix, @NotNull final String entry, final int score) {
        this.team = this.scoreboard.registerNewTeam(teamName);
        this.team.setPrefix(coloredMessage(prefix));
        this.team.setSuffix(coloredMessage(suffix));
        this.team.addEntry(entry);
        this.objective.getScore(entry).setScore(score);
        return this;
    }

    /**
     * Finally send the scoreboard
     */
    public void sendScoreboard() {
        this.player.setScoreboard(this.scoreboard);
    }

    private String coloredMessage(@NotNull final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
