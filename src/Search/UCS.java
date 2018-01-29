package Search;

import Helpers.Actions;
import Helpers.Environment;
import Helpers.PathCostComparator;
import Helpers.State;
import Nodes.UCSNode;

import java.util.*;

public class UCS
{
    Comparator<UCSNode> comp = new PathCostComparator();
    private State initialState;
    private Environment environment;
    private Queue<UCSNode> Frontier;
    private UCSNode currNode;
    private ArrayList<String> possibleActions;
    private ArrayList<String> actionList;
    private HashSet<State> visited;

    public UCS(State initialState, Environment environment)
    {
        this.initialState = initialState;
        this.environment = environment;
        this.Frontier = new PriorityQueue<>(1, comp);
        this.Frontier.add(new UCSNode(null, initialState, "", 0));
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
                UCSNode newNode = createNewState(act, currNode);
                if(newNode != null)
                {
                    if(!isGoal(newNode))
                    {
                        Frontier.add(newNode);
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

    private boolean isGoal(UCSNode node)
    {
        //if (node.getState().getDirtList().size() == 0 && node.getState().getAgentLocation() == environment.getHome())
        if(node.getState().getDirtList().size() == 0
                && node.getState().getAgentLocation().getX() == environment.getHome().getX()
                && node.getState().getAgentLocation().getY() == environment.getHome().getY())
        //if(node.getState().getAgentLocation().getX() == 5 && node.getState().getAgentLocation().getY() == 1 && node.getState().getDirtList().size() == 4)
        {
            return true;
        }
        return false;
    }

    private UCSNode createNewState(String action, UCSNode parentNode)
    {
        if(action.equals("SUCK"))
        {
            State newState = new State(parentNode.getState());
            newState.suckUpDirt();
            if(visited.add(newState)){
                return new UCSNode(parentNode,new State(newState), "SUCK", parentNode.getPathCost());
            }

        }
        else if(action.equals("TURN_LEFT"))
        {
            State newState = new State(parentNode.getState());
            newState.changeDirection(action);
            if(visited.add(newState)){
                return new UCSNode(parentNode, new State(newState), "TURN_LEFT", parentNode.getPathCost());
            }
        }
        else if(action.equals("TURN_RIGHT"))
        {
            State newState = new State(parentNode.getState());
            newState.changeDirection(action);
            if(visited.add(newState))
                return new UCSNode(parentNode, new State(newState), "TURN_RIGHT", parentNode.getPathCost());
        }
        else if(action.equals("GO"))
        {
            State newState = new State(parentNode.getState());
            newState.moveAgent();
            if(visited.add(newState))
                return new UCSNode(parentNode, new State(newState), "GO", parentNode.getPathCost());
        }
        return null;
    }

    private void possibleActions(State state, ArrayList<String> list)
    {
        if(state.containsDirt() && !list.contains("SUCK"))
        {
            list.add("SUCK");
        }
        else if(!state.containsDirt() && list.contains("SUCK")){
            list.remove("SUCK");
        }
        if(!state.isFacingObstacle(environment.getObstacleList()) && !goingOutOfBounds(state) && !list.contains("GO"))
        {
            list.add("GO");
        }
        else if ((state.isFacingObstacle(environment.getObstacleList()) || goingOutOfBounds(state)) && list.contains("GO")){
            list.remove("GO");
        }
    }

    public void getAllActionList(UCSNode goalNode)
    {
        while(goalNode.getParentNode() != null)
        {
            /*System.out.println();
            System.out.println(goalNode.getState().getOrientation());
            System.out.println(goalNode.getState().getAgentLocation().getX() + "," + goalNode.getState().getAgentLocation().getY());*/
            actionList.add(goalNode.getActions());
            //.out.println(goalNode.getActions());
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
