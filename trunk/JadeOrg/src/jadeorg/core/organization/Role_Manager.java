package jadeorg.core.organization;

import jade.lang.acl.ACLMessage;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import jadeorg.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerProtocol;
import jadeorg.util.ManagerBehaviour;

/**
 * A role manager behaviour.
 * @author Lukáš Kúdela
 * @since 2011-12-18
 * @version %I% %G%
 */
public class Role_Manager extends ManagerBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
        
    Role_Manager() {
        addHandler(new ActivateRoleHandler());
        addHandler(new DeactivateRoleHandler());
        addHandler(new InvokePowerHandler());
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    private Role getMyRole() {
        return (Role)myAgent;
    }
    
    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Classes">
        
    /**
     * The 'Activate role' protocol handler.
     */
    private class ActivateRoleHandler extends HandlerBehaviour {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ActivateRoleHandler() {
            super(ActivateRoleProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void handleMessage(ACLMessage message) {
            getMyRole().respondToActivateRole(message);
        }
        
        // </editor-fold>
    }
        
    /**
     * The 'Deactivate role' protocol handler.
     */
    private class DeactivateRoleHandler extends HandlerBehaviour {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        DeactivateRoleHandler() {
            super(DeactivateRoleProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void handleMessage(ACLMessage message) {
            getMyRole().respondToDeactivateRole(message);
        }
        
        // </editor-fold>
    }
        
    /**
     * The 'Invoke power' protocol handler.
     */
    private class InvokePowerHandler extends HandlerBehaviour {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        InvokePowerHandler() {
            super(InvokePowerProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void handleMessage(ACLMessage message) {
            getMyRole().respondToInvokePower(message);
        }
        
        // </editor-fold>
    }
        
    // </editor-fold>
}
