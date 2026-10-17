package game;

import org.jbox2d.common.Vec2;
import java.awt.Color;

/**
 * Level 1 — intro level.
 * The player starts on the left side and must cross the wider level,
 * defeating 2 bobbing enemies and 1 slow chaser, then collect the coin.
 */
public class Level1 extends GameLevel {

    private Enemy enemy1;
    private Enemy enemy2;
    private Enemy chaser;
    private Coin  requiredCoin;

    public Level1(Game game) {
        super(game);

        Color fill    = new Color(214, 176, 124);
        Color outline = new Color(170, 130, 85);

        // Five ground tiles covering the wider level (walls at ±30)
        placeGround(this, -24.4f);
        placeGround(this, -12.2f);
        placeGround(this,   0f);
        placeGround(this,  12.2f);
        placeGround(this,  24.4f);

        // Stepping platforms — left to right, gradually higher
        Platform p1 = new Platform(this, 5f, 0.7f, fill, outline, "data/platformE.png");
        p1.setPosition(new Vec2(-16f, -4f));

        Platform p2 = new Platform(this, 5f, 0.7f, fill, outline, "data/platformE.png");
        p2.setPosition(new Vec2(-6f, -2f));

        Platform p3 = new Platform(this, 5f, 0.7f, fill, outline, "data/platformE.png");
        p3.setPosition(new Vec2(7f, -3f));

        Platform p4 = new Platform(this, 5f, 0.7f, fill, outline, "data/platformE.png");
        p4.setPosition(new Vec2(18f, 0f));

        // Bobbing enemies on the middle platforms
        enemy1 = new Enemy(this, "data/email.png", 4f);
        enemy1.setPosition(new Vec2(-16f, -2f));
        addStepListener(new EnemyMove(enemy1, 0.1f, 0.1f));

        enemy2 = new Enemy(this, "data/phone.png", 4f);
        enemy2.setPosition(new Vec2(-6f, 0f));
        addStepListener(new EnemyMove(enemy2, 0.1f, 0.1f));

        // Slow chaser — prowls at ground level, moves toward the player
        chaser = new Enemy(this, "data/meeting.png", 4f);
        chaser.setPosition(new Vec2(2f, -7f));
        addStepListener(new ChaserMove(chaser, getPlayer(), 0.03f));

        // Required coin — above the far-right platform
        requiredCoin = new Coin(this);
        requiredCoin.setPosition(new Vec2(20f, 2f));
        addStepListener(new CoinMove(requiredCoin, 0.05f, 0.5f));

        // Bonus coins
        Coin bonus1 = new Coin(this);
        bonus1.setPosition(new Vec2(-24f, -8f));
        addStepListener(new CoinMove(bonus1, 0.04f, 0.4f));

        Coin bonus2 = new Coin(this);
        bonus2.setPosition(new Vec2(0f, -8f));
        addStepListener(new CoinMove(bonus2, 0.04f, 0.4f));
    }

    private static void placeGround(GameLevel level, float x) {
        Ground g = new Ground(level, 6.1f, 0.35f, "data/platform1.png", 10f, -0.9f);
        g.setPosition(new Vec2(x, -9.8f));
    }

    @Override public Vec2    getStartPosition() { return new Vec2(-22f, -7f); }
    @Override public String  getBackgroundFile() { return "data/level1.png"; }

    @Override
    public boolean isComplete() {
        return enemy1.isDestroyed() && enemy2.isDestroyed()
                && chaser.isDestroyed() && requiredCoin.isCollected();
    }
}
