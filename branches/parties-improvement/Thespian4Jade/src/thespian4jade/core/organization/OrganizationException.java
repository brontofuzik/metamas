package thespian4jade.core.organization;

/**
 * An organization exception.
 * Note: This class is currently not used.
 * @author Lukáš Kúdela
 * @since 2011-11-11
 * @version %I% %G%
 */
public class OrganizationException extends Exception {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String MESSAGE_FORMAT = "%1$: %2$";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Organization organization;
    
    private String message;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public OrganizationException(Organization organization, String message) {
        // ----- Preconditiosn -----
        if (organization == null) {
            throw new NullPointerException();
        }
        // -------------------------
        
        this.organization = organization;
        this.message = message;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public String getMessage() {
        return String.format(MESSAGE_FORMAT, organization.getName(), message);
    }
    
    // </editor-fold>    
}
