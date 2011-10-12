package demoorgjadehandwritten.agents;

import jadeorg.Agent;

/**
 *
 * @author Lukáš Kúdela (2010-10-11)
 */
public class HumanAgent extends Agent
    implements TerrorizeSkill {

    @Override
    public void terrorize() {
        System.out.println("Terrorizing ...");
    }
    
}
