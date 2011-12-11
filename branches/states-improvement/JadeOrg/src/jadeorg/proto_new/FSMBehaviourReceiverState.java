package jadeorg.proto_new;

import jade.core.AID;
import jadeorg.lang.Message;

/**
 * A FSM behaviour receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-05
 * @version %I% %G%
 */
public abstract class FSMBehaviourReceiverState extends FSMBehaviourState implements ReceiverState {

    protected FSMBehaviourReceiverState(String name) {
        super(name);
    }
    
    public Message receive(Class messageClass, AID senderAID) {
        return getParty().receive(messageClass, senderAID);
    } 
    
    public Message receive(Class messageClass) {
        return getParty().receive(messageClass, null);
    } 
}
