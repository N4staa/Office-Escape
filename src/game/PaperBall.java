package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Represents a projectile fired by the player.
 * Extends DynamicBody so it is affected by gravity and physics.
 * Created in Control.shootPaperBall() and travels horizontally
 * with a slight upward arc.
 */
public class PaperBall extends DynamicBody {

    // Small circular collision shape for the projectile
    private static final Shape SHAPE = new CircleShape(0.25f);

    // Shared image used for all PaperBall instances
    private static final BodyImage IMG = new BodyImage("data/Paperball.png", 2);

    /**
     * Creates a PaperBall inside the given world.
     *
     * @param world the physics world the projectile belongs to
     */
    public PaperBall(World world) {
        super(world, SHAPE);
        addImage(IMG);
    }

    /**
     * Launches the projectile horizontally with a slight upward arc.
     *
     * @param speed horizontal velocity (positive = right, negative = left)
     */
    public void shoot(float speed) {
        setLinearVelocity(new Vec2(speed, 5f));
    }
}
