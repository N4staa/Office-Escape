package game;

import city.cs.engine.World;

import javax.swing.*;
import java.awt.*;

/**
 * A floating panel containing Pause, Mute, and Exit buttons.
 * Drawn with a dark translucent rounded rectangle to match the HUD style.
 * Positioned in the top-right corner of the game window by Game.
 */
public class Buttons extends JPanel {

    private final JButton pauseButton;
    private final JButton muteButton;
    private final JButton exitButton;

    private boolean paused = false;

    /**
     * Creates the buttons panel and wires up all actions.
     *
     * @param world the physics world (stopped/started on pause/resume)
     * @param frame the main window (used for dialog positioning and disposal)
     * @param view  the game view (focus returned after button clicks)
     * @param game  the main game object (used for mute toggle)
     */
    public Buttons(World world, JFrame frame, GameView view, Game game) {
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 12));

        pauseButton = new JButton("Pause");
        muteButton  = new JButton("Mute");
        exitButton  = new JButton("Exit");

        styleButton(pauseButton, new Color(70, 190, 90));
        styleButton(muteButton,  new Color(80, 130, 210));
        styleButton(exitButton,  new Color(220, 70, 70));

        add(pauseButton);
        add(muteButton);
        add(exitButton);

        pauseButton.addActionListener(e -> {
            if (!paused) {
                world.stop();
                paused = true;
                pauseButton.setText("Resume");
                JOptionPane.showMessageDialog(frame, "Game paused. Press Resume to continue.");
            } else {
                world.start();
                paused = false;
                pauseButton.setText("Pause");
            }
            view.requestFocusInWindow();
        });

        muteButton.addActionListener(e -> {
            game.toggleMusic();
            muteButton.setText(game.isMuted() ? "Unmute" : "Mute");
            view.requestFocusInWindow();
        });

        exitButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to exit?",
                    "Exit Game",
                    JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                frame.dispose();
            }
            view.requestFocusInWindow();
        });
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 11));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(3, 9, 3, 9));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(35, 35, 35, 185));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        g2.setColor(new Color(255, 255, 255, 40));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(220, 60);
    }

    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintButtonBackground(g2, pauseButton, new Color(70, 190, 90));
        paintButtonBackground(g2, muteButton,  new Color(80, 130, 210));
        paintButtonBackground(g2, exitButton,  new Color(220, 70, 70));
        g2.dispose();
        super.paintChildren(g);
    }

    private void paintButtonBackground(Graphics2D g2, JButton button, Color color) {
        Point p = SwingUtilities.convertPoint(button.getParent(), button.getLocation(), this);
        g2.setColor(color);
        g2.fillRoundRect(p.x, p.y, button.getWidth(), button.getHeight(), 22, 22);
        g2.setColor(new Color(255, 255, 255, 60));
        g2.drawRoundRect(p.x, p.y, button.getWidth() - 1, button.getHeight() - 1, 22, 22);
    }
}
