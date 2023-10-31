package mrblock.colorcontrolcristalix.listener;

import mrblock.colorcontrolcristalix.ColorControlCristalix;
import mrblock.colorcontrolcristalix.util.NameItems;
import mrblock.colorcontrolcristalix.mongodb.PlayerData;
import mrblock.colorcontrolcristalix.mongodb.PlayerStorage;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

public class PlayerConnectServer implements Listener {
    ColorControlCristalix colorControlCristalix;
    PlayerStorage playerStorage;
    protected FileConfiguration config;

    public PlayerConnectServer(Plugin plugin, ColorControlCristalix colorControlCristalix) {
        this.colorControlCristalix = colorControlCristalix;
        this.playerStorage = colorControlCristalix.getPlayerStorage();
        config = plugin.getConfig();

       // if (StartGameTimer.statusGame)
         //   updateScoreBoardLobby();

    }

    @EventHandler
    public void playerJoinServer(PlayerJoinEvent event) {

        /*
        Переменные
        */

        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();
        String itemPath = "items.player_add_item_compass";

        /*
        Предмет который мы выдаем
        */

        ItemStack compass = NameItems.createItem(
                Material.COMPASS,
                1,
                config.getString(itemPath + ".name"));

        createScoreBoardLobby(player);

        player.setGameMode(GameMode.ADVENTURE);

        inventory.clear();
        inventory.setItem(0, compass);
    }

    public void createScoreBoardLobby(Player player) {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("§b§lCCLobby", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("   §b§lColorControlLobby   ");
        Score money = objective.getScore("§6§lВаши Монеты: ");
        money.setScore(0);
        Score wins = objective.getScore("§2§lВаши Победы: ");
        wins.setScore(0);
        Score defeats = objective.getScore("§4§lВаши поражения: ");
        defeats.setScore(0);
        Score kills = objective.getScore("§c§lВаши Убийства: ");
        kills.setScore(0);
        Score deaths = objective.getScore("§c§lВаши Смерти: ");
        deaths.setScore(0);

        player.setScoreboard(scoreboard);
    }

    public void updateScoreBoardLobby() {
        Bukkit.getScheduler().runTaskTimer(colorControlCristalix, () -> {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                PlayerData playerData = playerStorage.getStats(onlinePlayer.getUniqueId());
                Scoreboard scoreboard = onlinePlayer.getScoreboard();
                Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
                Score money = objective.getScore("§6§lВаши Монеты: ");
                money.setScore(playerData.getMoney());
                Score wins = objective.getScore("§2§lВаши Победы: ");
                wins.setScore(playerData.getWins());
                Score defeats = objective.getScore("§4§lВаши Поражения: ");
                defeats.setScore(playerData.getDefeats());
                Score kills = objective.getScore("§c§lВаши Убийства: ");
                kills.setScore(playerData.getKills());
                Score deaths = objective.getScore("§c§lВаши Смерти: ");
                deaths.setScore(playerData.getDeaths());
            }
        }, 20, 20);
    }
}