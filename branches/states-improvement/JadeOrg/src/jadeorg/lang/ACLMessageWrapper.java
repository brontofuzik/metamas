package jadeorg.lang;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.proto.Protocol;

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
    
    public ACLMessageWrapper() {
        wrappedACLMessage = new ACLMessage();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Sets the associated protocol.
     * @param protocol the associated protocol
     */
    @Override
    public void setProtocol(Protocol protocol) {
        super.setProtocol(protocol);
        wrappedACLMessage.setProtocol(protocol.getName());
    }
    
    @Override
    public Message setSender(AID sender) {
        super.setSender(sender);
        wrappedACLMessage.setSender(sender);
        return this;
    }

    public Message addReceiver(AID receiver) {
        super.addReceiver(receiver);
        wrappedACLMessage.addReceiver(receiver);
        return this;
    }
    
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
    protected MessageGenerator createGenerator() {
        return new ACLMessageWrapperGenerator();
    }
    
    @Override
    protected MessageParser createParser() {
        return new ACLMessageWrapperParser();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The ACL message wrapper generator.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela
     * @since 2011-11-06
     * @version %I% %G%
     */
    private static class ACLMessageWrapperGenerator extends MessageGenerator {

        @Override
        public ACLMessage generate(Message message) {
            ACLMessageWrapper aclMessageWrapper = (ACLMessageWrapper)message;
            return aclMessageWrapper.getWrappedACLMessage();
        }
    }
    
    /**
     * The ACL message wrapper parser.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela
     * @since 2011-11-06
     * @version %I% %G%
     */
    private static class ACLMessageWrapperParser extends MessageParser {

        @Override
        public Message parse(ACLMessage message) {
            return new ACLMessageWrapper(message);
        }
    }
    
    // </editor-fold>
}
