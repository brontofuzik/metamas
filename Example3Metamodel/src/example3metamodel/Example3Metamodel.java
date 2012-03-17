package example3metamodel;

import thespian.semanticmodel.MultiAgentSystem;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-17
 * @version %I% %G%
 */
public class Example3Metamodel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MultiAgentSystem model = createModel();
        model.generate("C:\\DATA\\projects\\MAS\\MetaMAS");
    }
    
    // ----- PRIVATE -----

    private static MultiAgentSystem createModel() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
