package example2.players;

import thespian4jade.concurrency.Future;
import thespian4jade.concurrency.IObservable;
import thespian4jade.concurrency.IObserver;
import thespian4jade.core.Event;
import thespian4jade.behaviours.EventHandler;
import thespian4jade.example.CompetencePlayer;

/**
 * 'Blank' player - the player playing the 'Evaluator' role.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class Blank_Player extends CompetencePlayer<String> implements IObserver {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    /** The full name of the 'Evaluator' role. */
    private static final String EVALUATOR_ROLE_FULL_NAME
        = "expressionEvaluation_Organization.Evaluator_Role";
    
    /** The name of the 'Evaluate' competence. */
    private static final String EVALUATE_COMPETENCE_NAME
        = "Evaluate_Competence";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private int operators;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instane of the Calculator1_Player class.
     * Creates the calculator1 player who will enact the 'Evaluator' role.
     */
    public Blank_Player() {
        super(new RoleFullName(EVALUATOR_ROLE_FULL_NAME), EVALUATE_COMPETENCE_NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void update(IObservable observable) {
        // Get 'Evaluate' competence result
        Integer evaluateResult = ((Future<Integer>)observable).getValue();
        System.out.println("----- 'Evaluate' competence result: " + evaluateResult + " -----");
        
        deactivateRole();
    }
    
    // ----- PROTECTED -----
    
    @Override
    protected void setup() {
        super.setup();
        
        // Schedule behaviours.
        // Role enactment
        scheduleEnactRole(2000);
        scheduleSubscribeToEvent(Event.ROLE_ACTIVATED, RoleActivated_EventHandler.class, 3000);
        
        // Competence invocation
        scheduleActivateRole(12000);
        
        // Role deactment
        scheduleDeactRole(16000);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Role activated' event handler.
     * @author Lukáš Kúdela
     * @since 2012-03-20
     * @version %I% %G%
     */
    public static class RoleActivated_EventHandler
        extends EventHandler<Blank_Player> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Handles the 'Role activated' event.
         * @param roleName the name of the activated role
         */
        @Override
        protected void handleEvent(String roleName) {
            getMyPlayer().operators++;
            if (getMyPlayer().operators == 4) {
                getMyPlayer().operators = 0;

                // Set 'Evaluate' competence argument.
                String evaluateArgument = "(1*2)+(4/2)";
                System.out.println("----- Evaluate argument: " + evaluateArgument + " -----");
                
                Future<Integer> future = getMyPlayer().invokeCompetence(evaluateArgument);
                future.addObserver(getMyPlayer());
            }
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
