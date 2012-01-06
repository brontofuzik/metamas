package demo1.organizations.powers;

import demo1.organizations.Asker_CalculateFactorialInitiator;
import jadeorg.core.organization.power.ComplexPower;

/**
 * The 'Calculate factorial' (complex) power.
 * @author Lukáš Kúdela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class CalculateFactorial_Power extends ComplexPower<Integer, Integer> {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "calculate-factorial-power";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public CalculateFactorial_Power() {
        super(NAME, new Asker_CalculateFactorialInitiator());
    }
    
    // </editor-fold>
}
