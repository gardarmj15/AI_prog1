package Helpers;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class State
{
    private Point2D agentLocation;
    private String orientation;
    private ArrayList<Point2D> dirtList;

    public State()
    {
        dirtList = new ArrayList<>();
    }

    public void setAgentLocation(Point2D agentLocation) {
        this.agentLocation = agentLocation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void addToDirtList(Point2D dirt) {
        dirtList.add(dirt);
    }

    public ArrayList<Point2D> getDirtList() {
        return dirtList;
    }

    public Point2D getAgentLocation() {
        return agentLocation;
    }

    public String getOrientation() {
        return orientation;
    }

    public boolean containsDirt()
    {
        if(dirtList.contains(agentLocation))
        {
            return true;
        }
        return false;
    }

    public void suckUpDirt()
    {
        dirtList.remove(agentLocation);
    }

    public void changeDirection(String action)
    {
        if(action == "TURN_LEFT")
        {
            if(orientation == "NORTH")
                orientation = "WEST";
            if(orientation == "WEST")
                orientation = "SOUTH";
            if(orientation == "SOUTH")
                orientation = "EAST";
            if(orientation == "EAST")
                orientation = "NORTH";
        }
        if(action == "TURN_RIGHT")
        {
            if(orientation == "NORTH")
                orientation = "EAST";
            if(orientation == "EAST")
                orientation = "SOUTH";
            if(orientation == "SOUTH")
                orientation = "WEST";
            if(orientation == "WEST")
                orientation = "NORTH";
        }
    }

    public boolean isFacingObstacle(ArrayList<Point2D> obstacle)
    {
        for(Point2D o : obstacle)
        {
            if(o.getY() == agentLocation.getY() + 1 && orientation.equals("NORTH"))
            {
                return true;
            }
            if(o.getY() == agentLocation.getY() - 1 && orientation.equals("SOUTH"))
            {
                return true;
            }
            if(o.getX() == agentLocation.getX() + 1 && orientation.equals("EAST"))
            {
                return true;
            }
            if(o.getX() == agentLocation.getX() - 1 && orientation.equals("WEST"))
            {
                return true;
            }
        }
        return false;
    }

    public void moveAgent()
    {
        if(orientation == "NORTH")
            agentLocation.incrementY();
        if(orientation == "EAST")
            agentLocation.decrementX();
        if(orientation == "SOUTH")
            agentLocation.decrementX();
        if(orientation == "WEST")
            agentLocation.incrementX();
    }
}
