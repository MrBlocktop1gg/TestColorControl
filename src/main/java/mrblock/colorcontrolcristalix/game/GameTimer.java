package mrblock.colorcontrolcristalix.game;

import mrblock.colorcontrolcristalix.ColorControlCristalix;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class GameTimer {
    private final Plugin plugin;
    private final FileConfiguration config;
    private final Location[] spawnTeamLocation;
    private int taskid;
    private int time;
    public static boolean statusGame = false;
    public final byte MAX_PLAYER = 10;
    public final byte MIN_PLAYER = 2;
    List<Player> players;
    World world = Bukkit.getWorld("world");
    ColorControlCristalix colorControlCristalix;

    public GameTimer(Plugin plugin, ColorControlCristalix colorControlCristalix) {
        config = plugin.getConfig();
        spawnTeamLocation = new Location[10];

        for (int i = 0; i < 10; i++)
            spawnTeamLocation[i] = new Location(world, config.getInt("coordinates.location_spawn_team_blue.x"),
                    config.getInt("coordinates.location_spawn_team_blue.y"),
                    config.getInt("coordinates.location_spawn_team_blue.z"));

        players = new ArrayList<>();
        time = 10;

        this.colorControlCristalix = colorControlCristalix;
        this.plugin = plugin;

        timer();
    }

    public void timer() {
        taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            if (players.size() <= MIN_PLAYER) {
                for (Player player : Bukkit.getOnlinePlayers()) {

                    if (time <= 0) {

                        statusGame = true;
                        player.sendTitle("", "§4§lИгра началась!");
                        player.setHealth(player.getMaxHealth());
                        player.sendMessage("Статус игры: " + statusGame);
                        player.getInventory().clear();
                        player.setGameMode(GameMode.SURVIVAL);
                        player.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD));
                        player.getInventory().setItem(1, new ItemStack(Material.WOOD_PICKAXE));
                        playerTeleport();

                        new ScoreBoardGame(player.getUniqueId(), colorControlCristalix).createScoreBoard(player);
                        Bukkit.getScheduler().cancelTask(taskid);

                    } else {
                        player.sendMessage("Статус игры: " + statusGame);
                        player.sendTitle(config.getString("messages.timer_title")
                                        .replaceAll("<time>", "" + time)
                                , config.getString("messages.timer_subtitle"));
                    }
                }
                time--;
            }
        }, 20, 20);
    }

    public void playerTeleport() {
        for (byte i = 0; i < players.size() / 2; i++)
            players.get(i).teleport(spawnTeamLocation[i]);
    }
}
