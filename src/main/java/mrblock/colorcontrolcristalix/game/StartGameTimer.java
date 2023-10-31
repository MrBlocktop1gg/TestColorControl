package mrblock.colorcontrolcristalix.game;

import mrblock.colorcontrolcristalix.ColorControlCristalix;
import mrblock.colorcontrolcristalix.util.Alarm;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class StartGameTimer {
    ColorControlCristalix colorControlCristalix;
    TeamColor blueTeam = TeamColor.BLUE_TEAM;
    TeamColor redTeam = TeamColor.RED_TEAM;
    static GameState gameState;

    private final Plugin plugin;
    private final FileConfiguration config;
    private final Location[] spawnTeamBlueLocation;
    private final Location[] spawnTeamRedLocation;
    private int taskid;
    private int time;
    public static final byte MAX_PLAYER = 10;
    public static final byte MIN_PLAYER = 2;
    List<Player> players;
    World world = Bukkit.getWorld("world");

    public StartGameTimer(Plugin plugin, ColorControlCristalix colorControlCristalix) {

        config = plugin.getConfig();
        spawnTeamBlueLocation = new Location[4];
        spawnTeamRedLocation = new Location[4];

        for (int i = 0; i < 4; i++)
            spawnTeamBlueLocation[i] = new Location(world, config.getInt("coordinates.location_spawn_team_blue.x"),
                    config.getInt("coordinates.location_spawn_team_blue.y"),
                    config.getInt("coordinates.location_spawn_team_blue.z"));

        for (int i = 0; i < 4; i++)
            spawnTeamRedLocation[i] = new Location(world, config.getInt("coordinates.location_spawn_team_red.x"),
                    config.getInt("coordinates.location_spawn_team_red.y"),
                    config.getInt("coordinates.location_spawn_team_red.z"));

        players = new ArrayList<>();
        time = 10;

        this.colorControlCristalix = colorControlCristalix;
        this.plugin = plugin;

        timer();
    }

    public void timer() {
        taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            if (players.size() >= MIN_PLAYER) {

                for (Player player : Bukkit.getOnlinePlayers()) {

                    if (players.size() < MIN_PLAYER) {
                        Alarm.sendMessage("Таймер был завершен из-за недостатка игроков!");
                        Bukkit.getScheduler().cancelTask(taskid);
                    }

                    if (time <= 0) {
                        gameState = GameState.GAME;

                        if (TeamColor.BLUE_TEAM == blueTeam)
                            player.teleport(spawnTeamBlueLocation[players.size()]);
                        if (TeamColor.RED_TEAM == redTeam)
                            player.teleport(spawnTeamRedLocation[players.size()]);

                        Alarm.alarmTitle("", "§4§lИгра началась!", 1,1,1);

                        player.setHealth(20);
                        player.getInventory().clear();
                        player.setGameMode(GameMode.SURVIVAL);
                        player.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD));
                        player.getInventory().setItem(1, new ItemStack(Material.WOOD_PICKAXE));

                        new ScoreBoardGame(player.getUniqueId(), colorControlCristalix).createScoreBoard(player);
                        new GameTimer(plugin).gameTimer();

                        Bukkit.getScheduler().cancelTask(taskid);

                    } else {
                        Alarm.alarmTitle(config.getString("messages.timer_title")
                                        .replaceAll("<time>", "" + time)
                                , config.getString("messages.timer_subtitle")
                                , config.getInt("messages.timer_fadeln")
                                , config.getInt("messages.timer_stay")
                                , config.getInt("messages.timer_fadeOut"));
                        Alarm.alarmSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1,1);
                    }
                }
                time--;
            }
        }, 20, 20);
    }
}
