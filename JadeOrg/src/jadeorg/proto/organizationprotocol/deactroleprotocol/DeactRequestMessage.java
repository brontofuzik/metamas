/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeorg.proto.organizationprotocol.deactroleprotocol;

import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRequestMessage;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;

/**
 * A 'Deact request' message.
 * A 'Deact request' message is a message send from a player to an organization
 * containing the request to deact a role.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public class DeactRequestMessage extends Message {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String roleName;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    DeactRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getRoleName() {
        return roleName;
    }
    
    public DeactRequestMessage setRoleName(String roleName) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        // -------------------------
        
        this.roleName = roleName;
        return this;
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public String generateContent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void parseContent(String content) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // </editor-fold>
}