package game;

import city.cs.engine.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The game's visual display.
 * Renders the physics world on screen, paints the background image,
 * and draws a HUD showing score, level, enemies defeated, lives (hearts),
 * and the countdown timer with a colour that shifts green → yellow → red.
 */
public class GameView extends UserView {

    private Image background;
    private final Game game;

    public GameView(World world, Game game, Body player, int width, int height) {
        super(world, width, height);
        this.game = game;

        try {
            String bgFile = (world instanceof GameLevel)
                    ? ((GameLevel) world).getBackgroundFile()
                    : "data/backround.png";
            File file = new File(bgFile);
            if (file.exists()) background = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        super.paintBackground(g);
        if (background != null) g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    protected void paintForeground(Graphics2D g) {
        super.paintForeground(g);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Glass panel background
        g.setColor(new Color(15, 15, 15, 195));
        g.fillRoundRect(10, 10, 170, 150, 18, 18);
        g.setColor(new Color(255, 255, 255, 50));
        g.drawRoundRect(10, 10, 170, 150, 18, 18);

        int x = 24;
        g.setFont(new Font("Arial", Font.BOLD, 14));

        // Score
        g.setColor(new Color(255, 215, 0));
        g.drawString("★  Score: " + game.getScore(), x, 38);

        // Level
        g.setColor(new Color(200, 180, 255));
        g.drawString("⬆  Level: " + game.getLevelNumber() + " / 3", x, 62);

        // Enemies defeated
        g.setColor(new Color(120, 220, 255));
        g.drawString("⚔  Defeated: " + game.getEnemiesKilled(), x, 86);

        // Lives as hearts
        g.setColor(new Color(255, 90, 90));
        g.setFont(new Font("Arial", Font.BOLD, 13));
        StringBuilder hearts = new StringBuilder("♥  ");
        int lives = game.getLives();
        for (int i = 0; i < 3; i++) hearts.append(i < lives ? "♥ " : "♡ ");
        g.drawString(hearts.toString(), x, 110);

        // Countdown timer — colour shifts with urgency
        int t = game.getTimeRemaining();
        Color timerColor;
        if      (t > 30) timerColor = new Color(100, 230, 100);
        else if (t > 15) timerColor = new Color(255, 200, 60);
        else             timerColor = new Color(255, 80,  80);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(timerColor);
        g.drawString("⏱  Time:  " + t + "s", x, 134);
    }
}
