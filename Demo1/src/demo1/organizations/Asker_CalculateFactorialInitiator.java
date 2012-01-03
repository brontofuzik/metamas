package demo1.organizations;

import demo1.protocols.CalculateFactorialProtocol;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;

/**
 * The 'Calculate factorial' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public class Asker_CalculateFactorialInitiator extends Party {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final String NAME = "calculate-factorial-initiator";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Asker_CalculateFactorialInitiator() {
        super(NAME);
        
        registerStatesAndTransitions();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    public Protocol getProtocol() {
        return CalculateFactorialProtocol.getInstance();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void registerStatesAndTransitions() {
        // TODO Implement.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    // </editor-fold>
}
