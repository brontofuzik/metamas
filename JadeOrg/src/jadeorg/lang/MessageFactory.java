package jadeorg.lang;

/**
 * A message factory.
 * @author Lukáš Kúdela
 * @since 2012-01-24
 * @version %I% %G%
 */
public interface MessageFactory<TMessage> {
    
    /**
     * Creates an empty message.
     * @return an empty
     */
    TMessage createMessage();
}
