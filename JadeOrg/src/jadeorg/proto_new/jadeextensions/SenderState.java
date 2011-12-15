package jadeorg.proto_new.jadeextensions;

import jadeorg.lang.Message;

/**
 * A sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-05
 * @version %I% %G%
 */
public interface SenderState extends State {
   
    void send(Class messageClass, Message message);
}
