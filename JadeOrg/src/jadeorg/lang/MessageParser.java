package jadeorg.lang;

import jade.lang.acl.ACLMessage;

/**
 * A message parser.
 * DP: Abstract factory - Abstract product
 * @author Lukáš Kúdela (2011-10-21)
 * @version 0.1
 */
public abstract class MessageParser {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public abstract Message parse(ACLMessage message);
    
    // </editor-fold>
}
