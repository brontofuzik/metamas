package metamas.exceptions;

import metamas.semanticmodel.Group;

/**
 *
 * @author Lukáš Kúdela
 */
public class PositionAlreadyOccupiedError extends CompileTimeError {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String positionName;
    
    private Group group;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public PositionAlreadyOccupiedError(String positionName, Group group) {
        this.positionName = positionName;
        this.group = group;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
   
    @Override
    public String getMessage() {
        return String.format(
            "The position '%1$' is already occupied in the group '%2$'.",
            positionName, group.getName());
    }
    
    // </editor-fold>  
}
