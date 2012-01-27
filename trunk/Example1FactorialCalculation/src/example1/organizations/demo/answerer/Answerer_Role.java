/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example1.organizations.demo.answerer;

import jadeorg.core.organization.Role;

/**
 * An Answerer role.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Answerer_Role extends Role {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes the Answerer_Role class.
     */
    static {
        addRequirement("CalculateRequirement_Bid");
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        addBehaviour(new Answerer_Responder());
        logInfo("Behaviours added.");

        // Add powers.
        // No powers.
    }

    // </editor-fold>
}
