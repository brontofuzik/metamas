/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeorg.proto.roleprotocol.meetrequirementprotocol;

import jadeorg.proto.Protocol;

/**
 * @author Lukáš Kúdela
 * @since 2011-11-16
 * @version %I% %G%
 */
public class MeetRequirementProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "meet-requirement-protocol";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static MeetRequirementProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private MeetRequirementProtocol() {
        super(NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public static MeetRequirementProtocol getInstance() {
        if (singleton == null) {
            singleton = new MeetRequirementProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
}
