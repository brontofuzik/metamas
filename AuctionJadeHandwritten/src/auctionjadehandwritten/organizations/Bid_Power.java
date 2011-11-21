package auctionjadehandwritten.organizations;

import jadeorg.core.organization.behaviours.Power;

/**
 * A Bid power.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public class Bid_Power extends Power {
    
    private static final String NAME = "bid";
    
    Bid_Power() {
        super(NAME);
    }
}
