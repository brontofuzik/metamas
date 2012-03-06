package thespian4jade.proto.organizationprotocol.enactroleprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.lang.MessageFactory;
import thespian4jade.lang.TextMessage;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 'Responsibilities' message.
 * A 'Responsibilities' message is a message send from an organization to a player
 * containing information about responsibilities to enact a certain role.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public class ResponsibilitiesInformMessage extends TextMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String[] responsibilities;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ResponsibilitiesInformMessage() {
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
     */
    public ResponsibilitiesInformMessage setResponsibilities(String[] responsibilities) {
        this.responsibilities = Arrays.copyOf(responsibilities, responsibilities.length);
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public String generateContent() {
        return String.format("responsibilities(%1$s)",
            thespian4jade.util.StringUtils.join(responsibilities, ","));
    }

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
     * A 'Requiremenets inform' message factory. 
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */ 
    public static class Factory implements MessageFactory<ResponsibilitiesInformMessage> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Requirements inform' message.
         * @return an empty 'Requirements inform' message
         */
        @Override
        public ResponsibilitiesInformMessage createMessage() {
            return new ResponsibilitiesInformMessage();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
