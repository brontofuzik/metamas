package jadeorg.proto.organizationprotocol.enactroleprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.MessageFactory;
import jadeorg.lang.TextMessage;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 'Requirements' message.
 * A 'Requirements' message is a message send from an organization to a player
 * containing information about requirements to enact a certain role.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public class RequirementsInformMessage extends TextMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String[] requirements;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public RequirementsInformMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the requirements.
     * @return the requirements
     */
    public String[] getRequirements() {
        return Arrays.copyOf(requirements, requirements.length);
    }
    
    /**
     * Sets the requirements.
     * @requirements the requirements
     */
    public RequirementsInformMessage setRequirements(String[] requirements) {
        this.requirements = Arrays.copyOf(requirements, requirements.length);
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public String generateContent() {
        return String.format("requirements(%1$s)",
            jadeorg.util.StringUtils.join(requirements, ","));
    }

    @Override
    public void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("requirements\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();

        String requirementsString = matcher.group(1);
        String[] requirements = !requirementsString.isEmpty() ?
            requirementsString.split(",") :
            new String[0];
        this.requirements = requirements;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * A 'Requiremenets inform' message factory. 
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */ 
    public static class Factory implements MessageFactory<RequirementsInformMessage> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Requirements inform' message.
         * @return an empty 'Requirements inform' message
         */
        @Override
        public RequirementsInformMessage createMessage() {
            return new RequirementsInformMessage();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
