package example1.protocols.invokefunctionprotocol;

import example1.organizations.functioninvocation.executor.InvokeFunction_ResponderParty;
import example1.organizations.functioninvocation.invoker.InvokeFunction_InitiatorParty;
import jade.lang.acl.ACLMessage;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.parties.ResponderParty;

/**
 * The 'Invoke function' protocol.
 * Design pattern: Abstract factory, Role: Concrete factory
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public class InvokeFunctionProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the InvokeFunctionProtocol class.
     */
    public InvokeFunctionProtocol() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static InvokeFunctionProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public static InvokeFunctionProtocol getInstance() {
        if (singleton == null) {
            singleton = new InvokeFunctionProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a new 'Invoke function' protocol initiator party.
     * @param arguments the 'Invoke function' protocol initiator party's contructor
     * arguments:
     *     - none
     * @returns a new 'Invoke function' protocol initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        return new InvokeFunction_InitiatorParty();
    }

    /**
     * Creates a new 'Invoke function' protocol responder party.
     * @param message the ACL message to which the 'Invoke function' protocol
     * responder party responds
     * @returns a new 'Invoke function' protocol responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new InvokeFunction_ResponderParty(message);
    }
    
    // </editor-fold> 
}
