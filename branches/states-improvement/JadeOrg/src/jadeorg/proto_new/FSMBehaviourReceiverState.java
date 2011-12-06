package jadeorg.proto_new;

import jadeorg.lang.Message;

/**
 * A FSM behaviour receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-05
 * @version %I% %G%
 */
abstract class FSMBehaviourReceiverState extends FSMBehaviourState implements ReceiverState {

    protected FSMBehaviourReceiverState(String name) {
        super(name);
    }
    
    @Override
    public Message receive(Class messageClass) {
        return getParty().receive(messageClass, null);
    } 
}
