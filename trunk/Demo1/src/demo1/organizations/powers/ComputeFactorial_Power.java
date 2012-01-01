package demo1.organizations.powers;

import jadeorg.core.organization.Power;

/**
 * The 'Compute factorial' power.
 * @author Lukáš Kúdela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class ComputeFactorial_Power extends Power {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "compute-factorial";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ComputeFactorial_Power() {
        super(NAME);
    }
    
    // </editor-fold>
}
