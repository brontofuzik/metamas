package thespian4jade.proto;

import java.util.HashMap;
import java.util.Map;
import thespian4jade.utililites.ClassHelper;

/**
 * A protocol registry - static class version.
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public class ProtocolRegistry_StaticClass {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static Map<Class, Protocol> protocols = new HashMap<Class, Protocol>();
    
    // </editor-fold>   
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public static Protocol getProtocol(Class protocolClass) {
        if (!protocols.containsKey(protocolClass)) {
            Protocol protocol = ClassHelper.instantiateClass(protocolClass);
            protocols.put(protocolClass, protocol);
        }
        return protocols.get(protocolClass);
    }
    
    // </editor-fold>
}
