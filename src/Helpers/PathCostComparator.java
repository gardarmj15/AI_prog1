package Helpers;

import Nodes.UCSNode;

import java.util.Comparator;

public class PathCostComparator implements Comparator<UCSNode> {
    @Override
    public int compare(UCSNode Node1, UCSNode Node2)
    {
        if(Node1.getPathCost() < Node2.getPathCost())
        {
            return -1;
        }
        if(Node1.getPathCost() > Node2.getPathCost())
        {
            return 1;
        }
        return 0;
    }
}
