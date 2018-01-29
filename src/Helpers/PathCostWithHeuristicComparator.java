package Helpers;

import Nodes.AStarNode;
import Nodes.UCSNode;
import Search.AStar;

import java.util.Comparator;

public class PathCostWithHeuristicComparator implements Comparator<AStarNode> {
    @Override
    public int compare(AStarNode Node1, AStarNode Node2)
    {
        if(Node1.getPathCost() + Node1.getHeuristic() < Node2.getPathCost() + Node2.getHeuristic())
        {
            return -1;
        }
        if(Node1.getPathCost() + Node1.getHeuristic() > Node2.getPathCost() + Node2.getHeuristic())
        {
            return 1;
        }
        return 0;
    }
}
