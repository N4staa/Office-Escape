package game;

import javax.swing.*;
import java.awt.*;

/**
 * The game's title / start screen.
 * Shown once at launch; clicking "PLAY" disposes this frame and starts the game.
 */
public class StartScreen extends JFrame {

    public StartScreen() {
        super("Office Escape");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,     RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Dark background
                g2.setColor(new Color(14, 14, 26));
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Subtle grid
                g2.setColor(new Color(255, 255, 255, 10));
                for (int x = 0; x < getWidth();  x += 28) g2.drawLine(x, 0, x, getHeight());
                for (int y = 0; y < getHeight(); y += 28) g2.drawLine(0, y, getWidth(), y);

                // ── Title ─────────────────────────────────────────────
                g2.setFont(new Font("Arial", Font.BOLD, 54));
                FontMetrics fm = g2.getFontMetrics();
                String title = "OFFICE ESCAPE";
                int tx = (getWidth() - fm.stringWidth(title)) / 2;
                GradientPaint gp = new GradientPaint(tx, 0, new Color(255, 215, 0),
                        tx + fm.stringWidth(title), 0, new Color(255, 140, 0));
                g2.setPaint(gp);
                g2.drawString(title, tx, 90);

                // Subtitle
                g2.setFont(new Font("Arial", Font.PLAIN, 15));
                g2.setColor(new Color(160, 160, 190));
                String sub = "Escape the chaos of corporate life";
                g2.drawString(sub, (getWidth() - g2.getFontMetrics().stringWidth(sub)) / 2, 118);

                // Divider
                g2.setColor(new Color(255, 215, 0, 70));
                g2.drawLine(70, 138, getWidth() - 70, 138);

                // ── Controls ──────────────────────────────────────────
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.setColor(new Color(130, 190, 255));
                g2.drawString("CONTROLS", 70, 164);

                drawRow(g2, 70, 186, "← →", "Move left / right");
                drawRow(g2, 70, 208, "↑",   "Jump");
                drawRow(g2, 70, 230, "Space","Shoot paper ball");

                // ── Objective ─────────────────────────────────────────
                g2.setFont(new Font("Arial", Font.BOLD, 12));
                g2.setColor(new Color(130, 190, 255));
                g2.drawString("OBJECTIVE", 70, 262);

                g2.setFont(new Font("Arial", Font.PLAIN, 13));
                g2.setColor(new Color(205, 205, 225));
                g2.drawString("Defeat all enemies and collect the coin to advance.", 70, 282);
                g2.drawString("Survive 3 levels — you have 90 seconds per level!", 70, 300);

                // ── High score ────────────────────────────────────────
                int hs = HighScoreManager.load();
                if (hs > 0) {
                    g2.setFont(new Font("Arial", Font.BOLD, 13));
                    g2.setColor(new Color(255, 215, 0));
                    String hsStr = "Best Score: " + hs;
                    g2.drawString(hsStr, (getWidth() - g2.getFontMetrics().stringWidth(hsStr)) / 2, 330);
                }
            }

            private void drawRow(Graphics2D g2, int x, int y, String key, String desc) {
                g2.setFont(new Font("Monospaced", Font.BOLD, 13));
                g2.setColor(new Color(255, 220, 100));
                g2.drawString(key, x, y);
                g2.setFont(new Font("Arial", Font.PLAIN, 13));
                g2.setColor(new Color(205, 205, 225));
                g2.drawString(desc, x + 72, y);
            }
        };

        panel.setPreferredSize(new Dimension(560, 400));

        JButton playBtn = makeButton("  PLAY  ", new Color(70, 190, 90));
        playBtn.setBounds(210, 348, 140, 40);
        playBtn.addActionListener(e -> {
            dispose();
            new Game();
        });
        panel.add(playBtn);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    static JButton makeButton(String text, Color color) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(new Color(255, 255, 255, 50));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
