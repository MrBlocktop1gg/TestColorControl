package mrblock.colorcontrolcristalix.game;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

public class GamePlayer extends PlayerDataGame implements Listener {
    public GamePlayer(UUID uuid) {
        super(uuid);
    }

    @EventHandler
    public void killerKilledPlayer(PlayerDeathEvent event) {

        Player player = event.getEntity();
        Player killer = player.getKiller();

        if (killer != player)
            setRemoveLifePlayer();

        if (killer == player)
            setKills();

        if (getRemoveLifePlayer() <= 0) {

          //  new PlayerConnectServer(plugin, colorControlCristalix).createScoreBoardLobby(player);
          //  playerData.addDefeats();
            player.setGameMode(GameMode.SPECTATOR);
            player.sendTitle("", "§c§lПоражение!", 15, 50, 20);
        }
    }
}
