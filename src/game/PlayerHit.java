package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Handles collisions between the player and an enemy.
 * When the player touches an enemy, they lose a life and get knocked back.
 * A cooldown prevents multiple lives being lost from a single collision.
 */
public class PlayerHit implements CollisionListener {

    private final Game game;
    private final Player player;

    // Timestamp of the last hit, used to enforce the cooldown
    private long lastHitTime = 0;

    // Minimum time (ms) that must pass before the player can be hit again
    private static final long HIT_COOLDOWN = 1000;

    /**
     * Creates a PlayerHit listener.
     *
     * @param game   the main Game object (used to reduce lives)
     * @param player the player body (used to apply knockback)
     */
    public PlayerHit(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    /**
     * Called whenever the player collides with something.
     * If the other body is an Enemy and the cooldown has expired,
     * the player loses a life and is knocked away from the enemy.
     */
    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Enemy) {

            long now = System.currentTimeMillis();
            if (now - lastHitTime < HIT_COOLDOWN) {
                return; // Still within cooldown — ignore this hit
            }
            lastHitTime = now;

            game.loseLife();

            try {
                new SoundClip("data/enemyTM.wav").play();
            } catch (Exception ex) {
                System.out.println("Could not play hit sound");
            }

            // Knock the player away from the enemy
            Vec2 playerPos = player.getPosition();
            Vec2 enemyPos  = e.getOtherBody().getPosition();

            // Push left if the player is to the left of the enemy, right otherwise
            float pushX = (playerPos.x < enemyPos.x) ? -8f : 8f;
            player.setLinearVelocity(new Vec2(pushX, 8f));
        }
    }
}
