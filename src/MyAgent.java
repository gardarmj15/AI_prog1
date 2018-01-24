import Helpers.Actions;
import Helpers.Environment;
import Helpers.Point2D;
import Helpers.State;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyAgent implements Agent
{
    private State state = new State();
    private Environment env = new Environment();

    public void init(Collection<String> percepts) {
		state = env.init(percepts);
    }

    public String nextAction(Collection<String> percepts) {
        System.out.print("perceiving:");
        for(String percept:percepts) {
            System.out.print("'" + percept + "', ");
        }
        System.out.println("");
        return Actions.TURN_RIGHT;
    }
}
