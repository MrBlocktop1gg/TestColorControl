package mrblock.colorcontrolcristalix.mongodb;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerStorage {
    private final Map<UUID, PlayerData> uuidPlayerDataMap = new HashMap<>();
    public PlayerData getStats(UUID uuid) {
        return uuidPlayerDataMap.get(uuid);
    }
    public PlayerData addStats(UUID uuid) {
        PlayerData playerData = new PlayerData(uuid);
        uuidPlayerDataMap.put(uuid, playerData);
        return playerData;
    }
}
