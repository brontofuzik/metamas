package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.organization.competence.ICompetence;
import thespian4jade.proto.Initialize;
import thespian4jade.proto.ResponderParty;
import thespian4jade.proto.SendSuccessOrFailure;
import thespian4jade.proto.SingleReceiverState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.IState;
import thespian4jade.proto.roleprotocol.invokecompetenceprotocol.InvokeCompetenceProtocol;
import thespian4jade.proto.roleprotocol.invokecompetenceprotocol.InvokeCompetenceRequestMessage;
import thespian4jade.proto.roleprotocol.invokecompetenceprotocol.CompetenceArgumentMessage;
import thespian4jade.proto.roleprotocol.invokecompetenceprotocol.ArgumentRequestMessage;
import thespian4jade.proto.roleprotocol.invokecompetenceprotocol.CompetenceResultMessage;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * An 'Invoke competence' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Role_InvokeCompetence_ResponderParty<TArgument extends Serializable,
    TResult extends Serializable> extends ResponderParty<Role> {
 
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    
    /**
     * The player; more precisely its AID.
     */
    private AID player;
    
    /**
     * The name of the competence.
     */
    private String competenceName;
    
    /**
     * The 'Receive competence argument' state.
     */
    private IState receiveCompetenceArgument;
    
    /**
     * The competence state.
     */
    private ICompetence competence;
    
    /**
     * The 'Send competence result' state.
     */
    private IState sendCompetenceResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Role_InvokeCompetenceResponder class.
     * @param aclMessage the received ACL message
     */
    public Role_InvokeCompetence_ResponderParty(ACLMessage aclMessage) {
        super(InvokeCompetenceProtocol.getInstance(), aclMessage);
        
        player = getACLMessage().getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState initialize = new MyInitialize();
        IState receiveInvokeCompetenceRequest = new ReceiveInvokeCompetenceRequest();
        IState sendCompetenceArgumentRequest = new SendCompetenceArgumentRequest();
        receiveCompetenceArgument = new ReceiveCompetenceArgument();
        sendCompetenceResult = new SendCompetenceResult();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
        // ------------------
        
        // Register states.
        registerFirstState(initialize);       
        registerState(receiveInvokeCompetenceRequest);      
        registerState(sendCompetenceArgumentRequest);
        registerState(receiveCompetenceArgument);
        registerState(sendCompetenceResult);       
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register transitions.
        initialize.registerTransition(MyInitialize.OK, receiveInvokeCompetenceRequest);
        initialize.registerTransition(MyInitialize.FAIL, failureEnd);       
        receiveInvokeCompetenceRequest.registerDefaultTransition(sendCompetenceArgumentRequest);       
        sendCompetenceArgumentRequest.registerTransition(SendCompetenceArgumentRequest.SUCCESS, receiveCompetenceArgument);
        sendCompetenceArgumentRequest.registerTransition(SendCompetenceArgumentRequest.FAILURE, failureEnd);       
        sendCompetenceResult.registerTransition(SendCompetenceResult.SUCCESS, successEnd);
        sendCompetenceResult.registerTransition(SendCompetenceResult.FAILURE, failureEnd);
    }
    
    /**
     * Selects a competence specified by its name.
     * @param competenceName the name of the competence to select
     */
    private void selectCompetence(String competenceName) {
//        System.out.println("----- competenceName: " + competenceName + " -----");
        
        Class competenceClass = getMyAgent().competences.get(competenceName);
//        System.out.println("----- competenceClass: " + competenceClass + " -----");

        competence = createCompetence(competenceClass);
        
        // Register the competence-related states.
        registerState(competence);
        
        // Register the competence-related transitions.
        receiveCompetenceArgument.registerDefaultTransition(competence);
        competence.registerDefaultTransition(sendCompetenceResult);
        //System.out.println("----- COMPETENCE ADDED -----");
    }
    
    /**
     * Create a new competence from its class.
     * @param competenceClass the competence class
     * @return the competence instance
     */
    private ICompetence createCompetence(Class competenceClass) {        
        // Get the competence constructor.
        Constructor competenceConstructor = null;
        try {
            competenceConstructor = competenceClass.getConstructor();
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
                
        // Instantiate the competence.
        ICompetence competence = null;
        try {
            competence = (ICompetence)competenceConstructor.newInstance();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
        
        return competence;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int initialize() {
            getMyAgent().logInfo(String.format(
                "Responding to the 'Invoke competence' protocol (id = %1$s).",
                getACLMessage().getConversationId()));
        
            if (player.equals(getMyAgent().playerAID)) {
                // The sender player is enacting this role.
                return OK;
            } else {
                // The sender player is not enacting this role.
                // TODO (priority: low) Send a message to the player exaplaining
                // that a competence cannot be invoked on a non-enacted role.
                return FAIL;
            }
        }      
    
        // </editor-fold>
    }
    
    private class ReceiveInvokeCompetenceRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            InvokeCompetenceRequestMessage message = new InvokeCompetenceRequestMessage();
            message.parseACLMessage(getACLMessage());
            
            competenceName = message.getCompetence();                       
            selectCompetence(competenceName);
        }
        
        // </editor-fold>
    }
    
    private class SendCompetenceArgumentRequest
        extends SendSuccessOrFailure<ArgumentRequestMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Send competence argument request.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected ArgumentRequestMessage prepareMessage() {
            ArgumentRequestMessage message = new ArgumentRequestMessage();
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Competence argument request sent.");
        }
        
        // </editor-fold>
    }
    
    private class ReceiveCompetenceArgument
        extends SingleReceiverState<CompetenceArgumentMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveCompetenceArgument() {
            super(new CompetenceArgumentMessage.Factory());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving competence argument.");
        }
        
        @Override
        protected void handleMessage(CompetenceArgumentMessage message) {
            competence.setArgument(message.getArgument());
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Competence argument received.");
        }
        
        // </editor-fold>
    }
    
    private class SendCompetenceResult
        extends SendSuccessOrFailure<CompetenceResultMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending competence result.");
        }

        @Override
        protected int onManager() {
            return SUCCESS;
        }
        
        @Override
        protected CompetenceResultMessage prepareMessage() {
            CompetenceResultMessage message = new CompetenceResultMessage();
            message.setResult(competence.getResult());
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Competence result sent.");
        }
        
        // </editor-fold>
    }
    
    private class SuccessEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Invoke competence' responder party succeeded.");
        }
        
        // </editor-fold>
    }
    
    private class FailureEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Invoke competence' responder party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
