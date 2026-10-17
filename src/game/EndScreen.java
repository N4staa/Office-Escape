package game;

import javax.swing.*;
import java.awt.*;

/**
 * Full-screen result panel shown at game-over or on winning.
 * Displays score, high score, and offers "Play Again" / "Quit" options.
 */
public class EndScreen extends JFrame {

    /**
     * @param won       true for a win screen, false for game-over
     * @param score     the player's final score
     * @param highScore the all-time best score (already updated before this is called)
     */
    public EndScreen(boolean won, int score, int highScore) {
        super(won ? "You Won!" : "Game Over");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        boolean newRecord = (score == highScore && score > 0);

        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,  RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Background
                g2.setColor(new Color(12, 12, 22));
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Subtle grid
                g2.setColor(new Color(255, 255, 255, 8));
                for (int x = 0; x < getWidth();  x += 28) g2.drawLine(x, 0, x, getHeight());
                for (int y = 0; y < getHeight(); y += 28) g2.drawLine(0, y, getWidth(), y);

                // ── Main title ────────────────────────────────────────
                String heading = won ? "YOU ESCAPED!" : "GAME OVER";
                g2.setFont(new Font("Arial", Font.BOLD, 52));
                FontMetrics fm = g2.getFontMetrics();
                int hx = (getWidth() - fm.stringWidth(heading)) / 2;

                Color c1 = won ? new Color(255, 215, 0)  : new Color(230, 50, 50);
                Color c2 = won ? new Color(255, 160, 0)  : new Color(180, 20, 20);
                g2.setPaint(new GradientPaint(hx, 0, c1, hx + fm.stringWidth(heading), 0, c2));
                g2.drawString(heading, hx, 95);

                // Divider
                g2.setColor(new Color(255, 255, 255, 30));
                g2.drawLine(60, 115, getWidth() - 60, 115);

                // ── Score block ───────────────────────────────────────
                g2.setFont(new Font("Arial", Font.BOLD, 26));
                g2.setColor(new Color(255, 215, 0));
                String scoreStr = "★  Score:  " + score;
                g2.drawString(scoreStr, (getWidth() - g2.getFontMetrics().stringWidth(scoreStr)) / 2, 160);

                g2.setFont(new Font("Arial", Font.PLAIN, 18));
                g2.setColor(new Color(180, 180, 200));
                String hsStr = "Best Score:  " + highScore;
                g2.drawString(hsStr, (getWidth() - g2.getFontMetrics().stringWidth(hsStr)) / 2, 192);

                // New record badge
                if (newRecord) {
                    g2.setFont(new Font("Arial", Font.BOLD, 15));
                    g2.setColor(new Color(100, 255, 150));
                    String rec = "✦  NEW RECORD!  ✦";
                    g2.drawString(rec, (getWidth() - g2.getFontMetrics().stringWidth(rec)) / 2, 220);
                }

                // Tip
                g2.setFont(new Font("Arial", Font.PLAIN, 13));
                g2.setColor(new Color(120, 120, 150));
                String tip = won ? "Amazing — you made it out of the office!" : "The office won this time...";
                g2.drawString(tip, (getWidth() - g2.getFontMetrics().stringWidth(tip)) / 2, 250);
            }
        };

        panel.setPreferredSize(new Dimension(480, 330));

        JButton playBtn = StartScreen.makeButton("Play Again", new Color(70, 190, 90));
        playBtn.setBounds(80, 275, 140, 38);
        playBtn.addActionListener(e -> {
            dispose();
            new Game();
        });

        JButton quitBtn = StartScreen.makeButton("  Quit  ", new Color(190, 60, 60));
        quitBtn.setBounds(260, 275, 140, 38);
        quitBtn.addActionListener(e -> System.exit(0));

        panel.add(playBtn);
        panel.add(quitBtn);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
