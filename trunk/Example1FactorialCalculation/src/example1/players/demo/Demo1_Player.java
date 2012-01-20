package example1.players.demo;

/**
 * The Demo1_Player player. The player playing the Asker role.
 * @author Lukáš Kúdela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class Demo1_Player extends Demo_Player {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The full name of the power to invoke. 
     */
    private PowerFullName powerFullName; 
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Creates a new Demo player who will enact the Asker role.
     * @param powerFullName the full name of the power to invoke 
     */
    public Demo1_Player() {
        super(new RoleFullName("demo_Organization.Asker"));
        powerFullName = new PowerFullName("demo_Organization.Asker.CalculateFactorial_Power");
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Schedule individual behaviours.
     * Design pattern: Template method, Role: Primitive operation
     */
    @Override
    protected int doScheduleBehaviours(int timeout) {
        Integer calculateFactorialArgument = new Integer(10);
        return scheduleInvokePower(powerFullName, calculateFactorialArgument,
            timeout, 2000);
    }
    
    // </editor-fold>
}
