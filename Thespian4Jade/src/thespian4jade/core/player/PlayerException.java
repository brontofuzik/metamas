package thespian4jade.core.player;

/**
 * A player exception.
 * Note: This class is currently not used.
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
public class PlayerException extends Exception {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String MESSAGE_FORMAT = "%1$: %2$";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Player player;
    
    private String message;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public PlayerException(Player player, String message) {
        // ----- Preconditiosn -----
        if (player == null) {
            throw new NullPointerException();
        }
        // -------------------------
        
        this.player = player;
        this.message = message;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public String getMessage() {
        return String.format(MESSAGE_FORMAT, player.getName(), message);
    }
    
    // </editor-fold>
}
