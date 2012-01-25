/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example1.organizations.demo.asker;

import example1.organizations.demo.asker.CalculateFactorial_Power;
import jadeorg.core.organization.Role;

/**
 * An Asker role.
 * @author Luk� K�dela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Asker extends Role {

    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        // No behaviours.

        // Add powers.
        addPower(CalculateFactorial_Power.class);
        logInfo("Powers added.");
    }

    // </editor-fold>
}
