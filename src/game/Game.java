package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.Dimension;

/**
 * Main game class. Manages the window, level transitions, scoring, lives,
 * the countdown timer, and camera follow.
 *
 * Level progression: Level1 → Level2 → Level3.
 * The high score is loaded from disk at startup and saved whenever it improves.
 */
public class Game {

    // ── Stats ─────────────────────────────────────────────────────────────

    private int enemiesKilled = 0;
    private int score         = 0;
    private int lives         = 3;
    private boolean gameOver  = false;
    private boolean muted     = false;

    /** All-time high score, loaded from file when the class is first used. */
    private static int highScore = HighScoreManager.load();

    // ── Time ──────────────────────────────────────────────────────────────

    /** Seconds remaining for the current level. Losing a life when this hits 0. */
    private int timeRemaining;
    private static final int SECONDS_PER_LEVEL = 90;

    // ── Swing / world refs ────────────────────────────────────────────────

    private JFrame     frame;
    private GameLevel  level;
    private GameView   view;
    private Player     player;
    private int        levelNumber = 1;

    private Timer levelTimer;
    private Timer countdownTimer;
    private SoundClip backgroundMusic;

    // ─────────────────────────────────────────────────────────────────────

    public Game() {
        level  = new Level1(this);
        player = level.getPlayer();
        player.addCollisionListener(new PlayerHit(this, player));

        buildWindow();

        // Level-complete polling
        levelTimer = new Timer(200, e -> {
            if (level.isComplete()) goNextLevel();
        });
        levelTimer.start();

        level.start();

        // Countdown timer — fires every second
        timeRemaining = SECONDS_PER_LEVEL;
        countdownTimer = new Timer(1000, e -> {
            if (!gameOver) {
                timeRemaining--;
                if (timeRemaining <= 0) {
                    timeRemaining = SECONDS_PER_LEVEL;
                    loseLife();
                }
            }
        });
        countdownTimer.start();

        try {
            backgroundMusic = new SoundClip("data/backroundmus.wav");
            backgroundMusic.loop();
        } catch (Exception e) {
            System.out.println("Could not load background music: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // Window
    // ─────────────────────────────────────────────────────────────────────

    /**
     * Builds (or rebuilds) the game window for the current level.
     * Also installs the camera step-listener that keeps the view
     * centred on the player as the wider level scrolls.
     */
    private void buildWindow() {
        view = new GameView(level, this, player, 700, 500);

        // Initial camera position — centred on where the player spawns
        view.setCentre(new Vec2(player.getPosition().x, 2.2f));

        // Camera follow: clamp to [-12, 12] so the screen never shows past the walls
        final GameView  v = view;
        final Player    p = player;
        level.addStepListener(new StepListener() {
            @Override public void preStep(StepEvent e) {
                float cx = Math.max(-12f, Math.min(12f, p.getPosition().x));
                v.setCentre(new Vec2(cx, 2.2f));
            }
            @Override public void postStep(StepEvent e) {}
        });

        frame = new JFrame("Office Escape");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);

        Buttons buttons = new Buttons(level, frame, view, this);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(view.getPreferredSize());

        view.setBounds(0, 0, view.getPreferredSize().width, view.getPreferredSize().height);

        Dimension btnSize = buttons.getPreferredSize();
        buttons.setBounds(
                view.getPreferredSize().width - btnSize.width - 20,
                20,
                btnSize.width,
                btnSize.height
        );

        layeredPane.add(view,    JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(buttons, JLayeredPane.PALETTE_LAYER);

        frame.setContentPane(layeredPane);
        frame.pack();
        frame.setVisible(true);

        Control controls = new Control(player, level, this);
        view.addKeyListener(controls);
        view.setFocusable(true);
        view.addMouseListener(new GiveFocus());
        view.requestFocusInWindow();
    }

    // ─────────────────────────────────────────────────────────────────────
    // Accessors
    // ─────────────────────────────────────────────────────────────────────

    public int  getScore()         { return score; }
    public int  getLives()         { return lives; }
    public int  getLevelNumber()   { return levelNumber; }
    public int  getEnemiesKilled() { return enemiesKilled; }
    public int  getTimeRemaining() { return timeRemaining; }
    public boolean isMuted()       { return muted; }

    public void addScore(int pts) {
        if (!gameOver) score += pts;
    }

    public void enemyKilled() { enemiesKilled++; }

    // ─────────────────────────────────────────────────────────────────────
    // Music
    // ─────────────────────────────────────────────────────────────────────

    public void toggleMusic() {
        muted = !muted;
        if (backgroundMusic != null) {
            if (muted) backgroundMusic.stop();
            else       backgroundMusic.loop();
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // Life & game over
    // ─────────────────────────────────────────────────────────────────────

    public void loseLife() {
        if (gameOver) return;
        if (lives > 0) lives--;

        if (lives <= 0) {
            gameOver = true;
            SwingUtilities.invokeLater(() -> {
                if (countdownTimer != null) countdownTimer.stop();
                if (backgroundMusic != null) backgroundMusic.stop();
                if (level != null) level.stop();

                updateHighScore();

                if (frame != null) frame.dispose();
                new EndScreen(false, score, highScore);
            });
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // Level transitions
    // ─────────────────────────────────────────────────────────────────────

    private void goNextLevel() {
        level.stop();
        countdownTimer.stop();

        if (levelNumber == 3) {
            levelTimer.stop();
            int bonus = lives * 5;
            addScore(bonus);
            updateHighScore();
            if (backgroundMusic != null) backgroundMusic.stop();
            frame.dispose();
            new EndScreen(true, score, highScore);
            return;
        }

        int lifeBonus = lives * 3;
        addScore(lifeBonus);

        String nextName;
        if (levelNumber == 1) {
            level = new Level2(this);
            levelNumber = 2;
            nextName = "Level 2";
        } else {
            level = new Level3(this);
            levelNumber = 3;
            nextName = "Level 3";
        }

        JOptionPane.showMessageDialog(frame,
                "Level Complete!  +" + lifeBonus + " life bonus pts\nStarting " + nextName + "...",
                "Level Complete", JOptionPane.INFORMATION_MESSAGE);

        player = level.getPlayer();
        player.addCollisionListener(new PlayerHit(this, player));

        frame.dispose();
        buildWindow();

        timeRemaining = SECONDS_PER_LEVEL;
        level.start();
        countdownTimer.start();
    }

    // ─────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────

    private void updateHighScore() {
        if (score > highScore) {
            highScore = score;
            HighScoreManager.save(highScore);
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // Entry point
    // ─────────────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        new StartScreen();
    }
}
