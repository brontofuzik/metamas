package jadeorg.core.organization;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import jadeorg.util.ManagerBehaviour;

/**
 * An organization manager behaviour.
 * @author Lukáš Kúdela
 * @since 2011-12-16
 * @version %I% %G%
 */
public class Organization_Manager extends ManagerBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    Organization_Manager() {
        addHandler(new EnactRoleHandler());
        addHandler(new DeactRoleHandler());
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
    private class EnactRoleHandler extends HandlerBehaviour {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        EnactRoleHandler() {
            super(EnactRoleProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void handleMessage(ACLMessage message) {
            getMyOrganization().respondToEnactRole(message);
        }
        
        // </editor-fold>
    }

    /**
     * The 'Deact role' protocol handler.
     */
    private class DeactRoleHandler extends HandlerBehaviour {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        DeactRoleHandler() {
            super(DeactRoleProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void handleMessage(ACLMessage message) {
            getMyOrganization().respondToDeactRole(message);
        }
        
        // </editor-fold>
    }

    // </editor-fold>
}
