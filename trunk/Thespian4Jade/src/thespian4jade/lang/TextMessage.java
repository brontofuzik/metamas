package thespian4jade.lang;

import jade.lang.acl.ACLMessage;

/**
 * A text message.
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public abstract class TextMessage extends Message {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the TextMessage class.
     * @param performative the performative
     */
    protected TextMessage(int performative) {
        super(performative);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Generates the ACL message.
     * @return the generated ACL message
     */
    @Override
    public ACLMessage generateACLMessage() {
        ACLMessage aclMessage = new ACLMessage(getPerformative());
        aclMessage.setContent(generateContent());
        return aclMessage;
    }

    /**
     * Parses the ACL message
     * @param aclMessage the ACL message to parse
     */    
    @Override
    public void parseACLMessage(ACLMessage aclMessage) {
        parseContent(aclMessage.getContent());
    }
    
    // ----- PROTECTED -----

    /**
     * Generates the content of the ACL message.
     * @return the content of the ACL message
     */
    protected abstract String generateContent();

    /**
     * Parses the content of the ACL message.
     * @param content the content of the ACL message
     */
    protected abstract void parseContent(String content);
   
    // </editor-fold>    
}
