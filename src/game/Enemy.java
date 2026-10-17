package game;

import city.cs.engine.*;
import java.io.File;

/**
 * Represents an enemy in the game.
 * Extends StaticBody so it does not respond to gravity or physics forces.
 * Each enemy has a fixed collision box and a visual image.
 * Movement is handled separately by EnemyMove.
 */
public class Enemy extends StaticBody {

    // Tracks whether this enemy has been hit and destroyed
    private boolean destroyed = false;

    /**
     * Creates an enemy with a collision box and a visual image.
     *
     * @param world     the physics world this enemy belongs to
     * @param imageFile path to the image file for the enemy sprite
     * @param size      height of the displayed image
     */
    public Enemy(World world, String imageFile, float size) {
        super(world);

        // Give the enemy a solid 1x1 collision box
        new SolidFixture(this, new BoxShape(1f, 1f));

        // Only attach the image if the file actually exists
        File file = new File(imageFile);
        if (!file.exists()) {
            System.out.println("Enemy image not found: " + file.getAbsolutePath());
        } else {
            addImage(new BodyImage(imageFile, size));
        }
    }

    /** Returns true if this enemy has been destroyed. */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Marks the enemy as destroyed and removes it from the world.
     * Overrides StaticBody.destroy() to also set the destroyed flag.
     */
    @Override
    public void destroy() {
        destroyed = true;
        super.destroy();
    }
}
