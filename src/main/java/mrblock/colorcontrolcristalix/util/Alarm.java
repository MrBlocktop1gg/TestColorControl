package mrblock.colorcontrolcristalix.util;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class Alarm {
    public static void sendMessage(String message) {
        Bukkit.getOnlinePlayers().forEach(player ->
                player.sendMessage(message));
    }

    public static void alarmSound(Sound sound, float volume, float pitch) {
        Bukkit.getOnlinePlayers().forEach(player ->
                player.playSound(player.getLocation(), sound, volume, pitch));
    }

    public static void alarmTitle(String title, String subtitle, int fadeln, int stay, int fadeOut) {
        Bukkit.getOnlinePlayers().forEach(player ->
                player.sendTitle(title, subtitle));
    }
}
