package jadeorg.proto.jadeextensions;

import jade.core.AID;
import jadeorg.lang.Message;

/**
 * A sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-05
 * @version %I% %G%
 */
public interface SenderState extends State {
   
    /**
     * Sends a message.
     * @param message the message to send
     * @param receivers the receivers. More precisely, their AIDs
     */
    public void send(Message message, AID[] receivers);
    
    public void send(Message message, AID receiver);
}
