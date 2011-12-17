package jadeorg.core.organization;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRequestMessage;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRequestMessage;
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

    private class EnactRoleHandler extends HandlerBehaviour {

        @Override
        public void action() {
            MessageTemplate enactRequestTemplate = EnactRoleProtocol.getInstance()
                .getTemplate(EnactRequestMessage.class);
            
            ACLMessage enactRequestMessage = getMyOrganization().receive(enactRequestTemplate);
            
            if (enactRequestMessage != null) {
                getMyOrganization().putBack(enactRequestMessage);
                getMyOrganization().enactRoleResponder(enactRequestMessage.getSender());
            }
        }
    }

    private class DeactRoleHandler extends HandlerBehaviour {

        @Override
        public void action() {
            MessageTemplate deactRequestTemplate = DeactRoleProtocol.getInstance()
                .getTemplate(DeactRequestMessage.class);
            
            ACLMessage deactRequestMessage = getMyOrganization().receive(deactRequestTemplate);
            
            if (deactRequestMessage != null) {
                getMyOrganization().putBack(deactRequestMessage);
                getMyOrganization().deactRoleResponder(deactRequestMessage.getSender());
            }
        }
    }

    // </editor-fold>
}
