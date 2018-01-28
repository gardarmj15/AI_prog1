import Helpers.Actions;
import Helpers.Environment;
import Helpers.Point2D;
import Helpers.State;
import Search.BFS;
import Search.DFS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyAgent implements Agent
{
    private State state = new State();
    private Environment env = new Environment();
    private BFS bfsSearch;
    private DFS dfsSearch;
    private ArrayList<String> actionList;

    public void init(Collection<String> percepts) {
		state = env.init(percepts);
		dfsSearch = new DFS(state, env);
		actionList = new ArrayList<>();
		actionList = dfsSearch.startSearch();
		System.out.println("INIT");
    }

    public String nextAction(Collection<String> percepts) {
        System.out.println("next");
        System.out.print("perceiving:");
        for (String percept : percepts) {
            System.out.print("'" + percept + "', ");
        }
        System.out.println("");
        return Actions.TURN_RIGHT;
    }
}
