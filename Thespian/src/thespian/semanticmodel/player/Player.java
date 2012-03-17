package thespian.semanticmodel.player;

import thespian.utilities.Assert;

/**
 * An concrete player - an instance of a player class.
 * @author Lukáš Kúdela
 * @since 2012-01-11
 * @version %I% %G%
 */
public class Player {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String name;

    private PlayerType type;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player(String name, PlayerType klass) {
        Assert.isNotEmpty(name, "name");
        Assert.isNotNull(klass, "klass");

        this.name = name;
        this.type = klass;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getName() {
        return name;
    }

    public PlayerType getType() {
        return type;
    }

    // </editor-fold>
}
