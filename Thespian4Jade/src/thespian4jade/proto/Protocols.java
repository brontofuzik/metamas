package thespian4jade.proto;

import thespian4jade.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import thespian4jade.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import thespian4jade.proto.organizationprotocol.publisheventprotocol.PublishEventProtocol;
import thespian4jade.proto.organizationprotocol.subscribetoeventprotocol.SubscribeToEventProtocol;
import thespian4jade.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import thespian4jade.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;
import thespian4jade.proto.roleprotocol.invokecompetenceprotocol.InvokeCompetenceProtocol;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.InvokeResponsibilityProtocol;

/**
 * A static class.
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public /* static */ class Protocols {

    // Organization-related protocols
    public static final Class ENACT_ROLE_PROTOCOL = EnactRoleProtocol.class;
    public static final Class DEACT_ROLE_PROTOCOL = DeactRoleProtocol.class;
    public static final Class SUBSCRIBE_TO_EVENT_PROTOCOL = SubscribeToEventProtocol.class;
    public static final Class PUBLISH_EVENT_PROTOCOL = PublishEventProtocol.class;
    
    // Role-related protocols
    public static final Class ACTIVATE_ROLE_PROTOCOL = ActivateRoleProtocol.class;
    public static final Class DEACTIVATE_ROLE_PROTOCOL = DeactivateRoleProtocol.class;
    public static final Class INVOKE_COMPETENCE_PROTOCOL = InvokeCompetenceProtocol.class;
    public static final Class INVOKE_RESPONSIBILITY_PROTOCOL = InvokeResponsibilityProtocol.class;
}
