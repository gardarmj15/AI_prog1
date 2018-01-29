package Search;

import Helpers.*;
import Nodes.AStarNode;
import Nodes.BFSNode;
import Nodes.UCSNode;

import java.util.*;

public class AStar
{
    Comparator<AStarNode> comp = new PathCostWithHeuristicComparator();
    private State initialState;
    private Environment environment;
    private AStarNode currNode;
    private ArrayList<String> possibleActions;
    private ArrayList<String> actionList;
    private Queue<AStarNode> open;
    private ArrayList<State> closed;
    private HashSet<State> visited;
    private int nodesExpanded = 0;
    private int maxFrontier = 0;

    public AStar(State initialState, Environment environment)
    {
        this.initialState = initialState;
        this.environment = environment;
        this.open = new PriorityQueue<>(1, comp);
        this.open.add(new AStarNode(null, initialState, "", 0));
        this.possibleActions = new ArrayList<>(Arrays.asList(Actions.TURN_LEFT, Actions.TURN_RIGHT, Actions.GO, Actions.SUCK));
        this.actionList = new ArrayList<>();
        this.visited = new HashSet<>();
        this.closed = new ArrayList<>();
    }

    public ArrayList<String> startSearch() {
        search();
        return actionList;
    }

    public void search()
    {
        while(!open.isEmpty())
        {
            currNode = open.poll();
            if(isGoal(currNode))
            {
                getAllActionList(currNode);
                return;
            }
            else
            {
                closed.add(currNode.getState());
            }
            possibleActions(currNode.getState(), possibleActions);
            for(String act : possibleActions)
            {
                AStarNode newNode = createNewState(act, currNode);
                if(newNode != null)
                {
                    open.add(newNode);
                    if(open.size() > maxFrontier)
                        maxFrontier = open.size();
                }
            }

        }
    }

    private AStarNode createNewState(String action, AStarNode parentNode)
    {
        if(action.equals("SUCK"))
        {
            State newState = new State(parentNode.getState());
            newState.suckUpDirt();
            if(visited.add(newState)){
                nodesExpanded++;
                return new AStarNode(parentNode,new State(newState), "SUCK", parentNode.getPathCost() + 1);
            }
        }
        else if(action.equals("TURN_LEFT"))
        {
            State newState = new State(parentNode.getState());
            newState.changeDirection(action);
            if(visited.add(newState)){
                nodesExpanded++;
                return new AStarNode(parentNode, new State(newState), "TURN_LEFT", parentNode.getPathCost() + 1);
            }
        }
        else if(action.equals("TURN_RIGHT"))
        {
            State newState = new State(parentNode.getState());
            newState.changeDirection(action);
            if(visited.add(newState)) {
                nodesExpanded++;
                return new AStarNode(parentNode, new State(newState), "TURN_RIGHT", parentNode.getPathCost() + 1);
            }
        }
        else if(action.equals("GO"))
        {
            State newState = new State(parentNode.getState());
            newState.moveAgent();
            if(visited.add(newState)) {
                nodesExpanded++;
                return new AStarNode(parentNode, new State(newState), "GO", parentNode.getPathCost() + 1);
            }
        }
        return null;
    }

    public void getAllActionList(AStarNode goalNode)
    {
        while(goalNode.getParentNode() != null)
        {
            actionList.add(goalNode.getAction());
            goalNode = goalNode.getParentNode();
        }
        System.out.println("Nodes Expanded: " + nodesExpanded);
        System.out.println("Frontier size: " + maxFrontier);
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

    public boolean goingOutOfBounds(State state)
    {
        if(state.getAgentLocation().getX() == 1 && state.getOrientation().equals("WEST"))
            return true;
        if(state.getAgentLocation().getY() == 1 && state.getOrientation().equals("SOUTH"))
            return true;
        if(state.getAgentLocation().getX() == environment.getSizeX() && state.getOrientation().equals("EAST"))
            return true;
        if(state.getAgentLocation().getY() == environment.getSizeY() && state.getOrientation().equals("NORTH"))
            return true;
        return false;
    }
}
