package jadeorg.proto.roleprotocol.invokerequirementprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.SimpleMessage;

/**
 * 
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public class ArgumentRequestMessage extends SimpleMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final String CONTENT = "requirement-argument?";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ArgumentRequestMessage() {
        super(ACLMessage.REQUEST);
        setContent(CONTENT);
    }
    
    // </editor-fold>
}
