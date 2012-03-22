package thespian4jade.core.player.kb;

import jade.core.AID;

/**
 * A position description for a player.
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
public class PositionDescription {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The position AID. */
    private AID positionAID;
    
    /** The role. */
    private String role;
    
    /** The organization AID. */
    private AID organizationAID;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public PositionDescription(AID positionAID, String role, AID organizationAID) {
        this.positionAID = positionAID;  
        this.role = role;
        this.organizationAID = organizationAID;      
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public AID getAID() {
        return positionAID;
    }

    public String getRole() {
        return role;
    }
    

    
    // </editor-fold>
}
