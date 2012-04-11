package example3.protocols;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.IMessageFactory;
import thespian4jade.language.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An 'Bid proposal' message is a message sent by the 'Auction'
 * protocol responder party (a bidder) to the initiator party (an auctioneer) and
 * contains a proposal for a bid.
 * @author Lukáš Kúdela
 * @since 2012-01-24
 * @version %I% %G%
 */
public class BidProposeMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The bid amount.
     */
    private double bidAmount;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the BidProposeMessage class.
     */
    public BidProposeMessage() {
        super(ACLMessage.PROPOSE);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the bid amount.
     * @return the bid amount
     */
    public Double getBidAmount() {
        return bidAmount;
    }
    
    /**
     * Sets the bid amount.
     * @param bid the bid amount
     * @return this 'Bid propose' message (Design pattern: Fluent interface)
     */
    public BidProposeMessage setBid(double bid) {
        this.bidAmount = bid;
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
        return String.format("bid(%1$s)", bidAmount);
    }

    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    @Override
    protected void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("bid\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        bidAmount = new Double(matcher.group(1)).doubleValue();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * A 'Bid propose' message factory.
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */
    public static class Factory implements IMessageFactory<BidProposeMessage> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Bid' message.
         * @return an empty 'Bid' message
         */
        @Override
        public BidProposeMessage createMessage() {
            return new BidProposeMessage();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
