package thespian4jade.language;

/**
 * A message factory.
 * @param <TMessage> the message type
 * @author Lukáš Kúdela
 * @since 2012-01-24
 * @version %I% %G%
 */
public interface IMessageFactory<TMessage> {
    
    /**
     * Creates an empty message.
     * @return an empty
     */
    TMessage createMessage();
}
