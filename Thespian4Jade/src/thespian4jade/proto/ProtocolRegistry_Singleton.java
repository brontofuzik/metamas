package thespian4jade.proto;

import java.util.HashMap;
import java.util.Map;
import thespian4jade.util.ClassHelper;

/**
 * A protocol registry - singleton version.
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public class ProtocolRegistry_Singleton {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static ProtocolRegistry_Singleton instance;
    
    private Map<Class, Protocol> protocols = new HashMap<Class, Protocol>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private ProtocolRegistry_Singleton() {
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public ProtocolRegistry_Singleton getInstance() {
        if (instance == null) {
            instance = new ProtocolRegistry_Singleton();
        }
        return instance;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public Protocol getProtocol(Class protocolClass) {
        if (!protocols.containsKey(protocolClass)) {
            Protocol protocol = ClassHelper.instantiateClass(protocolClass);
            protocols.put(protocolClass, protocol);
        }
        return protocols.get(protocolClass);
    }
    
    // </editor-fold>
}
