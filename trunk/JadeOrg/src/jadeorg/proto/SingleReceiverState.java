package jadeorg.proto;

/**
 * A single receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public abstract class SingleReceiverState extends OuterReceiverState {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    static final int SINGLE_RECEIVER = 0;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public SingleReceiverState(String name) {
        super(name);
        
        addReceiver(new SingleReceiver());
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">   
    
    protected abstract int onSingleReceiver();   
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class SingleReceiver extends InnerReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "single-receiver";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SingleReceiver() {
            super(NAME, RECEIVED, null);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setExitValue(onSingleReceiver());
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
