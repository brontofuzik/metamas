package example2.players;

import thespian4jade.core.Event;

/**
 * The 'Multiplication computer' player - the player playing the 'Multiplier' role.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class MultiplicationComputer_Player extends OperatorPlayer {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    /**
     * The full name of the 'Multiplier' role.
     */
    private static String MULTIPLIER_ROLE_FULL_NAME
        = "expressionEvaluation_Organization.Multiplier_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the MultiplicationComputer_Player class.
     */
    public MultiplicationComputer_Player() {
        super(new RoleFullName(MULTIPLIER_ROLE_FULL_NAME));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add responsibilities.
        addResponsibility(Multiply_Responsibility.class);
        
        // Schedule behaviours.
        // Role enactment
        scheduleEnactRole(8000);
        scheduleSubscribeToEvent(Event.ROLE_ACTIVATED, RoleActivated_EventHandler.class, 9000);
        scheduleSubscribeToEvent(Event.ROLE_DEACTIVATED, RoleDeactivated_EventHandler.class, 9000);
        
        // Role deactment
        scheduleDeactRole(22000);
    }
    
    // </editor-fold>   
}
