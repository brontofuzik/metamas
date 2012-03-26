package example3.protocols;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.IMessageFactory;
import thespian4jade.language.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 'Bid propose' message.
 * A 'Bid propose' message is sent by a bidder to an auctioneer.
 * @author Lukáš Kúdela
 * @since 2012-01-24
 * @version %I% %G%
 */
public class BidProposeMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The bid.
     */
    private double bid;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public BidProposeMessage() {
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
    public BidProposeMessage setBid(double bid) {
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
