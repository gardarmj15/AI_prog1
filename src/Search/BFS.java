package Search;

import Helpers.Actions;
import Helpers.Environment;
import Helpers.Point2D;
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
    private ArrayList<String> actionList;

    public BFS(State initialState, Environment environment)
    {
        this.initialState = initialState;
        this.environment = environment;
        this.Frontier = new LinkedList<>();
        this.Frontier.add(new BFSNode(null, initialState, ""));
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
            //System.out.println("Size: " + Frontier.size());
            if(Frontier.size() > 20)
            {
                return;
            }
            currNode = Frontier.poll();
            possibleActions = possibleActions(currNode.getState());
            System.out.println("Size: " + possibleActions.size());
            int i = 0;
            for(String act : possibleActions)
            {
                BFSNode newNode = createNewState(act);
                if(!isGoal(newNode))
                {
                    Frontier.add(newNode);
                    /*System.out.println();
                    System.out.println(newNode.getActions());
                    System.out.println(newNode.getState().getOrientation());
                    System.out.println(newNode.getState().getAgentLocation().getX() + "," + newNode.getState().getAgentLocation().getY());
                    System.out.println("Node end");*/
                }
                else
                {
                    getAllActionList(newNode);
                    return;
                }
            }
        }
    }

    private boolean isGoal(BFSNode node)
    {
        //if (node.getState().getDirtList().size() == 0 && node.getState().getAgentLocation() == environment.getHome())
        if(node.getState().getDirtList().size() == 4)
        {
            return true;
        }
        return false;
    }

    private BFSNode createNewState(String action)
    {
        State newState = new State(currNode.getState());
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
            System.out.println(newState.getAgentLocation().getX() + "," + newState.getAgentLocation().getY());
            //System.out.println(newState.getAgentLocation().getX() + "," + newState.getAgentLocation().getY());
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
        if(!state.isFacingObstacle(environment.getObstacleList()) && !goingOutOfBounds(state))
        {
            list.add("GO");
        }
        return list;
    }

    public void getAllActionList(BFSNode goalNode)
    {
        System.out.println("WE WON");
        while(goalNode.getParentNode() != null)
        {
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
