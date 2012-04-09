package thespian4jade.protocols.organization.enactrole;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.IMessageFactory;
import thespian4jade.language.TextMessage;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 'Responsibilities' (text) message is a message send from an organization to a player
 * containing information about responsibilities to enact a certain role.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public class ResponsibilitiesMessage extends TextMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The responsibilities.
     */
    private String[] responsibilities;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the ResponsibilitiesMessage class.
     * The corresponding ACL message has the INFORM performative.
     */
    public ResponsibilitiesMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the responsibilities.
     * @return the responsibilities
     */
    public String[] getResponsibilities() {
        return Arrays.copyOf(responsibilities, responsibilities.length);
    }
    
    /**
     * Sets the responsibilities.
     * @responsibilities the responsibilities
     * @return this 'Responsibilities' message (Design pattern: Fleunt interface)
     */
    public ResponsibilitiesMessage setResponsibilities(String[] responsibilities) {
        this.responsibilities = Arrays.copyOf(responsibilities, responsibilities.length);
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Generates the content of corresponding the ACL message.
     * @return the content of the ACL message
     */
    @Override
    public String generateContent() {
        return String.format("responsibilities(%1$s)",
            thespian4jade.utililites.StringUtils.join(responsibilities, ","));
    }

    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    @Override
    public void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("responsibilities\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();

        String responsibilitiesString = matcher.group(1);
        String[] responsibilities = !responsibilitiesString.isEmpty() ?
            responsibilitiesString.split(",") :
            new String[0];
        this.responsibilities = responsibilities;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * A 'Responsibilities' message factory. 
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */ 
    public static class Factory implements IMessageFactory<ResponsibilitiesMessage> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Responsibilities inform' message.
         * @return an empty 'Responsibilities inform' message
         */
        @Override
        public ResponsibilitiesMessage createMessage() {
            return new ResponsibilitiesMessage();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
