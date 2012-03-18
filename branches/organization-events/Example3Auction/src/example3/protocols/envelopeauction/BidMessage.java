package example3.protocols.envelopeauction;

import jade.lang.acl.ACLMessage;
import thespian4jade.lang.MessageFactory;
import thespian4jade.lang.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 'Bid' message.
 * A 'Bid' message is sent by a bidder to an auctioneer.
 * @author Lukáš Kúdela
 * @since 2012-01-24
 * @version %I% %G%
 */
public class BidMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The bid.
     */
    private double bid;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public BidMessage() {
        super(ACLMessage.PROPOSE);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the bid.
     * @return the bid
     */
    public Double getBid() {
        return bid;
    }
    
    /**
     * Sets the bid.
     * @param bid the bid
     * @return this 'Bid' message (fluent interface)
     */
    public BidMessage setBid(double bid) {
        this.bid = bid;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Generates the 'Bid' message content.
     * @return the 'Bid' message content
     */
    @Override
    protected String generateContent() {
        return String.format("bid(%1$s)", bid);
    }

    /**
     * Parses the 'Bid' message content.
     * @param content the 'Bid' message content
     */
    @Override
    protected void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("bid\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        bid = new Double(matcher.group(1)).doubleValue();
    }


    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * A 'Bid' message factory.
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */
    public static class Factory implements MessageFactory<BidMessage> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Bid' message.
         * @return an empty 'Bid' message
         */
        @Override
        public BidMessage createMessage() {
            return new BidMessage();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
