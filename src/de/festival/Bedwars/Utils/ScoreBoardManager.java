package de.festival.Bedwars.Utils;

import de.festival.Bedwars.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreBoardManager {

    Main plugin;

    public ScoreBoardManager(Main plugin) {
        this.plugin = plugin;
    }

    public void openScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("teams", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.AQUA+"Bedwars");

        objective.getScore(this.plugin.getServer().getWorlds().get(0).getName()).setScore(20);
        objective.getScore(player.getDisplayName()).setScore(19);
        objective.getScore(" ").setScore(18);
        objective.getScore(ChatColor.GOLD + "Kills").setScore(17);
        objective.getScore("-1").setScore(16);
        objective.getScore("  ").setScore(15);

        objective.getScore(this.plugin.teams.get("red").teamColor.chatColor + "❤ Rot").setScore(this.plugin.teams.get("red").getActiveMembers());
        objective.getScore(this.plugin.teams.get("yellow").teamColor.chatColor + "❤ Gelb").setScore(this.plugin.teams.get("yellow").getActiveMembers());
        objective.getScore(this.plugin.teams.get("green").teamColor.chatColor + "❤ Grün").setScore(this.plugin.teams.get("green").getActiveMembers());
        objective.getScore(this.plugin.teams.get("blue").teamColor.chatColor + "❤ Blau").setScore(this.plugin.teams.get("blue").getActiveMembers());

        player.setScoreboard(board);
    }

    public void updateScoreboard() {
        for (Player player: this.plugin.getServer().getOnlinePlayers()) {
            Scoreboard board = player.getScoreboard();
            Objective objective = board.getObjective("teams");

            objective.getScore(this.plugin.teams.get("red").teamColor.chatColor + "❤ Rot").setScore(this.plugin.teams.get("red").getActiveMembers());
            objective.getScore(this.plugin.teams.get("yellow").teamColor.chatColor + "❤ Gelb").setScore(this.plugin.teams.get("yellow").getActiveMembers());
            objective.getScore(this.plugin.teams.get("green").teamColor.chatColor + "❤ Grün").setScore(this.plugin.teams.get("green").getActiveMembers());
            objective.getScore(this.plugin.teams.get("blue").teamColor.chatColor + "❤ Blau").setScore(this.plugin.teams.get("blue").getActiveMembers());

            player.setScoreboard(board);
        }
    }
}
