package example3.players.calculator;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class Calculator1_Player extends Calculator_Player {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    /**
     * The full name of the Evaluator role.
     */
    private static String EVALUATOR_ROLE_FULL_NAME = "expressionEvaluation_Organization.Evaluator_Role";
    
    /**
     * The full name of the Evaluate competence.
     */
    private static String EVALUATE_COMPETENCE_FULL_NAME = "expressionEvaluation_Organization.Evaluator_Role.Evaluate_Competence";
    
    private CompetenceFullName competenceFullName;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instane of the Calculator1_Player class.
     */
    public Calculator1_Player() {
        super(new RoleFullName(EVALUATOR_ROLE_FULL_NAME));
        competenceFullName = new CompetenceFullName(EVALUATE_COMPETENCE_FULL_NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected int doScheduleCompetenceInvocations(int timeout) {
        String competenceArgument = "(1*2)+(4/2)";
        return scheduleInvokeCompetence(competenceFullName, competenceArgument, timeout, 4000);
    }
    
    // </editor-fold>
}
