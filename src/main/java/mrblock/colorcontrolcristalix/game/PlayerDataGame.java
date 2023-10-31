package mrblock.colorcontrolcristalix.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
@Setter
public class PlayerDataGame {
    List<Player> players;
    private int removeLifePlayer = 3;
    private int lifeTeam;
    private int kills = 0;
    public void setRemoveLifePlayer() {
        this.removeLifePlayer--;
    }
    public int getRemoveLifePlayer() {
        return removeLifePlayer;
    }

    public void setKills() {
        this.kills++;
    }
    public int getKills() {
        return kills;
    }
    public int getLifeGameTeam() {
        switch (players.size()) {
            case 1:
                lifeTeam = 3;
                break;
            case 2:
                lifeTeam = 6;
                break;
            case 3:
                lifeTeam = 9;
                break;
            case 4:
                lifeTeam = 12;
                break;
            case 5:
                lifeTeam = 15;
                break;
            default:
                Bukkit.broadcastMessage("Server restart!");
                Bukkit.getServer().shutdown();
        }
        return lifeTeam;
    }
}
