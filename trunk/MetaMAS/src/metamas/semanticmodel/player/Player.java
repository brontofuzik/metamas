package metamas.semanticmodel.player;

import metamas.utilities.Assert;

/**
 * An concrete player - an instance of a player class.
 * @author Lukáš Kúdela
 * @since 2012-01-11
 * @version %I% %G%
 */
public class Player {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String name;

    private PlayerClass klass;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player(String name, PlayerClass klass) {
        Assert.isNotEmpty(name, "name");
        Assert.isNotNull(klass, "klass");

        this.name = name;
        this.klass = klass;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getName() {
        return name;
    }

    public PlayerClass getKlass() {
        return klass;
    }

    // </editor-fold>
}
