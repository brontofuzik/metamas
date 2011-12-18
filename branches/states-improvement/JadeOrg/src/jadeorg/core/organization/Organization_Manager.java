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
     * 
     */
    private class EnactRoleHandler extends HandlerBehaviour {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            MessageTemplate template = MessageTemplate.and(
                EnactRoleProtocol.getInstance().getTemplate(),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
                 
            ACLMessage enactRequestMessage = getMyOrganization().receive(template);          
            if (enactRequestMessage != null) {
                getMyOrganization().putBack(enactRequestMessage);
                getMyOrganization().enactRoleResponder(enactRequestMessage.getSender());
            }
        }
        
            // </editor-fold>
    }

    /**
     * 
     */
    private class DeactRoleHandler extends HandlerBehaviour {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            MessageTemplate template = MessageTemplate.and(
                DeactRoleProtocol.getInstance().getTemplate(),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

            ACLMessage deactRequestMessage = getMyOrganization().receive(template);          
            if (deactRequestMessage != null) {
                getMyOrganization().putBack(deactRequestMessage);
                getMyOrganization().deactRoleResponder(deactRequestMessage.getSender());
            }
        }
        
        // </editor-fold>
    }

    // </editor-fold>
}
