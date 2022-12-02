package engines.command;

import api.SwingAPI;

public class CommandEngine {
    public KeyHandler keyHandler ;
    public CommandEngine() {
        keyHandler = new KeyHandler();
    }
    // retourne


    public void enableKeyboardIO() {
        SwingAPI.getListenerMethods().addKeyListener(keyHandler);
    }

    /**
     * Désactiver les entrées/sorties clavier
     */
    public void disableKeyboardIO() {
        SwingAPI.getListenerMethods().removeKeyListener(keyHandler);
    }
}
