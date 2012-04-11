package example3.organizations.auction.auctioneer;

import thespian4jade.core.organization.Role;

/**
 * The 'Auctioneer' role.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Auctioneer_Role extends Role {

    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        // No behaviours.

        // Add competences.
        addCompetence(Auction_Competence.class);
        logInfo("Competences added.");
    }

    // </editor-fold>
}