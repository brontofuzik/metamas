package jadeorg.proto_new;

import jadeorg.lang.Message;

/**
 * A receiver state.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public interface ReceiverState {
    
    Message receive(Class messageClass);
}
