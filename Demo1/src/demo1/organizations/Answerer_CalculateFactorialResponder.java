/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo1.organizations;

import demo1.protocols.CalculateFactorialProtocol;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;

/**
 * The 'Calculate factorial' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public class Answerer_CalculateFactorialResponder extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "calculate-factorial-responder";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Answerer_CalculateFactorialResponder() {
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
