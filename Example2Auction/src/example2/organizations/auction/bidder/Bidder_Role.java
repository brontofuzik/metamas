package example2.organizations.auction.bidder;

import jadeorg.core.organization.Role;

/**
 * A 'Bidder' role.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Bidder_Role extends Role {

    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        addBehaviour(new Bidder_Responder());
        logInfo("Behaviours added.");

        // Add powers.
        // No powers.
    }

    // </editor-fold>
}
