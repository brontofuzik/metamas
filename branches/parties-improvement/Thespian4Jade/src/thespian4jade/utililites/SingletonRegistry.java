package thespian4jade.utililites;

import java.util.HashMap;
import java.util.Map;

/**
 * A singleton registry.
 * Note: This class is currently not used.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class SingletonRegistry<T> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static SingletonRegistry instance;
    
    private Map<String, T> map = new HashMap<String, T>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private SingletonRegistry() {
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public static SingletonRegistry getInstance() {
        if (instance == null) {
            instance = new SingletonRegistry();
        }
        return instance;
    }
    
    public T getSingleton(String singletonClassName) {
        if (map.containsKey(singletonClassName)) {
            T singleton = createSinleton(singletonClassName);
            map.put(singletonClassName, singleton);
        }
        return map.get(singletonClassName);
    }
  
    // ----- PRIVATE -----
    
    private T createSinleton(String singletonClassName) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    // </editor-fold>
}
