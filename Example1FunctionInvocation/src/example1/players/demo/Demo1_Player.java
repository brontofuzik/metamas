package example1.players.demo;

/**
 * The Demo1_Player player. The player playing the Invoker role.
 * @author Lukáš Kúdela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class Demo1_Player extends Demo_Player {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    // TODO Consider moving this to the doScheduleCompetenceInvocations() method.
    /**
     * The full name of the competence to invoke.
     */
    private CompetenceFullName competenceFullName; 
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Creates a new Demo player who will enact the Invoker role.
     * @param powerFullName the full name of the power to invoke 
     */
    public Demo1_Player() {
        super(new RoleFullName("functionInvocation_Organization.Invoker_Role"));
        competenceFullName = new CompetenceFullName("functionInvocation_Organization.Invoker_Role.InvokeFunction_Competence");
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Schedule individual behaviours.
     * Design pattern: Template method, Role: Primitive operation
     */
    @Override
    protected int doScheduleCompetenceInvocations(int timeout) {
        Integer invokeFunctionArgument = new Integer(10);
        return scheduleInvokeCompetence(competenceFullName, invokeFunctionArgument, timeout, 2000);
    }
    
    // </editor-fold>
}
