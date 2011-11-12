package jadeorg.proto;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jadeorg.lang.Message;

/**
 * A passive protocol state.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public abstract class PassiveState extends State {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public PassiveState(String name) {
        super(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
   
    /**
     * Receives a JadeOrg message.
     * @param messageClass the message class
     * @return the received message
     */
    public Message receive(Class messageClass, AID senderAID) {
        return getParty().receive(messageClass, senderAID);
    }
    
    // </editor-fold>
}
