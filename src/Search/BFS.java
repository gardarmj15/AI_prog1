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
    private Queue<BFSNode> Frontier;
    private BFSNode currNode;
    private ArrayList<String> possibleActions;
    private ArrayList<String> actionList;
    private HashSet<State> visited;
    private int nodesExpanded = 0;
    private int maxFrontier = 0;

    public BFS(State initialState, Environment environment)
    {
        this.initialState = initialState;
        this.environment = environment;
        this.Frontier = new LinkedList<>();
        this.Frontier.add(new BFSNode(null, initialState, ""));
        nodesExpanded++;
        maxFrontier = Frontier.size();
        this.possibleActions = new ArrayList<>(Arrays.asList(Actions.TURN_LEFT, Actions.TURN_RIGHT, Actions.GO, Actions.SUCK));
        this.actionList = new ArrayList<>();
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
            currNode = Frontier.poll();
            possibleActions(currNode.getState(), possibleActions);
            for(String act : possibleActions)
            {
                BFSNode newNode = createNewState(act, currNode);
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
    }

    private boolean isGoal(BFSNode node)
    {
        if(node.getState().getDirtList().size() == 0
                && node.getState().getAgentLocation().getX() == environment.getHome().getX()
                && node.getState().getAgentLocation().getY() == environment.getHome().getY())
        {
            return true;
        }
        return false;
    }

    private BFSNode createNewState(String action, BFSNode parentNode)
    {
        if(action.equals("SUCK"))
        {
            State newState = new State(parentNode.getState());
            newState.suckUpDirt();
            if(visited.add(newState)){
                nodesExpanded++;
                return new BFSNode(parentNode,new State(newState), "SUCK");
            }

        }
        else if(action.equals("TURN_LEFT"))
        {
            State newState = new State(parentNode.getState());
            newState.changeDirection(action);
            if(visited.add(newState)){
                nodesExpanded++;
                return new BFSNode(parentNode, new State(newState), "TURN_LEFT");
            }
        }
        else if(action.equals("TURN_RIGHT"))
        {
            State newState = new State(parentNode.getState());
            newState.changeDirection(action);
            if(visited.add(newState)) {
                nodesExpanded++;
                return new BFSNode(parentNode, new State(newState), "TURN_RIGHT");
            }
        }
        else if(action.equals("GO"))
        {
            State newState = new State(parentNode.getState());
            newState.moveAgent();
            if(visited.add(newState)) {
                nodesExpanded++;
                return new BFSNode(parentNode, new State(newState), "GO");
            }
        }
        return null;
    }

    private void possibleActions(State state, ArrayList<String> list)
    {
        if(state.containsDirt() && !list.contains("SUCK"))
            list.add("SUCK");
        else if(!state.containsDirt() && list.contains("SUCK"))
            list.remove("SUCK");
        if(!state.isFacingObstacle(environment.getObstacleList()) && !goingOutOfBounds(state) && !list.contains("GO"))
            list.add("GO");
        else if ((state.isFacingObstacle(environment.getObstacleList()) || goingOutOfBounds(state)) && list.contains("GO"))
            list.remove("GO");

    }

    public void getAllActionList(BFSNode goalNode)
    {
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
