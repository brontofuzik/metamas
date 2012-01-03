package demo1.players.requirements;

import jadeorg.core.player.requirement.SimpleRequirement;

/**
 * The 'Calculate factorial' (simple) requirement.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public class CalculateFactorial_Requirement extends SimpleRequirement {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "calculate-factorial-requirement";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public CalculateFactorial_Requirement() {
        super(NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        // Get and convert the argument.
        int argument = ((Integer)getArgument()).intValue();
        
        int result = factorial(argument);
        
        // Convert and set the result.
        setResult(new Integer(result));
    }
    
    private static int factorial(int n) {
        if (n > 1) {
            return n * factorial(n - 1);
        } else {
            return 1;
        }
    }
    
    // </editor-fold>
}
