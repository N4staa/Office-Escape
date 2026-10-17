package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Animates a coin with a smooth floating up-and-down motion using a sine wave.
 * Registered as a StepListener so it updates every physics step.
 */
public class CoinMove implements StepListener {

    private final Coin coin;

    // The coin's original X and Y positions, used as the centre of movement
    private final float baseX;
    private final float baseY;

    // How far the coin bobs up and down from its base position
    private final float amplitude;

    // How fast the coin oscillates
    private final float speed;

    // Time counter incremented each step, fed into the sine function
    private float t = 0;

    /**
     * Creates a movement controller for the given coin.
     *
     * @param coin      the coin to animate
     * @param speed     how fast the coin oscillates
     * @param amplitude how far the coin moves up and down
     */
    public CoinMove(Coin coin, float speed, float amplitude) {
        this.coin = coin;
        this.speed = speed;
        this.amplitude = amplitude;
        this.baseX = coin.getPosition().x;
        this.baseY = coin.getPosition().y;
    }

    /**
     * Called before each physics step.
     * Updates the coin's Y position using a sine wave.
     */
    @Override
    public void preStep(StepEvent e) {
        t += 1;
        float newY = baseY + (float) Math.sin(t * speed) * amplitude;
        coin.setPosition(new Vec2(baseX, newY));
    }

    /** Called after each physics step — not used here. */
    @Override
    public void postStep(StepEvent e) {}
}
