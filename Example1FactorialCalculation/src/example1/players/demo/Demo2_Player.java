package example1.players.demo;

/**
 * The Participant2 player. The player playing the Answerer role.
 * @author Lukáš Kúdela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class Demo2_Player extends Demo_Player {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Creates a new Demo player who will enact the Answerer role.
     * @param roleFullName the full name of the role to enact and activate
     */
    public Demo2_Player() {
        super(new RoleFullName("demo_Organization.Answerer"));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Schedule individual behaviours.
     * Design pattern: Template method, Role: Primitive operation
     */
    @Override
    protected int doScheduleBehaviours(int timeout) {
        // No behaviours.
        return timeout + 2000;
    }
    
    // </editor-fold>
}
