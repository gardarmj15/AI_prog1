package Search;

import Helpers.Actions;
import Helpers.Environment;
import Helpers.State;
import Nodes.BFSNode;

import java.util.*;

public class BFS
{
    private State initialState;
    private Environment environment;
    private Actions actions;
    private Queue<BFSNode> Frontier;
    private BFSNode currNode;
    private ArrayList<String> allActions;
    private ArrayList<String> possibleActions;

    public BFS(State initialState, Environment environment)
    {
        this.initialState = initialState;
        this.environment = environment;
        this.Frontier = new LinkedList<>();
        this.Frontier.add(new BFSNode(null, initialState, ""));
        this.possibleActions = new ArrayList<>();
        this.allActions = new ArrayList<>(Arrays.asList(Actions.TURN_LEFT, Actions.TURN_RIGHT, Actions.GO, Actions.SUCK));
        search();
    }

    private void search()
    {
        currNode = Frontier.peek();
        isGoal();
        while(Frontier.size() > 0)
        {
            currNode = Frontier.poll();
            possibleActions = possibleActions(currNode.getState());
            for(String act : possibleActions)
            {
                BFSNode newNode = createNewState(act);
                if(newNode.getState().getAgentLocation() == environment.getHome() || newNode.getState().getDirtList().size() == 0)
                {
                    Frontier.add(newNode);
                }
            }
        }
    }

    private boolean isGoal()
    {
        if (currNode.getState().getDirtList().size() == 0 && currNode.getState().getAgentLocation() == environment.getHome())
        {
            return true;
        }
        return false;
    }

    private BFSNode createNewState(String action)
    {
        State newState = currNode.getState();
        if(action.equals("SUCK"))
        {
            newState.suckUpDirt();
            //System.out.println("SUCKING DIRT");
            return new BFSNode(currNode, newState, "SUCK");
        }
        else if(action.equals("TURN_LEFT"))
        {
            newState.changeDirection(action);
            //System.out.println("TURNING LEFT");
            return new BFSNode(currNode, newState, "TURN_LEFT");
        }
        else if(action.equals("TURN_RIGHT"))
        {
            newState.changeDirection(action);
            //System.out.println("TURNING RIGHT");
            return new BFSNode(currNode, newState, "TURN_RIGHT");
        }
        else if(action.equals("GO"))
        {
            newState.moveAgent();
            System.out.println("GOING FORWARD");
            return new BFSNode(currNode, newState, "GO");
        }
        else
            return null;
    }

    private ArrayList<String> possibleActions(State state)
    {
        ArrayList<String> list = new ArrayList<>();
        list.add("TURN_LEFT");
        list.add("TURN_RIGHT");
        if(state.containsDirt())
        {
            list.add("SUCK");
        }
        if(!state.isFacingObstacle(environment.getObstacleList()))
        {
            list.add("GO");
        }
        return list;
    }
}
