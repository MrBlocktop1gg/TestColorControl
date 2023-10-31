package mrblock.colorcontrolcristalix.manager;

import mrblock.colorcontrolcristalix.game.GameState;
import mrblock.colorcontrolcristalix.game.GameWaiting;

public class GameManager {
    private final GameWaiting gameWaiting;
    GameState gameState;
    public GameManager() {
        gameWaiting = new GameWaiting();
    }
    public GameWaiting waiting() {
        return gameWaiting;
    }
}
