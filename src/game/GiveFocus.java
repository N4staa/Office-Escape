package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Gives keyboard focus to the game window whenever the mouse enters it.
 * This ensures the KeyListener in Control receives input without the player
 * needing to click the window first.
 */
public class GiveFocus extends MouseAdapter {

    /**
     * Called when the mouse enters the component.
     * Requests keyboard focus so key input works immediately.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().requestFocus();
    }
}
