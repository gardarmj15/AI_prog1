package Nodes;

import Helpers.State;
import Search.AStar;

public class AStarNode
{
    private int pathCost;
    private State state;
    private AStarNode parentNode;
    private int heuristic;
    private String action;

    public AStarNode(AStarNode parentNode, State state, String action)
    {
        this.pathCost = 0;
        this.parentNode = parentNode;
        this.state = state;
        this.heuristic = 0;
        this.action = action;
    }

    public AStarNode(AStarNode parentNode, State state, String action, int pathCost)
    {
        this.pathCost = pathCost;
        this.parentNode = parentNode;
        this.state = state;
        this.heuristic = state.findHeuristic();
        this.action = action;
    }

    public int getPathCost() {
        return pathCost;
    }

    public State getState() {
        return state;
    }

    public AStarNode getParentNode() {
        return parentNode;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public String getAction() {
        return action;
    }
}
