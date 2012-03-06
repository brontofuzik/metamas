package example2.players.participant;

import example2.organizations.auction.auctioneer.auction.AuctionArgument;

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
    public Participant3_Player() {
        addItemToBuy(new Item(POLLOCK, 155.8));
        addItemToBuy(new Item(KOONING, 154)); // Highest bid.
        
        addItemToSell(new Item(KLIMT, 135));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Schedule the Pollock auction.
     * Design pattern: Template method, Role: Primitive operation
     * @param
     * @return
     */
    @Override
    protected int doSchedulePollockAuction(int timeout) {
        // Activate the 'Bidder' role.
        timeout = scheduleActivateRole(bidderRoleFullName, timeout);
        
        timeout += 4000;
        
        // Deactivate the 'Bidder' role.
        return scheduleDeactivateRole(bidderRoleFullName, timeout);
    }

    /**
     * Schedule the Kooning auction.
     * Design pattern: Template method, Role: Primitive operation
     * @param timeout 
     * @return 
     */
    @Override
    protected int doScheduleKooningAuction(int timeout) {
        // Activate the 'Bidder' role.
        timeout = scheduleActivateRole(bidderRoleFullName, timeout);
        
        timeout += 4000;
        
        // Deactivate the 'Bidder' role.
        return scheduleDeactivateRole(bidderRoleFullName, timeout);
    }

    /**
     * Schedule the Klimt auction.
     * Design pattern: Template method, Role: Primitive operation
     * @param timeout
     * @return 
     */
    @Override
    protected int doScheduleKlimtAuction(int timeout) {
        // Activate the 'Auctioneer' role.
        timeout = scheduleActivateRole(auctioneerRoleFullName, timeout);
        
        // Invoke the 'Auction' power.
        Item klimt = getItemToSell(KLIMT);
        AuctionArgument auctionArgument = AuctionArgument.createEnvelopeAuctionArgument(
            klimt.getName(), klimt.getPrice());
        timeout = scheduleInvokePower(auctionPowerFullName, auctionArgument,
            timeout, 4000);
       
        // Deactivate the 'Auctioneer' role.
        return scheduleDeactivateRole(auctioneerRoleFullName, timeout);
    }
    
    // </editor-fold>
}
