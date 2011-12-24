package jadeorg.proto.organizationprotocol.enactroleprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.StringMessage;
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
public class RequirementsInformMessage extends StringMessage {
    
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
            jadeorg.util.StringUtil.join(requirements, ","));
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
}
