package mrblock.colorcontrolcristalix.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class CancelledListener implements Listener {
    @EventHandler
    public void offRain(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void offHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void noMobSpawn(EntitySpawnEvent event) {
        event.setCancelled(true);
    }
}
