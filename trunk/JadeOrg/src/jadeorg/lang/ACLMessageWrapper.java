package jadeorg.lang;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * An ACL message wrapper.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public class ACLMessageWrapper extends Message {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The wrapped ACL message */
    private ACLMessage wrappedACLMessage;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public ACLMessageWrapper(ACLMessage aclMessage) {
        // ----- Preconditions ----
        assert aclMessage != null;
        // ------------------------
        
        this.wrappedACLMessage = aclMessage;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public ACLMessage getWrappedACLMessage() {
        return wrappedACLMessage;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
        
    @Override
    protected MessageTemplate createPerformativeTemplate() {
        return null;
    }
    
    @Override
    protected MessageParser createParser() {
        return new ACLMessageWrapperParser();
    }

    @Override
    protected MessageGenerator createGenerator() {
        return new ACLMessageWrapperGenerator();
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The ACL message wrapper parser.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela
     * @since 2011-11-06
     * @version %I% %G%
     */
    private class ACLMessageWrapperParser extends MessageParser {

        @Override
        public Message parse(ACLMessage message) {
            return new ACLMessageWrapper(message);
        }
    }
    
    /**
     * The ACL message wrapper generator.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela
     * @since 2011-11-06
     * @version %I% %G%
     */
    private class ACLMessageWrapperGenerator extends MessageGenerator {

        @Override
        public ACLMessage generate(Message message) {
            ACLMessageWrapper aclMessageWrapper = (ACLMessageWrapper)message;
            return aclMessageWrapper.getWrappedACLMessage();
        }
    }
    
    // </editor-fold>
}
