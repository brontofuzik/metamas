package example2.players.participant;

/**
 * The 'Participant3' player.
 * @author Lukáš Kúdela
 * @since 2012-01-20
 * @version %I% %G%
 */
public class Participant3_Player extends Participant_Player {
       
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes the 'Participant3' player.
     */
    Participant3_Player() {
        addItemToBuy(new Item(POLLOCK, 155.8));
        addItemToBuy(new Item(KOONING, 154)); // Highest bid.
        
        addItemToSell(new Item(KLIMT, 135));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Schedule individual behaviours.
     * Design pattern: Template method, Role: Primitive operation
     */
    @Override
    protected int doScheduleBehaviours(int timeout) {
        // First auction: Pollock.
        timeout = scheduleActivateRole(auctioneerRoleFullName, timeout);
        timeout += 4000;
        timeout = scheduleDeactivateRole(auctioneerRoleFullName, timeout);
       
//        timeout += 8000;
//        
//        // Second auction: Kooning.
//        timeout = scheduleActivateRole(bidderRoleFullName, timeout);
//        timeout += 4000;
//        timeout = scheduleDeactivateRole(bidderRoleFullName, timeout);
//        
//        timeout += 8000;
//        
//        // Third auction: Klimt.
//        timeout = scheduleActivateRole(bidderRoleFullName, timeout);
//        timeout = scheduleInvokePower(auctionPowerFullName, timeout, 4000);
//        return scheduleDeactivateRole(bidderRoleFullName, timeout);
        
        return timeout;
    }
    
    // </editor-fold>
}
