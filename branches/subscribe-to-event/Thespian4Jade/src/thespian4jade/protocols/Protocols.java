package thespian4jade.protocols;

import thespian4jade.protocols.organization.deactrole.DeactRoleProtocol;
import thespian4jade.protocols.organization.enactrole.EnactRoleProtocol;
import thespian4jade.protocols.organization.publishevent.PublishEventProtocol;
import thespian4jade.protocols.organization.subscribetoevent.SubscribeToEventProtocol;
import thespian4jade.protocols.role.activaterole.ActivateRoleProtocol;
import thespian4jade.protocols.role.deactivaterole.DeactivateRoleProtocol;
import thespian4jade.protocols.role.invokecompetence.InvokeCompetenceProtocol;
import thespian4jade.protocols.role.invokeresponsibility.InvokeResponsibilityProtocol;

/**
 * A static class containing the application-agnostic (infrastructure) protocols
 * used in Thespian4Jade.
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public /* static */ class Protocols {

    // Organization protocols
    public static final Class ENACT_ROLE_PROTOCOL = EnactRoleProtocol.class;
    public static final Class DEACT_ROLE_PROTOCOL = DeactRoleProtocol.class;
    public static final Class SUBSCRIBE_TO_EVENT_PROTOCOL = SubscribeToEventProtocol.class;
    public static final Class PUBLISH_EVENT_PROTOCOL = PublishEventProtocol.class;
    
    // Role protocols
    public static final Class ACTIVATE_ROLE_PROTOCOL = ActivateRoleProtocol.class;
    public static final Class DEACTIVATE_ROLE_PROTOCOL = DeactivateRoleProtocol.class;
    public static final Class INVOKE_COMPETENCE_PROTOCOL = InvokeCompetenceProtocol.class;
    public static final Class INVOKE_RESPONSIBILITY_PROTOCOL = InvokeResponsibilityProtocol.class;
}