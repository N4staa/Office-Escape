package game;

import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles keyboard input to control the player.
 * Arrow keys move and jump; Space fires a PaperBall projectile.
 * Implements KeyListener and is registered on the GameView.
 */
public class Control implements KeyListener {

    // How fast the player walks horizontally
    private static final float WALK_SPEED = 6f;

    // How fast the PaperBall travels when fired
    private static final float SHOT_SPEED = 12f;

    private final Player player;

    // The physics world — needed when spawning projectiles
    private final World world;

    private final Game game;

    /**
     * Creates a keyboard controller.
     *
     * @param player the player to control
     * @param world  the physics world (used when creating projectiles)
     * @param game   the main game object
     */
    public Control(Player player, World world, Game game) {
        this.player = player;
        this.world = world;
        this.game = game;
    }

    /**
     * Called when a key is pressed.
     * LEFT/RIGHT move the player; UP jumps; SPACE fires a projectile.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_LEFT) {
            player.faceLeft();
            player.startWalking(-WALK_SPEED);

        } else if (code == KeyEvent.VK_RIGHT) {
            player.faceRight();
            player.startWalking(WALK_SPEED);

        } else if (code == KeyEvent.VK_UP) {
            player.jump(12);

        } else if (code == KeyEvent.VK_SPACE) {
            shootPaperBall();
        }
    }

    /**
     * Creates and launches a PaperBall in the direction the player is facing.
     * The ball spawns slightly in front of the player to avoid self-collision.
     */
    private void shootPaperBall() {
        PaperBall ball = new PaperBall(world);
        ball.addCollisionListener(new EnemyHit(game));

        Vec2 p = player.getPosition();

        // Offset spawn position in front of the player
        float xOffset = player.isFacingRight() ? 1.2f : -1.2f;
        ball.setPosition(new Vec2(p.x + xOffset, p.y + 0.5f));

        // Fire in the direction the player is facing
        float speed = player.isFacingRight() ? SHOT_SPEED : -SHOT_SPEED;
        ball.shoot(speed);
    }

    /**
     * Called when a key is released.
     * Stops horizontal movement when the left or right key is released.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT) {
            player.stopWalking();
        }
    }

    /** Required by KeyListener but not used. */
    @Override
    public void keyTyped(KeyEvent e) {}
}
