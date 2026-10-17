package game;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import org.jbox2d.common.Vec2;

/**
 * Moves an enemy directly toward the player every physics step.
 * The enemy phases through platforms (it is a StaticBody moved by setPosition),
 * creating constant pressure that the player must deal with.
 *
 * Speed is set per-level: slow in Level 1, faster in Level 3.
 */
public class ChaserMove implements StepListener {

    private final Enemy enemy;
    private final Player player;
    private final float speed;

    /**
     * @param enemy  the enemy to control
     * @param player the target to chase
     * @param speed  units moved per physics step (e.g. 0.03 ≈ 1.8 units/sec at 60fps)
     */
    public ChaserMove(Enemy enemy, Player player, float speed) {
        this.enemy = enemy;
        this.player = player;
        this.speed = speed;
    }

    @Override
    public void preStep(StepEvent e) {
        if (enemy.isDestroyed()) return;

        Vec2 ePos = enemy.getPosition();
        Vec2 pPos = player.getPosition();
        float dx = pPos.x - ePos.x;
        float dy = pPos.y - ePos.y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist > 0.5f) {
            float nx = dx / dist;
            float ny = dy / dist;
            enemy.setPosition(new Vec2(ePos.x + nx * speed, ePos.y + ny * speed));
        }
    }

    @Override
    public void postStep(StepEvent e) {}
}
