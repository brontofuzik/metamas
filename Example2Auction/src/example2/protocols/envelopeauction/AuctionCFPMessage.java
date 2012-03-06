package example2.protocols.envelopeauction;

import jade.lang.acl.ACLMessage;
import thespian4jade.lang.MessageFactory;
import thespian4jade.lang.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An 'Auction CFP' message.
 * An 'Auciton CFP' message is sent by an auctioneer to bidders. 
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public class AuctionCFPMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The name of the item.
     */
    private String itemName;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the AuctionCFPMessage class.
     */
    public AuctionCFPMessage() {
        super(ACLMessage.CFP);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the item.
     * @return the name of the item
     */
    public String getItemName() {
        return itemName;
    }
    
    /**
     * Sets the name of the item.
     * @param itemName the name of the item
     * @return this 'Auction CFP' message (fluent interface)
     */
    public AuctionCFPMessage setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Generates the ACL message content.
     * @return the ACL message content
     */
    @Override
    protected String generateContent() {
        return String.format("auction(%1$s)", itemName);
    }

    /**
     * Parses the ACL message content.
     * @param content the ACL message content
     */
    @Override
    protected void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("auction\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        itemName = matcher.group(1);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * A 'Auction CFP' message factory.
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */
    public static class Factory implements MessageFactory<AuctionCFPMessage> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Auction CFP' message.
         * @return an empty 'Auction CFP' message
         */
        @Override
        public AuctionCFPMessage createMessage() {
            return new AuctionCFPMessage();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
