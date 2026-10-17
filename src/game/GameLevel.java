package game;

import city.cs.engine.World;
import org.jbox2d.common.Vec2;

/**
 * Abstract base class for all game levels.
 * Each level is a physics World containing a player, platforms, enemies, and coins.
 * Concrete subclasses (Level1–Level3) define the layout and completion condition.
 *
 * Levels are wider than the visible window (walls at ±30) so the camera can scroll.
 */
public abstract class GameLevel extends World {

    protected Player player;
    protected Game game;

    public GameLevel(Game game) {
        this.game = game;

        player = new Player(this, game);
        player.setPosition(getStartPosition());

        // Invisible walls at the level edges — wider than the screen to allow scrolling
        Wall wallLeft = new Wall(this, 0.5f, 30f);
        wallLeft.setPosition(new Vec2(-30f, 0f));

        Wall wallRight = new Wall(this, 0.5f, 30f);
        wallRight.setPosition(new Vec2(30f, 0f));
    }

    public Player getPlayer() { return player; }

    public abstract Vec2 getStartPosition();

    public abstract boolean isComplete();

    public abstract String getBackgroundFile();
}
