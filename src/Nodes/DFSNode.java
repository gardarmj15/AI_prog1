package Nodes;

import Helpers.State;

public class DFSNode
{
    private DFSNode parentNode;
    private State state;
    private String actions;

    public DFSNode(DFSNode parentNode, State state, String actions)
    {
        this.parentNode = parentNode;
        this.state = state;
        this.actions = actions;
    }

    public String getActions() {
        return actions;
    }

    public DFSNode getParentNode() {
        return parentNode;
    }

    public State getState() {
        return state;
    }
}
