package example3metamodel;

import thespian.semanticmodel.MultiAgentSystem;
import thespian.semanticmodel.fsm.FSM;
import thespian.semanticmodel.organization.Competence;
import thespian.semanticmodel.organization.OrganizationType;
import thespian.semanticmodel.organization.Role;
import thespian.semanticmodel.player.PlayerType;
import thespian.semanticmodel.player.Responsibility;
import thespian.semanticmodel.protocol.Message;
import thespian.semanticmodel.protocol.Party;
import thespian.semanticmodel.protocol.Protocol;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-17
 * @version %I% %G%
 */
public class Example3Metamodel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MultiAgentSystem model = createModel();
        model.generate("C:\\DATA\\projects\\MAS\\MetaMAS");
    }
    
    // ----- PRIVATE -----

    private static MultiAgentSystem createModel() {
        MultiAgentSystem example3Mas = new MultiAgentSystem("Example3Auction", "example3");

        // ---------- Protocols ----------
        
        example3Mas.addProtocol(createEnvelopeAuctionProtocol());
        example3Mas.addProtocol(createVickreyAuctionProtocol());
        example3Mas.addProtocol(createEnglishAuctionProtocol());
        example3Mas.addProtocol(createDutchAuctionProtocol());

        // ---------- Organizations ----------
        
        OrganizationType auctionOrganizationType = createAuctionOrganizationType();        
        example3Mas.addOrganizationType(auctionOrganizationType);
        example3Mas.addOrganization(auctionOrganizationType.createOrganization("auction_Organization"));

        // ---------- Players ----------
        
        PlayerType participantPlayerType = createParticipantPlayerType();
        example3Mas.addPlayerType(participantPlayerType);
        example3Mas.addPlayer(participantPlayerType.createPlayer("participant1_Player"));
        example3Mas.addPlayer(participantPlayerType.createPlayer("participant2_Player"));
        example3Mas.addPlayer(participantPlayerType.createPlayer("participant3_Player"));
        
        return example3Mas;
    }
    
    // ---------- Protocols ----------

    private static Protocol createEnvelopeAuctionProtocol() {
        Protocol envelopeAuctionProtocol = new Protocol("EnvelopeAuctionProtocol");
        
        // ---------- Parties ----------
        
        // The initiator party.
        Party initiatorParty = new Party("EnvelopeAuction_InitiatorParty");
        initiatorParty.setFSM(createEvaluateExpressionInitiatorFSM());
        envelopeAuctionProtocol.setInitiatorParty(initiatorParty);
        
        // The responder party.
        Party responderParty = new Party("EnvelopeAuciton_ResponderParty");
        responderParty.setFSM(createEvaluateExpressionResponderFSM());
        envelopeAuctionProtocol.setResponderParty(responderParty);
        
        // ---------- Messages ----------
        
        envelopeAuctionProtocol.addMessage(new Message("AuctionCFPMessage", Message.MessageType.TextMessage));
        envelopeAuctionProtocol.addMessage(new Message("BidMessage", Message.MessageType.TextMessage));
        
        return envelopeAuctionProtocol;
    }
    
    private static FSM createEvaluateExpressionInitiatorFSM() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private static FSM createEvaluateExpressionResponderFSM() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private static Protocol createVickreyAuctionProtocol() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private static Protocol createEnglishAuctionProtocol() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private static Protocol createDutchAuctionProtocol() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    // ---------- Organizations ----------
    
    private static OrganizationType createAuctionOrganizationType() {
        OrganizationType auctionOrganizationType = new OrganizationType("Auction_Organization");
        
        // ---------- Roles ----------
        
        auctionOrganizationType.addRole(createAuctioneerRole());
        auctionOrganizationType.addRole(createBidderRole());
        
        return auctionOrganizationType;
    }
    
    private static Role createAuctioneerRole() {
        Role auctioneerRole = new Role("Auctioneer_Role");
        
        // The 'Auction' competence.
        Competence auctionCompetence = new Competence("Auction_Competence",
            Competence.CompetenceType.Synchronous, "AuctionArgument", "AuctionResult");
        auctionCompetence.setFSM(createAuctionCompetenceFSM());
        auctioneerRole.addCompetence(auctionCompetence);
        
        return auctioneerRole;
    }
    
    private static FSM createAuctionCompetenceFSM() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private static Role createBidderRole() {
        Role bidderRole = new Role("Bidder_Role");
        return bidderRole;
    }
    
    // ---------- Players ----------

    private static PlayerType createParticipantPlayerType() {
        PlayerType participantPlayerType = new PlayerType("Participant_Player");
        
        // The 'Bid' responsibility.
        Responsibility addResponsibility = new Responsibility("Bid_Responsibility",
                Responsibility.ResponsibilityType.Asynchronous, "BidArgument", "BidResult");
        participantPlayerType.addResponsibility(addResponsibility);
        
        return participantPlayerType;
    }
}
