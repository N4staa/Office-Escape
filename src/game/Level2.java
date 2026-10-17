package game;

import org.jbox2d.common.Vec2;
import java.awt.Color;

/**
 * Level 2 — mid-game.
 * Four platforms arranged in a climbing path with a back-track at the top.
 * Three bobbing enemies, one medium-speed chaser.
 */
public class Level2 extends GameLevel {

    private Enemy enemy1;
    private Enemy enemy2;
    private Enemy enemy3;
    private Enemy chaser;
    private Coin  requiredCoin;

    public Level2(Game game) {
        super(game);

        Color fill    = new Color(214, 176, 124);
        Color outline = new Color(170, 130, 85);

        // Five ground tiles
        placeGround(this, -24.4f);
        placeGround(this, -12.2f);
        placeGround(this,   0f);
        placeGround(this,  12.2f);
        placeGround(this,  24.4f);

        // Platforms — staircase rising left to right, with a back-left top platform
        Platform p1 = new Platform(this, 5f, 0.7f, fill, outline, "data/platformE.png");
        p1.setPosition(new Vec2(-15f, -4f));

        Platform p2 = new Platform(this, 5f, 0.7f, fill, outline, "data/platformE.png");
        p2.setPosition(new Vec2(-5f, -1f));

        Platform p3 = new Platform(this, 5f, 0.7f, fill, outline, "data/platformE.png");
        p3.setPosition(new Vec2(5f, 2f));

        Platform p4 = new Platform(this, 4f, 0.7f, fill, outline, "data/platformE.png");
        p4.setPosition(new Vec2(-8f, 6f));

        // Bobbing enemies — one per climbing platform
        enemy1 = new Enemy(this, "data/email.png", 4f);
        enemy1.setPosition(new Vec2(-15f, -2f));
        addStepListener(new EnemyMove(enemy1, 0.1f, 0.1f));

        enemy2 = new Enemy(this, "data/phone.png", 4f);
        enemy2.setPosition(new Vec2(-5f, 1f));
        addStepListener(new EnemyMove(enemy2, 0.1f, 0.1f));

        enemy3 = new Enemy(this, "data/meeting.png", 4f);
        enemy3.setPosition(new Vec2(5f, 4f));
        addStepListener(new EnemyMove(enemy3, 0.1f, 0.1f));

        // Medium chaser — slightly faster than Level 1
        chaser = new Enemy(this, "data/email.png", 4f);
        chaser.setPosition(new Vec2(10f, -7f));
        addStepListener(new ChaserMove(chaser, getPlayer(), 0.035f));

        // Required coin — on the top-left back platform
        requiredCoin = new Coin(this);
        requiredCoin.setPosition(new Vec2(-8f, 8f));
        addStepListener(new CoinMove(requiredCoin, 0.05f, 0.5f));

        // Bonus coins
        Coin bonus1 = new Coin(this);
        bonus1.setPosition(new Vec2(-25f, -8f));
        addStepListener(new CoinMove(bonus1, 0.04f, 0.4f));

        Coin bonus2 = new Coin(this);
        bonus2.setPosition(new Vec2(25f, -8f));
        addStepListener(new CoinMove(bonus2, 0.04f, 0.4f));
    }

    private static void placeGround(GameLevel level, float x) {
        Ground g = new Ground(level, 6.1f, 0.35f, "data/platform1.png", 10f, -0.9f);
        g.setPosition(new Vec2(x, -9.8f));
    }

    @Override public Vec2   getStartPosition() { return new Vec2(-20f, -7f); }
    @Override public String getBackgroundFile() { return "data/level2.png"; }

    @Override
    public boolean isComplete() {
        return enemy1.isDestroyed() && enemy2.isDestroyed()
                && enemy3.isDestroyed() && chaser.isDestroyed()
                && requiredCoin.isCollected();
    }
}
