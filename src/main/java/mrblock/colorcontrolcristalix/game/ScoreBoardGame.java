package mrblock.colorcontrolcristalix.game;

import mrblock.colorcontrolcristalix.ColorControlCristalix;
import mrblock.colorcontrolcristalix.mongodb.PlayerStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

import java.util.UUID;

public class ScoreBoardGame extends PlayerDataGame {
    Plugin plugin;
    ColorControlCristalix colorControlCristalix;
    PlayerStorage playerStorage;

    public ScoreBoardGame(UUID uuid, ColorControlCristalix colorControlCristalix) {

        this.colorControlCristalix = colorControlCristalix;
        this.playerStorage = colorControlCristalix.getPlayerStorage();
        this.plugin = colorControlCristalix;

       if (StartGameTimer.gameState == GameState.GAME)
           updateScoreBoardGame();
    }

    public void createScoreBoard(Player player) {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("§b§lCCGame", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("   §b§lColorControl   ");
        Score lifePlayer = objective.getScore("§c§lЖизни команды: ");
        lifePlayer.setScore(getLifeTeam());
        Score kills = objective.getScore("§c§lВаши Убийства: ");
        kills.setScore(0);

        player.setScoreboard(scoreboard);
    }

    public void updateScoreBoardGame() {
        Bukkit.getScheduler().runTaskTimer(colorControlCristalix, () -> {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                Scoreboard scoreboard = onlinePlayer.getScoreboard();
                Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
                Score lifeTeam = objective.getScore("§c§lЖизни команды: ");
                lifeTeam.setScore(getLifeGameTeam());
                Score kills = objective.getScore("§c§lВаши Убийства: ");
                kills.setScore(getKills());
            }
        }, 20, 20);
    }
}