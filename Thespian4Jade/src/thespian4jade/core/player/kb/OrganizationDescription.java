package thespian4jade.core.player.kb;

import jade.core.AID;

/**
 * An organization description for a player.
 * @author Lukáš Kúdela
 * @since 2012-03-22
 * @version %I% %G%
 */
public class OrganizationDescription {
 
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The organization AID. */
    private AID organizationAID;
    
    /** The organization type. */
    private String organizationType;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public AID getAID() {
        return organizationAID;
    }
    
    String getOrganizationType() {
        return organizationType;
    }
    
    // </editor-fold>
}
