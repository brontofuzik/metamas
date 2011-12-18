package jadeorg.proto_new.jadeextensions;

import jade.core.AID;
import jadeorg.lang.Message;

/**
 * A receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-06
 * @version %I% %G%
 */
public interface ReceiverState {
    
    boolean receive(Message message, AID senderAID);
}
