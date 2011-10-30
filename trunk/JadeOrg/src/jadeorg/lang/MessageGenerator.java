package jadeorg.lang;

import jade.lang.acl.ACLMessage;

/**
 * A message generator.
 * DP: Abstract factory - Abstract product
 * @author Lukáš Kúdela
 * @since 2011-10-21
 * @version %I% %G%
 */
public abstract class MessageGenerator {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public abstract ACLMessage generate(Message message);
        
    // </editor-fold>
}
