package thespian4jade.protocols;

import java.util.HashMap;
import java.util.Map;
import thespian4jade.utililites.ClassHelper;

/**
 * A protocol registry (singleton version).
 * Architecture pattern: Registry (Fowler)
 * Design pattern: Singleton
 * Note: This class is currently not used.
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public class ProtocolRegistry_Singleton {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The single instance of this class.
     */
    private static ProtocolRegistry_Singleton instance;
  
    /**
     * The protocol singletons.
     */
    private Map<Class, Protocol> protocols = new HashMap<Class, Protocol>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the ProtocolRegistry_Singleton class.
     * Note that the constructor is private to prevent direct instantiation.
     */
    private ProtocolRegistry_Singleton() {
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the single instance of this class.
     * @return the single instance of this class
     */
    public ProtocolRegistry_Singleton getInstance() {
        if (instance == null) {
            instance = new ProtocolRegistry_Singleton();
        }
        return instance;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Gets a protocol singleton for a given protocol class.
     * @param protocolClass the class of the protocol singleton to get
     * @return the protocol singleton for the given protocol class
     */
    public Protocol getProtocol(Class protocolClass) {
        if (!protocols.containsKey(protocolClass)) {
            Protocol protocol = ClassHelper.instantiateClass(protocolClass);
            protocols.put(protocolClass, protocol);
        }
        return protocols.get(protocolClass);
    }
    
    // </editor-fold>
}
