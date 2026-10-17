package game;

import city.cs.engine.*;

/**
 * Represents a collectible coin in the game.
 * When the player touches the coin, collect() is called to remove it
 * from the world and mark it as collected.
 * Levels use isCollected() to check whether their completion condition is met.
 */
public class Coin extends StaticBody {

    // Tracks whether this coin has been picked up by the player
    private boolean collected = false;

    /**
     * Creates a coin with a circular collision shape and a visual image.
     *
     * @param world the physics world this coin belongs to
     */
    public Coin(World world) {
        super(world, new CircleShape(0.5f));
        addImage(new BodyImage("data/coin.png", 3f));
    }

    /**
     * Marks the coin as collected and removes it from the world.
     * Called from the Player's collision listener.
     */
    public void collect() {
        collected = true;
        destroy();
    }

    /** Returns true if the player has already collected this coin. */
    public boolean isCollected() {
        return collected;
    }
}
