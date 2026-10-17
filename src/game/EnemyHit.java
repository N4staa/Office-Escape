package game;

import city.cs.engine.*;

/**
 * Handles collisions between a PaperBall projectile and an enemy.
 * When a hit is detected, both bodies are destroyed and the game's
 * enemy kill counter is incremented.
 */
public class EnemyHit implements CollisionListener {

    // Reference to the game so we can update the enemy kill count
    private final Game game;

    /**
     * Creates an EnemyHit listener.
     *
     * @param game the main Game object used to update statistics
     */
    public EnemyHit(Game game) {
        this.game = game;
    }

    /**
     * Called automatically whenever the attached body collides with something.
     * Checks whether the collision is between a PaperBall and an Enemy,
     * then destroys both and notifies the game.
     */
    @Override
    public void collide(CollisionEvent e) {

        // Check both orderings since reporting/other can be either body
        if ((e.getReportingBody() instanceof PaperBall && e.getOtherBody() instanceof Enemy)
                || (e.getReportingBody() instanceof Enemy && e.getOtherBody() instanceof PaperBall)) {

            // Identify which body is the projectile and which is the enemy
            Body paper = (e.getReportingBody() instanceof PaperBall)
                    ? e.getReportingBody() : e.getOtherBody();
            Body enemy = (paper == e.getReportingBody())
                    ? e.getOtherBody() : e.getReportingBody();

            try {
                new SoundClip("data/hitM.wav").play();
            } catch (Exception ex) {
                System.out.println("Could not play hit sound");
            }

            // Remove both bodies from the world
            paper.destroy();
            enemy.destroy();

            // Notify the game that an enemy was defeated
            game.enemyKilled();
            game.addScore(5);
        }
    }
}
