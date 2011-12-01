package jadeorg.util;

import jade.core.AID;
import jade.lang.acl.MessageTemplate;

/**
 * The message template builder.
 * @author Lukáš Kúdela
 * @since 2011-11-13
 * @version %I% %G%
 */
public class MessageTemplateBuilder {

    public static MessageTemplate createMessageTemplate(String protocol, int[] performatives, AID senderAID) {
        // Match protocol.
        MessageTemplate messageTemplate = MessageTemplate.MatchProtocol(protocol);
        
        // Match peformatives.
        MessageTemplate performativeTemplate = null;
        for (int performative : performatives) {
            performativeTemplate = MessageTemplate.or(
                performativeTemplate,
                MessageTemplate.MatchPerformative(performative));
        }
        messageTemplate = messageTemplate.and(
            messageTemplate,
            messageTemplate);
        
        // Match sender AID.
        messageTemplate = MessageTemplate.and(
            messageTemplate,
            MessageTemplate.MatchSender(senderAID));
        return messageTemplate;
    }
}
