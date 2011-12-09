package jadeorg.proto_new;

import jadeorg.lang.Message;

/**
 * A FSM behaviour sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-05
 * @version %I% %G%
 */
public abstract class FSMBehaviourSenderState extends FSMBehaviourState implements SenderState {
   
    protected FSMBehaviourSenderState(String name) {
        super(name);
    }

    @Override
    public void send(Class messageClass, Message message) {
        getParty().send(messageClass, message);
    }
}
