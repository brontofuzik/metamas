package thespian4jade.protocols;

import java.util.HashMap;
import java.util.Map;
import thespian4jade.utililites.ClassHelper;

/**
 * A protocol registry - static class version.
 * Architecture pattern: Registry (Fowler)
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public /* static */ class ProtocolRegistry {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The protocol singletons.
     */
    private static Map<Class, Protocol> protocols = new HashMap<Class, Protocol>();
    
    // </editor-fold>   
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Gets a protocol singleton for a given protocol class.
     * @param protocolClass the class of the protocol singleton to get
     * @return the protocol singleton for the given protocol class
     */
    public static Protocol getProtocol(Class protocolClass) {
        if (!protocols.containsKey(protocolClass)) {
            Protocol protocol = ClassHelper.instantiateClass(protocolClass);
            protocols.put(protocolClass, protocol);
        }
        return protocols.get(protocolClass);
    }
    
    // </editor-fold>
}
