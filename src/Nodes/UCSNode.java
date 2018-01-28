package Nodes;

import Helpers.State;

public class UCSNode
{
    private UCSNode parentNode;
    private State state;
    private String actions;
    private int pathCost;

    public UCSNode(UCSNode parentNode, State state, String actions, int pathCost)
    {
        this.parentNode = parentNode;
        this.state = state;
        this.actions = actions;
        this.pathCost = pathCost;
    }

    public String getActions() {
        return actions;
    }

    public UCSNode getParentNode() {
        return parentNode;
    }

    public State getState() {
        return state;
    }
}
