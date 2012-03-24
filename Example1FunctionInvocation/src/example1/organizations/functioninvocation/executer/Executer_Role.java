/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example1.organizations.functioninvocation.executer;

import thespian4jade.core.organization.Role;

/**
 * An Executer role.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Executer_Role extends Role {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    public static final String NAME = "Executer_Role";
    
    public static final String EXECUTE_RESPONSIBILITY = "ExecuteFunction_Responsibility";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        addBehaviour(new Executer_Responder());
        logInfo("Behaviours added.");

        // Add competences.
        // No competences.
    }

    // </editor-fold>
}
