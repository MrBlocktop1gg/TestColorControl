package mrblock.colorcontrolcristalix;

import lombok.Getter;
import lombok.Setter;
import mrblock.colorcontrolcristalix.game.GamePlayer;
import mrblock.colorcontrolcristalix.game.TestCommandStartTimer;
import mrblock.colorcontrolcristalix.listener.CancelledListener;
import mrblock.colorcontrolcristalix.listener.PlayerConnectServer;
import mrblock.colorcontrolcristalix.listener.SelectTeam;
import mrblock.colorcontrolcristalix.mongodb.PlayerStorage;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

@Getter
@Setter

public final class ColorControlCristalix extends JavaPlugin {
    Plugin plugin;
    UUID uuid;
    PlayerStorage playerStorage;

    @Override
    public void onEnable() {
        plugin = this;
        playerStorage = new PlayerStorage();

        getCommand("startTimerTest").setExecutor(new TestCommandStartTimer(plugin, this));

        registerListeners(
                new PlayerConnectServer(plugin, this),
                new SelectTeam(plugin));
                new GamePlayer(uuid);
                new CancelledListener();
    }

    public void registerListeners(Listener... listeners) {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
