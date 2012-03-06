package thespian4jade.util;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * A logger.
 * @author Lukáš Kúdela
 * @since 2011-10-30
 * @version %I% %G%
 */
public class Logger {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String LOG_FILE_EXTENSION = "log";
    
    private static final String LOG_MESSAGE_FORMAT = "";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The agent (organization, role, player) that owns this logger. */
    private Agent owner;
    
    /** The log file writer */
    private PrintWriter logFileWriter;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Logger(Agent owner, String logFilePath) {
        // ----- Preconditions -----
        assert owner != null;
        assert logFilePath != null && !logFilePath.isEmpty();
        // -------------------------
        
        this.owner = owner;

        // Open the log file.
        try {
            logFileWriter = new PrintWriter(new FileWriter(getLogFileName(logFilePath)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void logSentMessage(ACLMessage message) {
        log(new ACLMessageLogMessage(Direction.SENT, message));
    }
    
    public void logReceivedMessage(ACLMessage message) {
        log(new ACLMessageLogMessage(Direction.RECEIVED, message));
    }
    
    public void log(LogLevel logLevel, String logMessage) {
        log(new LogMessage(logLevel, logMessage));
    }
    
    // ---------- PRIVATE ----------
    
    /**
     * This methods is used only in constructor.
     * @param logFilePath
     * @return 
     */
    private String getLogFileName(String logFilePath) {
        String logFileName = owner.getName() + '.' + LOG_FILE_EXTENSION;
        return logFilePath + File.separatorChar + logFileName;
    }
    
    /**
     * This method is used by the log* methods.
     * @param logMessage the log message
     */
    private void log(LogMessage logMessage) {       
        logFileWriter.println(logMessage.toString());
        logFileWriter.flush();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
   
    /**
     * A general log message.
     */
    private class LogMessage {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        /**
         * 1 - log level
         * 2 - log date and time
         * 3 - log message
         */
        private static final String LOG_MESSAGE_FORMAT = "[%1$] %2$ - %3$";
        
        private static final String DATE_TIME_FORMAT = "yyyy-mm-dd hh:mm:ss";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        
        /** The level. */
        private LogLevel level;
        
        /** The date and time. */
        private Date date;
        
        /** The message. */
        private String message;
           
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public LogMessage(LogLevel level, String message) {
            this(level);
            
            // ----- Preconditions -----
            assert message != null && !message.isEmpty();
            // -------------------------
            
            this.level = level;
            this.message = message;
        }
        
        protected LogMessage(LogLevel level) {            
            this.level = level;
            date = new Date();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
        
        protected String getMessage() {
            return message;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public String toString() {
            return String.format(LOG_MESSAGE_FORMAT,
                level, dateFormat.format(date), getMessage());
        }
        
        // </editor-fold>
    }
    
    /**
     * A specific log message pertaining to sent/received ACL messages.
     */
    private class ACLMessageLogMessage extends LogMessage {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        /**
         * 1 - header ("SENT TO: ..." or "RECEIVED FROM: ...")
         * 2 - protocol
         * 3 - performative
         * 4 - content
         */
        private static final String MESSAGE_FORMAT = "%1$ - $2$ - %3$ - %4$";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private Direction direction;
        
        private AID sender;
        
        // TODO Get all receivers instead of just the first one.
        private AID receiver;
        
        private String protocol;
        
        private int performative;
        
        private String content;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public ACLMessageLogMessage(Direction direction, ACLMessage message) {
            super(LogLevel.INFO);
            
            // ----- Preconditions -----
            assert message != null;
            // -------------------------
            
            this.direction = direction;         
            if (this.direction == Direction.SENT) {              
                receiver = getMessageReceivers(message).get(0);
            } else {
                sender = message.getSender();
            }

            protocol = message.getProtocol();
            performative = message.getPerformative();
            content = message.getContent();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
        
        @Override
        protected String getMessage() {
            return String.format(MESSAGE_FORMAT,
                getHeader(), protocol, getPerformative(), content);
        }
        
        // ---------- PRIVATE ----------
        
        private String getHeader() {
            if (direction == Direction.SENT) {
                return String.format("SENT TO: %1$", receiver);
            } else {
                return String.format("RECEIVED FROM: %1$", sender);
            }
        }
        
        private String getPerformative() {
            return ACLMessage.getPerformative(performative);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        private List<AID> getMessageReceivers(ACLMessage message) {
            List<AID> receivers = new ArrayList<AID>();
            Iterator iterator = message.getAllReceiver();
            while (iterator.hasNext()) {
                AID receiver = (AID)iterator.next();
                receivers.add(receiver);
            }
            return receivers;
        }
        
        // </editor-fold>
    }
    
    /**
     * The log level enumeration.
     */
    public enum LogLevel {
        INFO,
        WARNING,
        ERROR
    }
    
    /**
     * The direction (i. e. sent/received) enumeration.
     */
    public enum Direction {
        SENT,
        RECEIVED
    }
    
    // </editor-fold>
}
