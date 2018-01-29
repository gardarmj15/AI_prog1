import Helpers.Actions;
import Helpers.Environment;
import Helpers.Point2D;
import Helpers.State;
import Search.AStar;
import Search.BFS;
import Search.DFS;
import Search.UCS;

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
    private UCS ucsSearch;
    private AStar aStarSearch;
    private ArrayList<String> actionList;

    public void init(Collection<String> percepts) {
		state = env.init(percepts);
		//prepBFS();
		//prepDFS();
        //prepUCS();
        prepAStar();
        actionList.add("TURN_ON");
    }

    public String nextAction(Collection<String> percepts) {
        /*System.out.print("perceiving:");
        for (String percept : percepts) {
            System.out.print("'" + percept + "', ");
        }*/
        while(actionList.size() > 0)
        {
            String nextAction = actionList.get(actionList.size() - 1);
            actionList.remove(actionList.size() - 1);
            return nextAction;
        }
        return "TURN_OFF";
    }

    private void prepDFS()
    {
        dfsSearch = new DFS(state, env);
        actionList = new ArrayList<>();
        actionList = dfsSearch.startSearch();
    }

    private void prepBFS()
    {
        bfsSearch = new BFS(state, env);
        actionList = new ArrayList<>();
        actionList = bfsSearch.startSearch();
    }

    private void prepUCS()
    {
        ucsSearch = new UCS(state, env);
        actionList = new ArrayList<>();
        actionList = ucsSearch.startSearch();
    }

    private void prepAStar()
    {
        aStarSearch = new AStar(state, env);
        actionList = new ArrayList<>();
        actionList = aStarSearch.startSearch();
    }
}
