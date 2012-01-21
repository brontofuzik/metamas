package jadeorg.proto;

/**
 * An initiator party.
 * @author Lukáš Kúdela
 * @since 2012-01-09
 * @version %I% %G%
 */
public abstract class InitiatorParty extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected InitiatorParty(Protocol protocol) {
        super(protocol);
        setProtocolId(new Integer(hashCode()).toString());
    }
    
    // </editor-fold>
}
