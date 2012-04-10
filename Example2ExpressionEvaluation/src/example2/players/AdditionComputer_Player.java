package example2.players;

import thespian4jade.core.Event;

/**
 * THe 'Addition computer' player - the player playing the 'Adder' role.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class AdditionComputer_Player extends OperatorPlayer {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    /**
     * The full name of the 'Adder' role.
     */
    private static String ADDER_ROLE_FULL_NAME
        = "expressionEvaluation_Organization.Adder_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the AdditionComputer_Player class.
     */
    public AdditionComputer_Player() {
        super(new RoleFullName(ADDER_ROLE_FULL_NAME));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add responsibilities.
        addResponsibility(Add_Responsibility.class);
        
        // Schedule behaviours.
        // Role enactment
        scheduleEnactRole(4000);
        scheduleSubscribeToEvent(Event.ROLE_ACTIVATED, RoleActivated_EventHandler.class, 5000);
        scheduleSubscribeToEvent(Event.ROLE_DEACTIVATED, RoleDeactivated_EventHandler.class, 5000);
        
        // Role deactment
        scheduleDeactRole(18000);
    }
    
    // </editor-fold>
}
