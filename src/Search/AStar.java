package Search;

import Helpers.Actions;
import Helpers.Environment;
import Helpers.PathCostComparator;
import Helpers.State;
import Nodes.AStarNode;
import Nodes.BFSNode;
import Nodes.UCSNode;

import java.util.*;

public class AStar
{
    Comparator<UCSNode> comp = new PathCostComparator();
    private State initialState;
    private Environment environment;
    //private Queue<UCSNode> Frontier;
    private AStarNode currNode;
    private ArrayList<String> possibleActions;
    private ArrayList<String> actionList;
    private Queue<AStarNode> open;
    private ArrayList<State> closed;
    private HashSet<State> visited;
    private int nodesExpanded = 0;
    private int maxFrontier = 0;

    public AStar(Environment environment, State initialState)
    {
        this.initialState = initialState;
        this.environment = environment;
        //this.Frontier = new PriorityQueue<>(1, comp);
        //this.Frontier.add(new UCSNode(null, initialState, "", 0));
        this.possibleActions = new ArrayList<>(Arrays.asList(Actions.TURN_LEFT, Actions.TURN_RIGHT, Actions.GO, Actions.SUCK));
        this.actionList = new ArrayList<>();
        this.visited = new HashSet<>();
        //this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
    }

    public void search()
    {
        while(!open.isEmpty())
        {
            currNode = open.peek();

            if(!isGoal(currNode))
            {
                closed.add(currNode.getState());
            }
            else
            {

            }
        }
    }

    private boolean isGoal(AStarNode node)
    {
        if(node.getState().getDirtList().size() == 0
                && node.getState().getAgentLocation().getX() == environment.getHome().getX()
                && node.getState().getAgentLocation().getY() == environment.getHome().getY())
        {
            return true;
        }
        return false;
    }
}
