package example2.players.calculator;

import thespian4jade.concurrency.Future;
import thespian4jade.concurrency.IObservable;
import thespian4jade.concurrency.IObserver;
import thespian4jade.core.player.EventHandler;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class Calculator1_Player extends Calculator_Player implements IObserver {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    /**
     * The full name of the Evaluator role.
     */
    private static final String EVALUATOR_ROLE_FULL_NAME
        = "expressionEvaluation_Organization.Evaluator_Role";
    
    /**
     * The full name of the Evaluate competence.
     */
    private static final String EVALUATE_COMPETENCE_FULL_NAME
        = "expressionEvaluation_Organization.Evaluator_Role.Evaluate_Competence";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instane of the Calculator1_Player class.
     * Creates the calculator1 player who will enact the 'Evaluator' role.
     */
    public Calculator1_Player() {
        super(new RoleFullName(EVALUATOR_ROLE_FULL_NAME));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void update(IObservable observable) {
        Integer competenceResult = ((Future<Integer>)observable).getValue();
        System.out.println("----- 'Evalaute' competence result: " + competenceResult + " -----");
        deactivateRole();
    }
    
    // ----- PACKAGE -----
    
    void invokeCompetence() {
        CompetenceFullName competenceFullName = new CompetenceFullName(EVALUATE_COMPETENCE_FULL_NAME);
        String competenceArgument = "(1*2)+(4/2)";
        System.out.println("----- 'Evalaute' competence argument: " + competenceArgument + " -----");
        
        Future<Integer> future = invokeCompetence(competenceFullName.getCompetenceName(), competenceArgument);
        future.addObserver(this);
    }
    
    // ----- PROTECTED -----
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add event handlers.
        addEventHandler("role-enacted", RoleEnacted_EventHandler.class);
        addEventHandler("role-activated", RoleActivated_EventHandler.class);
        addEventHandler("role-deactivated", RoleDeactivated_EventHandler.class);
        
        // Schedule behaviours.
        scheduleEnactRole(2000);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * @author Lukáš Kúdela
     * @since 2012-03-20
     * @version %I% %G%
     */
    private class RoleEnacted_EventHandler
        extends EventHandler<Calculator1_Player> {

        @Override
        protected void handleEvent(String argument) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    /**
     * @author Lukáš Kúdela
     * @since 2012-03-20
     * @version %I% %G%
     */
    private class RoleActivated_EventHandler
        extends EventHandler<Calculator1_Player> {

        @Override
        protected void handleEvent(String argument) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    /**
     * @author Lukáš Kúdela
     * @since 2012-03-20
     * @version %I% %G%
     */   
    private class RoleDeactivated_EventHandler
        extends EventHandler<Calculator1_Player> {

        @Override
        protected void handleEvent(String argument) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    // </editor-fold>
}
