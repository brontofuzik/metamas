package example2.players.participant;

import jadeorg.core.player.requirement.OneShotRequirement;

/**
 * The 'Bid' (one-shot) requirement.
 * @author Lukáš Kúdela
 * @since 2012-01-20
 * @version %I% %G%
 */
public class Bid_Requirement extends OneShotRequirement<BidArgument, BidResult> {

    @Override
    public void action() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
