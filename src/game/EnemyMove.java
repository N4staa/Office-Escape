package game;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import org.jbox2d.common.Vec2;

/**
 * Animates an enemy with the same smooth vertical bobbing used by coins.
 * Uses a sine wave to produce continuous up-and-down floating motion.
 * Registered as a StepListener so it updates every physics step.
 */
public class EnemyMove implements StepListener {

    private final Enemy enemy;

    private final float baseX;
    private final float baseY;

    private final float amplitude;
    private final float speed;

    private float t = 0;

    /**
     * @param enemy     the enemy to animate
     * @param amplitude how far the enemy bobs up and down
     * @param speed     how fast it oscillates
     */
    public EnemyMove(Enemy enemy, float amplitude, float speed) {
        this.enemy = enemy;
        this.baseX = enemy.getPosition().x;
        this.baseY = enemy.getPosition().y;
        this.amplitude = amplitude;
        this.speed = speed;
    }

    @Override
    public void preStep(StepEvent e) {
        t += 1;
        float newY = baseY + (float) Math.sin(t * speed) * amplitude;
        enemy.setPosition(new Vec2(baseX, newY));
    }

    @Override
    public void postStep(StepEvent e) {}
}
