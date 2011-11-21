package auctionjadehandwritten.organizations;

import jadeorg.core.organization.behaviours.Power;

/**
 * An Auction power.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public class Auction_Power extends Power {

    private static final String NAME = "auction";
    
    Auction_Power() {
        super(NAME);
    }
}
