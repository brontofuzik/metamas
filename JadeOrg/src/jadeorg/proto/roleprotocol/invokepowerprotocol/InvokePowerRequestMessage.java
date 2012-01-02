package jadeorg.proto.roleprotocol.invokepowerprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A 'Invoke power request' message.
 * @author Lukáš Kúdela
 * @since 2011-11-09
 * @version %I% %G%
 */
public class InvokePowerRequestMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String powerName;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public InvokePowerRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public String getPower() {
        return powerName;
    }
    
    public InvokePowerRequestMessage setPower(String powerName) {
        this.powerName = powerName;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public String generateContent() {
        return String.format("invoke-power(%1$s)", powerName);
    }

    @Override
    public void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("invoke-power\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        powerName = matcher.group(1);
    }
     
    // </editor-fold>
}
