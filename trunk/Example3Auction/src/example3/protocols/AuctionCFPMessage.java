package example3.protocols;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.IMessageFactory;
import thespian4jade.language.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An 'Auction call-for-proposals' message is a message sent by the 'Auction'
 * protocol initiator party (an auctioneer) to the responder parties (bidders) and
 * contains a call for proposals for bids.
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
     * @return this 'Auction CFP' message (Design pattern: Fluent interface)
     */
    public AuctionCFPMessage setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Generates the content of corresponding the ACL message.
     * @return the content of the ACL message
     */
    @Override
    protected String generateContent() {
        return String.format("auction(%1$s)", itemName);
    }

    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
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
     * A 'Auction call-for-proposals' message factory.
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */
    public static class Factory implements IMessageFactory<AuctionCFPMessage> {

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
