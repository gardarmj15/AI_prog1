package Nodes;

import Helpers.State;
import Search.AStar;

public class AStarNode
{
    private int pathCost;
    private State state;
    private AStarNode parentNode;
    private int heuristic;

    public AStarNode(AStarNode parentNode, State state)
    {
        this.pathCost = parentNode.getPathCost();
        this.parentNode = parentNode;
        this.state = state;
        this.heuristic = parentNode.getHeuristic();
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
}
