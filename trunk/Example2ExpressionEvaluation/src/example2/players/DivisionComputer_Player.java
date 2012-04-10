package example2.players;

import thespian4jade.core.Event;

/**
 * The 'Division computer' player - the player playing the 'Divider' role.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class DivisionComputer_Player extends OperatorPlayer {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    /**
     * The full name of the 'Divider' role.
     */
    private static String DIVIDER_ROLE_FULL_NAME
        = "expressionEvaluation_Organization.Divider_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the DivisionComputer_Player class.
     */
    public DivisionComputer_Player() {
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
