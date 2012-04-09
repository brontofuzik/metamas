package thespian4jade.language;

/**
 * An interface of a message factory.
 * @param <TMessage> the type of the message factory
 * @author Lukáš Kúdela
 * @since 2012-01-24
 * @version %I% %G%
 */
public interface IMessageFactory<TMessage> {
    
    /**
     * Creates an empty message.
     * @return an empty message
     */
    TMessage createMessage();
}
