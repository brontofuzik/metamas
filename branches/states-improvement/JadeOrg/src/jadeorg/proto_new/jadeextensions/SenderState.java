package jadeorg.proto_new.jadeextensions;

import jade.core.AID;
import jadeorg.lang.Message;

/**
 * A sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-05
 * @version %I% %G%
 */
public interface SenderState extends State {
   
    void send(Message message, AID receiverAID);
}
