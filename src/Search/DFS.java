package Search;

import Helpers.Actions;
import Helpers.Environment;
import Helpers.State;
import Nodes.BFSNode;
import Nodes.DFSNode;

import java.util.*;

public class DFS
{
    private State initialState;
    private Environment environment;
    private Stack<DFSNode> Frontier;
    private DFSNode currNode;
    private ArrayList<String> allActions;
    private ArrayList<String> possibleActions;
    private ArrayList<String> actionList;

    public DFS(State initialState, Environment environment)
    {
        this.initialState = initialState;
        this.environment = environment;
        this.Frontier = new Stack<>();
        this.Frontier.add(new DFSNode(null, initialState, ""));
        this.possibleActions = new ArrayList<>();
        this.actionList = new ArrayList<>();
        this.allActions = new ArrayList<>(Arrays.asList(Actions.TURN_LEFT, Actions.TURN_RIGHT, Actions.GO, Actions.SUCK));
    }

    public ArrayList<String> startSearch() {
        search();
        return actionList;
    }

    private void search()
    {
        currNode = Frontier.peek();
        isGoal(currNode);
        while(Frontier.size() > 0)
        {
            currNode = Frontier.pop();
            possibleActions = possibleActions(currNode.getState());
            for(String act : possibleActions)
            {
                DFSNode newNode = createNewState(act, currNode);
                if(!isGoal(newNode))
                {
                    Frontier.push(newNode);
                }
                else
                {
                    getAllActionList(newNode);
                    return;
                }
            }
        }
    }

    private boolean isGoal(DFSNode node)
    {
        //if (node.getState().getDirtList().size() == 0 && node.getState().getAgentLocation() == environment.getHome())
        //if(node.getState().getDirtList().size() == 4)
        if(node.getState().getAgentLocation().getX() == 5 && node.getState().getAgentLocation().getY() == 1 && node.getState().getDirtList().size() == 4)
        {
            return true;
        }
        return false;
    }

    private DFSNode createNewState(String action, DFSNode parentNode)
    {
        State newState = new State(parentNode.getState());
        if(action.equals("SUCK"))
        {
            newState.suckUpDirt();
            return new DFSNode(parentNode, newState, "SUCK");
        }
        else if(action.equals("TURN_LEFT"))
        {
            newState.changeDirection(action);
            return new DFSNode(parentNode, newState, "TURN_LEFT");
        }
        else if(action.equals("TURN_RIGHT"))
        {
            newState.changeDirection(action);
            return new DFSNode(parentNode, newState, "TURN_RIGHT");
        }
        else if(action.equals("GO"))
        {
            newState.moveAgent();
            return new DFSNode(parentNode, newState, "GO");
        }
        else
            System.out.println("ADD go node");
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
        if(!state.isFacingObstacle(environment.getObstacleList()) && !goingOutOfBounds(state))
        {
            list.add("GO");
        }
        return list;
    }

    public void getAllActionList(DFSNode goalNode)
    {
        System.out.println("WE WON");
        while(goalNode.getParentNode() != null)
        {
            System.out.println();
            System.out.println(goalNode.getState().getOrientation());
            System.out.println(goalNode.getState().getAgentLocation().getX() + "," + goalNode.getState().getAgentLocation().getY());
            actionList.add(goalNode.getActions());
            System.out.println(goalNode.getActions());
            goalNode = goalNode.getParentNode();
        }
    }

    public boolean goingOutOfBounds(State state)
    {
        if(state.getAgentLocation().getX() == 1 && state.getOrientation().equals("WEST"))
        {
            return true;
        }
        if(state.getAgentLocation().getY() == 1 && state.getOrientation().equals("SOUTH"))
        {
            return true;
        }
        if(state.getAgentLocation().getX() == environment.getSizeX() && state.getOrientation().equals("EAST"))
        {
            return true;
        }
        if(state.getAgentLocation().getY() == environment.getSizeY() && state.getOrientation().equals("NORTH"))
        {
            return true;
        }
        return false;
    }
}
