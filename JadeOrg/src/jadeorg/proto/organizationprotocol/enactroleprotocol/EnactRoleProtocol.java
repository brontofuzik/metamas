package jadeorg.proto.organizationprotocol.enactroleprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.core.organization.Organization_DeactRoleResponder;
import jadeorg.core.organization.Organization_EnactRoleResponder;
import jadeorg.core.player.Player_EnactRoleInitiator;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;
import jadeorg.proto.ResponderParty;

/**
 * The 'Enact role' protocol.
 * Design pattern: Singleton, Role: Singleton
 * Design pattern: Abstract factory, Role: Concrete factory
 * @author Lukáš Kúdela
 * @since 2011-10-21
 * @version %I% %G%
 */
public class EnactRoleProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
   
    private static EnactRoleProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public static EnactRoleProtocol getInstance() {
        if (singleton == null) {
            singleton = new EnactRoleProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Creates an initiator party.
     * @param arguments the initiator party's contructor arguments
     * @returns an initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        String organizationName = (String)arguments[0];
        String roleName = (String)arguments[1];
        return new Player_EnactRoleInitiator(organizationName, roleName);
    }
    
    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Organization_EnactRoleResponder(message);
    }
    
    // </editor-fold>
}
