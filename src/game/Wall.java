package game;

import city.cs.engine.*;

/**
 * An invisible static wall used to stop the player walking off the screen edges.
 * Two walls are created in GameLevel — one on the left edge, one on the right.
 */
public class Wall extends StaticBody {

    /**
     * Creates an invisible wall with the given dimensions.
     *
     * @param world      the physics world
     * @param halfWidth  half the width of the wall's collision box
     * @param halfHeight half the height of the wall's collision box
     */
    public Wall(World world, float halfWidth, float halfHeight) {
        super(world, new BoxShape(halfWidth, halfHeight));
    }
}
