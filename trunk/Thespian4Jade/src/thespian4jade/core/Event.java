package thespian4jade.core;

/**
 * An event.
 * @author Lukáš Kúdela
 * @since 2012-03-20
 * @version %I% %G%
 */
public enum Event {
    
    NONE(""),
    
    ROLE_ENACTED("role-enacted"),
    
    ROLE_DEACTED("role-deacted"),
    
    ROLE_ACTIVATED("role-activated"),
    
    ROLE_DEACTIVATED("role-deactivated");

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The name of the event.
     */
    private String name;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private Event(String name) {
        this.name = name;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public static Event fromString(String name) {
        switch (name) {
            case "role-enacted":
                return Event.ROLE_ENACTED;
            case "role-deacted":
                return Event.ROLE_DEACTED;
            case "role-activated":
                return Event.ROLE_ACTIVATED;
            case "role-deactivated":
                return Event.ROLE_DEACTIVATED;
            default:
                return Event.NONE;
        }
    }
    
    public String toString() {
        return name;
    }
    
    // </editor-fold>
}
