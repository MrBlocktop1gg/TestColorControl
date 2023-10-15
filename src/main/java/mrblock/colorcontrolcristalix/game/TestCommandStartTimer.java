package mrblock.colorcontrolcristalix.game;

import mrblock.colorcontrolcristalix.ColorControlCristalix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class TestCommandStartTimer implements CommandExecutor {
    Plugin plugin;
    ColorControlCristalix colorControlCristalix;
    private final FileConfiguration config;
    public TestCommandStartTimer(Plugin plugin, ColorControlCristalix colorControlCristalix) {
        this.colorControlCristalix = colorControlCristalix;
        this.plugin = plugin;

        config = plugin.getConfig();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        new GameTimer(plugin, colorControlCristalix);
        return true;
    }
}
