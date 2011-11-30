/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeorg.proto.roleprotocol.invokepowerprotocol;

import jadeorg.proto.Protocol;

/**
 * @author Lukáš Kúdela
 * @since 2011-11-30
 * @version %I% %G%
 */
public class InvokePowerProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "invoke-power-protocol";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static InvokePowerProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private InvokePowerProtocol() {
        super(NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public static InvokePowerProtocol getInstance() {
        if (singleton == null) {
            singleton = new InvokePowerProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
}
