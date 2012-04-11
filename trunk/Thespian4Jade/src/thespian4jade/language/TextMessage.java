package thespian4jade.language;

import jade.lang.acl.ACLMessage;

/**
 * A message whose payload is interpreted as text.
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public abstract class TextMessage extends Message {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the TextMessage class.
     * @param performative the performative of the corresponding ACL message
     */
    protected TextMessage(int performative) {
        super(performative);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Generates the corresponding ACL message.
     * @return the corresponding ACL message
     */
    @Override
    public ACLMessage generateACLMessage() {
        ACLMessage aclMessage = new ACLMessage(getPerformative());
        aclMessage.setContent(generateContent());
        return aclMessage;
    }

    /**
     * Parses the corresponding ACL message
     * @param aclMessage the corresponding ACL message
     */    
    @Override
    public void parseACLMessage(ACLMessage aclMessage) {
        parseContent(aclMessage.getContent());
    }
    
    // ----- PROTECTED -----

    /**
     * Generates the content of corresponding the ACL message.
     * @return the content of the ACL message
     */
    protected abstract String generateContent();

    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    protected abstract void parseContent(String content);
   
    // </editor-fold>    
}
