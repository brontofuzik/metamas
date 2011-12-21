package jadeorg.core.organization;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
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

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            MessageTemplate template = MessageTemplate.and(
                ActivateRoleProtocol.getInstance().getTemplate(),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
                    
            ACLMessage activateRequestMessage = getMyRole().receive(template);
            if (activateRequestMessage != null) {
                getMyRole().putBack(activateRequestMessage);
                getMyRole().activateRoleResponder(activateRequestMessage.getSender());
            }
        }
        
        // </editor-fold>
    }
        
    /**
     * The 'Deactivate role' protocol handler.
     */
    private class DeactivateRoleHandler extends HandlerBehaviour {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            MessageTemplate template = MessageTemplate.and(
                DeactivateRoleProtocol.getInstance().getTemplate(),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST));         
            
            ACLMessage deactivateRequestMessage = getMyRole().receive(template);
            if (deactivateRequestMessage != null) {
                getMyRole().putBack(deactivateRequestMessage);
                getMyRole().deactivateRoleResponder(deactivateRequestMessage.getSender());
            }
        }
        
        // </editor-fold>
    }
        
    /**
     * The 'Invoke power' protocol handler.
     */
    private class InvokePowerHandler extends HandlerBehaviour {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            MessageTemplate template = MessageTemplate.and(
                InvokePowerProtocol.getInstance().getTemplate(),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST));      
            
            ACLMessage invokeRequestMessage = getMyRole().receive(template);
            if (invokeRequestMessage != null) {
                getMyRole().putBack(invokeRequestMessage);
//                getMyRole().invokePower(invokeRequestMessage.getSender());
            }
        }
        
        // </editor-fold>
    }
        
    // </editor-fold>
}
