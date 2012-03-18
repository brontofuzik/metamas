package example1.players.demo;

/**
 * The Demo1_Player player. The player playing the Invoker role.
 * @author Lukáš Kúdela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class Demo1_Player extends Demo_Player {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Creates a new Demo player who will enact the Invoker role.
     * @param competenceFullName the full name of the competence to invoke 
     */
    public Demo1_Player() {
        super(new RoleFullName("functionInvocation_Organization.Invoker_Role"));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Schedule individual behaviours.
     * Design pattern: Template method, Role: Primitive operation
     */
    @Override
    protected int doScheduleCompetenceInvocations(int timeout) {
        CompetenceFullName competenceFullName = new CompetenceFullName("functionInvocation_Organization.Invoker_Role.InvokeFunction_Competence");
        Integer competenceArgument = new Integer(10);
        return scheduleInvokeCompetence(competenceFullName, competenceArgument, timeout, 2000);
    }
    
    // </editor-fold>
}
