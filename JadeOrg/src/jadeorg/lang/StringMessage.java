package jadeorg.lang;

import jade.lang.acl.ACLMessage;

/**
 * A string message.
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public abstract class StringMessage extends Message {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected StringMessage(int performative) {
        super(performative);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public ACLMessage generateACLMessage() {
        ACLMessage aclMessage = new ACLMessage(getPerformative());
        aclMessage.setContent(generateContent());
        return aclMessage;
    }

    @Override
    public void parseACLMessage(ACLMessage aclMessage) {
        parseContent(aclMessage.getContent());
    }
    
    // ----- PROTECTED -----

    protected abstract String generateContent();

    protected abstract void parseContent(String content);
   
    // </editor-fold>    
}
