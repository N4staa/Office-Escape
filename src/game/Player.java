package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Represents the player character.
 * Extends Walker so it can move horizontally and jump using the physics engine.
 * The player sprite flips depending on which direction it is facing.
 * Collecting a coin triggers a sound and adds to the score.
 */
public class Player extends Walker {

    // Collision shape for the player body
    private static final Shape PLAYER_SHAPE = new BoxShape(1, 2.45f);

    // Sprites for each direction the player can face
    private static final BodyImage RIGHT_IMG = new BodyImage("data/worker.png", 4.9f);
    private static final BodyImage LEFT_IMG  = new BodyImage("data/worker1.png", 4.9f);

    // Vertical offset to align the sprite image with the physics body
    private static final float SPRITE_Y_OFFSET = -0.3f;

    // Reference to the game so we can update the score on coin collection
    private final Game game;

    // Tracks which direction the player is currently facing
    private boolean facingRight = false;

    // Holds a reference to the currently attached sprite so we can reposition it
    private AttachedImage currentSprite;

    /**
     * Creates the player body in the given world, sets the initial sprite,
     * and registers a collision listener to handle coin collection.
     *
     * @param world the physics world
     * @param game  the main game object
     */
    public Player(World world, Game game) {
        super(world, PLAYER_SHAPE);
        this.game = game;

        setSprite(LEFT_IMG);

        // Listen for collisions with coins
        addCollisionListener(e -> {
            if (e.getOtherBody() instanceof Coin) {
                Coin coin = (Coin) e.getOtherBody();
                game.addScore(1);
                coin.collect();
                try {
                    new SoundClip("data/coinmus.wav").play();
                } catch (Exception ex) {
                    System.out.println("Could not play coin sound");
                }
            }
        });
    }

    /** Returns true if the player is currently facing right. */
    public boolean isFacingRight() {
        return facingRight;
    }

    /**
     * Swaps the player's sprite image and reapplies the Y offset
     * so the feet stay aligned with the physics body.
     */
    private void setSprite(BodyImage img) {
        removeAllImages();
        currentSprite = addImage(img);
        currentSprite.setOffset(new Vec2(0f, SPRITE_Y_OFFSET));
    }

    /**
     * Turns the player to face left and swaps the sprite if needed.
     * Does nothing if already facing left.
     */
    public void faceLeft() {
        if (facingRight) {
            facingRight = false;
            setSprite(LEFT_IMG);
        }
    }

    /**
     * Turns the player to face right and swaps the sprite if needed.
     * Does nothing if already facing right.
     */
    public void faceRight() {
        if (!facingRight) {
            facingRight = true;
            setSprite(RIGHT_IMG);
        }
    }

    /**
     * Adjusts the vertical offset of the current sprite.
     *
     * @param y the new Y offset value
     */
    public void setSpriteYOffset(float y) {
        if (currentSprite != null) {
            currentSprite.setOffset(new Vec2(0f, y));
        }
    }
}
