/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeorg.proto.roleprotocol.invokepowerprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.core.organization.Role_InvokePowerResponder;
import jadeorg.core.player.Player_InvokePowerInitiator;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;
import jadeorg.proto.ResponderParty;
import java.io.Serializable;

/**
 * The 'Invoke power' protocol.
 * Design pattern: Singleton, Role: Singleton
 * Design pattern: Abstract factory, Role: Concrete factory
 * @author Lukáš Kúdela
 * @since 2011-11-30
 * @version %I% %G%
 */
public class InvokePowerProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The singleton instance of this protocol. */
    private static InvokePowerProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the singleton instance of this protocol.
     * @return the singleton instance of this protocol
     */
    public static InvokePowerProtocol getInstance() {
        if (singleton == null) {
            singleton = new InvokePowerProtocol();
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
        String powerName = (String)arguments[0];
        Serializable argument = (Serializable)arguments[1];
        return new Player_InvokePowerInitiator(powerName, argument);
    }

    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new Role_InvokePowerResponder(message);
    }
    
    // </editor-fold>
}
