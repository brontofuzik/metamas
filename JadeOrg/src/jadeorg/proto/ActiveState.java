package jadeorg.proto;

import jadeorg.lang.Message;

/**
 * An active protocol state.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public abstract class ActiveState extends State {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ActiveState(String name) {
        super(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Sends a JadeOrg message.
     * @param messageClass the message class
     * @param message the message
     */
    public void send(Class messageClass, Message message) {
        getParty().send(messageClass, message);
    }
    
    // </editor-fold>
}
