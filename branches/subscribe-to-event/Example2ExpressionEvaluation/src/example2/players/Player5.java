package example2.players;

import thespian4jade.core.Event;

/**
 * Player5 - the player playing the 'Divider' role.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class Player5 extends OperatorPlayer {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    /**
     * The full name of the Divider role.
     */
    private static String DIVIDER_ROLE_FULL_NAME
        = "expressionEvaluation_Organization.Divider_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instane of the Calculator5_Player class.
     */
    public Player5() {
        super(new RoleFullName(DIVIDER_ROLE_FULL_NAME));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add responsibility.
        addResponsibility(Divide_Responsibility.class);
        
        // Schedule behaviours.
        // Role enactment
        scheduleEnactRole(10000);
        scheduleSubscribeToEvent(Event.ROLE_ACTIVATED, RoleActivated_EventHandler.class, 11000);
        scheduleSubscribeToEvent(Event.ROLE_DEACTIVATED, RoleDeactivated_EventHandler.class, 11000);
        
        // Role deactment
        scheduleDeactRole(24000);
    }
    
    // </editor-fold>   
}
