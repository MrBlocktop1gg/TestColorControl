package mrblock.colorcontrolcristalix.mongodb;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerData {
    private final UUID uuid;
    private int money = 0;
    private int wins = 0;
    private int defeats = 0;
    private int kills = 0;
    private int deaths = 0;


    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }
    public void addMoney() {
        this.money=10;
    }
    public void addWins() {
        this.wins++;
    }
    public void addDefeats() {
        this.defeats++;
    }
    public void addKills() {
        this.kills++;
    }
    public void addDeaths() {
        this.deaths++;
    }
}
