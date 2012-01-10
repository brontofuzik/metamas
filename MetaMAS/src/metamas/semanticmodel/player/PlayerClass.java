package metamas.semanticmodel.player;

import metamas.utilities.Assert;

/**
 * @author Lukáš Kúdela
 * @since 2012-01-10
 * @version %I% %G%
 */
public class PlayerClass {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String name;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public PlayerClass(String name) {
        Assert.isNotEmpty(name, "name");

        this.name = name;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getName() {
        return name;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">

    public Player createAgent(String name) {
        Assert.isNotEmpty(name, "name");

        return new Player(name, this);
    }

    // </editor-fold>
}
