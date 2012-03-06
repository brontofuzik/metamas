package thespian4jade.proto;

import jade.core.Agent;

/**
 * An initiator party.
 * @author Lukáš Kúdela
 * @since 2012-01-09
 * @version %I% %G%
 */
public abstract class InitiatorParty<TAgent extends Agent>
    extends Party<TAgent> {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected InitiatorParty(Protocol protocol) {
        super(protocol);
        setProtocolId(new Integer(hashCode()).toString());
    }
    
    // </editor-fold>
}
