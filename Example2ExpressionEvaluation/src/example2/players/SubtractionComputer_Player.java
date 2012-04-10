package example2.players;

import thespian4jade.core.Event;

/**
 * The 'Subtraction computer' player - the player plying the 'Subtractor' role.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class SubtractionComputer_Player extends OperatorPlayer {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    /**
     * The full name of the 'Subtractor' role.
     */
    private static String SUBTRACTOR_ROLE_FULL_NAME
        = "expressionEvaluation_Organization.Subtractor_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the SubtractionComputer_Player class.
     */
    public SubtractionComputer_Player() {
        super(new RoleFullName(SUBTRACTOR_ROLE_FULL_NAME));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add responsibilities.
        addResponsibility(Subtract_Responsibility.class);
        
        // Schedule behaviours.
        // Role enactment
        scheduleEnactRole(6000);
        scheduleSubscribeToEvent(Event.ROLE_ACTIVATED, RoleActivated_EventHandler.class, 7000);
        scheduleSubscribeToEvent(Event.ROLE_DEACTIVATED, RoleDeactivated_EventHandler.class, 7000);    
        
        // Role deactment
        scheduleDeactRole(20000);
    }
    
    // </editor-fold>   
}
