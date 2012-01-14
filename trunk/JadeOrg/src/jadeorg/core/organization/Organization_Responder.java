package jadeorg.core.organization;

import jade.lang.acl.ACLMessage;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import jadeorg.core.Responder;

/**
 * The organization responder.
 * @author Lukáš Kúdela
 * @since 2011-12-16
 * @version %I% %G%
 */
public class Organization_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    Organization_Responder() {
        addResponder(new EnactRoleHandler());
        addResponder(new DeactRoleHandler());
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    private Organization getMyOrganization() {
        return (Organization)myAgent;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * The 'Enact role' protocol handler.
     */
    private class EnactRoleHandler extends ResponderWrapper {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        EnactRoleHandler() {
            super(EnactRoleProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void handleMessage(ACLMessage message) {
            getMyOrganization().logInfo(String.format("Responding to the 'Enact role' protocol (id = %1$s).",
                message.getConversationId()));
            
            getMyOrganization().addBehaviour(new Organization_EnactRoleResponder(message));
        }
        
        // </editor-fold>
    }

    /**
     * The 'Deact role' protocol handler.
     */
    private class DeactRoleHandler extends ResponderWrapper {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        DeactRoleHandler() {
            super(DeactRoleProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void handleMessage(ACLMessage message) {
            getMyOrganization().logInfo(String.format("Responding to the 'Deact role' protocol (id = %1$s).",
                message.getConversationId()));
        
            getMyOrganization().addBehaviour(new Organization_DeactRoleResponder(message));
        }
        
        // </editor-fold>
    }

    // </editor-fold>
}
