package mrblock.colorcontrolcristalix.game;

import mrblock.colorcontrolcristalix.util.Alarm;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GameTimer {

    public static final int FINAL_TIME = 81;
    private final FileConfiguration config;
    private Plugin plugin;
    private int taskid;
    private BossBar bar;
    private int timeGame;

    public GameTimer(Plugin plugin) {

        config = plugin.getConfig();
        timeGame = 0;

        this.plugin = plugin;

        bar = Bukkit.getServer().createBossBar("§4§lДо сужение зоны: " + FINAL_TIME, BarColor.GREEN, BarStyle.SOLID);

        for (Player player : Bukkit.getOnlinePlayers()) {
            bar.addPlayer(player);
        }

        bar.setStyle(BarStyle.SOLID);

    }

    public void gameTimer() {

        taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            timeGame++;

            for (Player player : Bukkit.getOnlinePlayers()) {

                if (timeGame >= FINAL_TIME) {

                    Alarm.alarmSound(Sound.ENTITY_ENDERDRAGON_GROWL, 1,0);
                    Alarm.sendMessage("§4§lЗона начала своё сужение!");

                    bar.removePlayer(player);

                    Bukkit.getScheduler().cancelTask(taskid);

                } else {

                    bar.setProgress((float)(FINAL_TIME-timeGame)/FINAL_TIME);
                    bar.setTitle("§4§lДо сужение зоны: " + (FINAL_TIME-timeGame));

                }
            }
        }, 20, 20);
    }
}
