package auctionjadehandwritten.players.requirements;

import jadeorg.core.player.Requirement;

public class ComputeFactorial_Requirement extends Requirement {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "compute-factorial";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ComputeFactorial_Requirement() {
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
