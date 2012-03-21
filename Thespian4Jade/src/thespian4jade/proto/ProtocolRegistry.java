package thespian4jade.proto;

import java.util.HashMap;
import java.util.Map;
import thespian4jade.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import thespian4jade.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import thespian4jade.proto.organizationprotocol.publisheventprotocol.PublishEventProtocol;
import thespian4jade.proto.organizationprotocol.subscribetoeventprotocol.SubscribeToEventProtocol;
import thespian4jade.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import thespian4jade.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;
import thespian4jade.proto.roleprotocol.invokecompetenceprotocol.InvokeCompetenceProtocol;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.InvokeResponsibilityProtocol;

public class ProtocolRegistry {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    public static final String ENACT_ROLE_PROTOCOL = "EnactRoleProtocol";
    public static final String DEACT_ROLE_PROTOCOL = "DeactRoleProtocol";
    public static final String SUBSCRIBE_TO_EVENT_PROTOCOL = "SubscribeToEventProtocol";
    public static final String PUBLISH_EVENT_PROTOCOL = "PublishEventProtocol";
    public static final String ACTIVATE_ROLE_PROTOCOL = "ActivateRoleProtocol";
    public static final String DEACTIVATE_ROLE_PROTOCOL = "DeactivateRoleProtocol";
    public static final String INVOKE_COMPETENCE_PROTOCOL = "InvokeCompetenceProtocol";
    public static final String INVOKE_RESPONSIBILITY_PROTOCOL = "InvokeResponsibilityProtocol";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final Map<String, Protocol> protocolSingletons = new HashMap<String, Protocol>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    static {
        protocolSingletons.put(ENACT_ROLE_PROTOCOL, new EnactRoleProtocol());
        protocolSingletons.put(DEACT_ROLE_PROTOCOL, new DeactRoleProtocol());
        protocolSingletons.put(SUBSCRIBE_TO_EVENT_PROTOCOL, new SubscribeToEventProtocol());
        protocolSingletons.put(PUBLISH_EVENT_PROTOCOL, new PublishEventProtocol());
        protocolSingletons.put(ACTIVATE_ROLE_PROTOCOL, new ActivateRoleProtocol());
        protocolSingletons.put(DEACTIVATE_ROLE_PROTOCOL, new DeactivateRoleProtocol());
        protocolSingletons.put(INVOKE_COMPETENCE_PROTOCOL, new InvokeCompetenceProtocol());
        protocolSingletons.put(INVOKE_RESPONSIBILITY_PROTOCOL, new InvokeResponsibilityProtocol());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public static synchronized Protocol getProtocol(String protocolName) {
        return protocolSingletons.get(protocolName);
    }
    
    // </editor-fold>
}
