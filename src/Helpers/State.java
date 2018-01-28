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

    public State(State state)
    {
        this.agentLocation = state.agentLocation;
        this.orientation = state.orientation;
        this.dirtList = state.dirtList;
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
        if(action.equals("TURN_LEFT"))
        {
            if(orientation.equals("NORTH"))
                orientation = "WEST";
            else if(orientation.equals("WEST"))
                orientation = "SOUTH";
            else if(orientation.equals("SOUTH"))
                orientation = "EAST";
            else if(orientation.equals("EAST"))
                orientation = "NORTH";
        }
        if(action.equals("TURN_RIGHT"))
        {
            if(orientation.equals("NORTH"))
                orientation = "EAST";
            else if(orientation.equals("EAST"))
                orientation = "SOUTH";
            else if(orientation.equals("SOUTH"))
                orientation = "WEST";
            else if(orientation.equals("WEST"))
                orientation = "NORTH";
        }
    }

    public boolean isFacingObstacle(ArrayList<Point2D> obstacle)
    {
        //System.out.println(agentLocation.getX() + "," + agentLocation.getY());
        for(Point2D o : obstacle)
        {
            //System.out.println(orientation);
            //System.out.println(o.getX() + "," + o.getY());
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
        System.out.println("Should move");
        System.out.println(agentLocation.getX() + "," + agentLocation.getY());
        System.out.println(orientation);
        return false;
    }

    public void moveAgent()
    {
        if(orientation.equals("NORTH"))
            agentLocation.incrementY();
        if(orientation.equals("EAST"))
            agentLocation.incrementX();
        if(orientation.equals("SOUTH"))
            agentLocation.decrementY();
        if(orientation.equals("WEST"))
            agentLocation.decrementX();
    }
}
