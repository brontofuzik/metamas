package jadeorg.proto;

/**
 * An initiator party.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public abstract class InitiatorParty extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected InitiatorParty() {
        setProtocolId(new Integer(hashCode()).toString());
    }
    
    // </editor-fold>
}
