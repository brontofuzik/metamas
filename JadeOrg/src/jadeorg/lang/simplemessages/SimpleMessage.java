package jadeorg.lang.simplemessages;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;

/**
 * A simple message.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public class SimpleMessage extends Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private int performative;
    
    private String content;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public SimpleMessage(int performative) {
        this.performative = performative;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    public int getPerformative() {
        return performative;
    }

    public void setPerformative(int performative) {
        this.performative = performative;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected MessageTemplate createPerformativeTemplate() {
        return MessageTemplate.MatchPerformative(performative);
    }
    
    @Override
    protected MessageGenerator createGenerator() {
        return new SimpleMessageGenerator();
    }
    
    @Override
    protected MessageParser createParser() {
        return new SimpleMessageParser();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The simple message generator.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela
     * @since 2011-11-06
     * @version %I% %G%
     */
    private static class SimpleMessageGenerator extends MessageGenerator {

        @Override
        public ACLMessage generate(Message message) {
            SimpleMessage simpleMessage = (SimpleMessage)message;
            
            ACLMessage aclMessage = new ACLMessage(simpleMessage.getPerformative());
            aclMessage.setContent(simpleMessage.getContent());
            return aclMessage;
        }
    }
    
    /**
     * The simple message parser.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela
     * @since 2011-11-06
     * @version %I% %G%
     */
    private static class SimpleMessageParser extends MessageParser {

        @Override
        public Message parse(ACLMessage aclMessage) {
            SimpleMessage simpleMessage = new SimpleMessage(aclMessage.getPerformative());
            simpleMessage.setContent(aclMessage.getContent());
            return simpleMessage;
        }
    }
    
    // </editor-fold>
}
