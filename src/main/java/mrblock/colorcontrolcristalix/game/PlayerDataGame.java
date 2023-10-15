package mrblock.colorcontrolcristalix.game;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerDataGame {
    private final UUID uuid;
    private int removeLifePlayer = 3;
    private int kills = 0;
    public PlayerDataGame(UUID uuid) {
        this.uuid = uuid;
    }
    public void setRemoveLifePlayer() {
        this.removeLifePlayer--;
    }
    public Integer getRemoveLifePlayer() {
        return removeLifePlayer;
    }

    public void setKills() {
        this.kills++;
    }
    public Integer getKills() {
        return kills;
    }
}
