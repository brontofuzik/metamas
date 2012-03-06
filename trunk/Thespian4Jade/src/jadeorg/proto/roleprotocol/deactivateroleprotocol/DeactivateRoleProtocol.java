package jadeorg.proto.roleprotocol.deactivateroleprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.core.organization.Role_DeactivateRoleResponder;
import jadeorg.core.player.Player_DeactivateRoleInitiator;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;
import jadeorg.proto.ResponderParty;

/**
 * The 'Deactivate role' protocol.
 * Design pattern: Singleton, Role: Singleton
 * Design pattern: Abstract factory, Role: Concrete factory
 * DP: Singleton - Singleton
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
public class DeactivateRoleProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static DeactivateRoleProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public static DeactivateRoleProtocol getInstance() {
        if (singleton == null) {
            singleton = new DeactivateRoleProtocol();
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
        String roleName = (String)arguments[0];
        return new Player_DeactivateRoleInitiator(roleName);
    }

    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Role_DeactivateRoleResponder(message);
    }
    
    // </editor-fold>
}
