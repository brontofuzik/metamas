package thespian4jade.proto.roleprotocol.invokepowerprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.lang.TextMessage;
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
    
    private String power;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public InvokePowerRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public String getPower() {
        return power;
    }
    
    public InvokePowerRequestMessage setPower(String powerName) {
        this.power = powerName;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public String generateContent() {
        return String.format("invoke-power(%1$s)", power);
    }

    @Override
    public void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("invoke-power\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        power = matcher.group(1);
    }
     
    // </editor-fold>
}
