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
    private HashSet<State> visited;
    private int nodesExpanded = 0;
    private int maxFrontier = 0;

    public DFS(State initialState, Environment environment)
    {
        this.initialState = initialState;
        this.environment = environment;
        this.Frontier = new Stack<>();
        this.Frontier.add(new DFSNode(null, initialState, ""));
        this.possibleActions = new ArrayList<>();
        nodesExpanded++;
        maxFrontier = Frontier.size();
        this.actionList = new ArrayList<>();
        this.allActions = new ArrayList<>(Arrays.asList(Actions.TURN_LEFT, Actions.TURN_RIGHT, Actions.GO, Actions.SUCK));
        this.visited = new HashSet<>();
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
                if(newNode != null)
                {
                    if(!isGoal(newNode))
                    {
                        Frontier.add(newNode);
                        if(Frontier.size() > maxFrontier)
                            maxFrontier = Frontier.size();
                    }
                    else
                    {
                        getAllActionList(newNode);
                        return;
                    }
                }
            }
        }
        System.out.println(currNode.getState().getDirtList().size());
    }

    private boolean isGoal(DFSNode node)
    {
        if(node.getState().getDirtList().size() == 0
                && node.getState().getAgentLocation().getX() == environment.getHome().getX()
                && node.getState().getAgentLocation().getY() == environment.getHome().getY())
        {
            return true;
        }
        return false;
    }

    private DFSNode createNewState(String action, DFSNode parentNode)
    {
        if(action.equals("SUCK"))
        {
            State newState = new State(parentNode.getState());
            newState.suckUpDirt();
            if(visited.add(newState)){
                nodesExpanded++;
                return new DFSNode(parentNode,new State(newState), "SUCK");
            }
        }
        else if(action.equals("TURN_LEFT"))
        {
            State newState = new State(parentNode.getState());
            newState.changeDirection(action);
            if(visited.add(newState)){
                nodesExpanded++;
                return new DFSNode(parentNode, new State(newState), "TURN_LEFT");
            }
        }
        else if(action.equals("TURN_RIGHT"))
        {
            State newState = new State(parentNode.getState());
            newState.changeDirection(action);
            if(visited.add(newState)) {
                nodesExpanded++;
                return new DFSNode(parentNode, new State(newState), "TURN_RIGHT");
            }
        }
        else if(action.equals("GO"))
        {
            State newState = new State(parentNode.getState());
            newState.moveAgent();
            if(visited.add(newState)) {
                nodesExpanded++;
                return new DFSNode(parentNode, new State(newState), "GO");
            }
        }
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
            actionList.add(goalNode.getActions());
            goalNode = goalNode.getParentNode();
        }
        System.out.println("Nodes Expanded: " + nodesExpanded);
        System.out.println("Frontier size: " + maxFrontier);
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
