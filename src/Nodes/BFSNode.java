package Nodes;

import Helpers.Actions;
import Helpers.State;

public class BFSNode
{
    private BFSNode parentNode;
    private State state;
    private String actions;

    public BFSNode(BFSNode parentNode, State state, String actions)
    {
        this.parentNode = parentNode;
        this.state = state;
        this.actions = actions;
    }

    public String getActions() {
        return actions;
    }

    public BFSNode getParentNode() {
        return parentNode;
    }

    public State getState() {
        return state;
    }
}


