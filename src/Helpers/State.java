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
        this.agentLocation = new Point2D(state.getAgentLocation().getX(), state.getAgentLocation().getY());
        this.orientation = state.orientation;
        this.dirtList = new ArrayList<>();
        this.dirtList.addAll(state.dirtList);
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
        for(Point2D d : dirtList)
        {
            if(d.getX() == agentLocation.getX() && d.getY() == agentLocation.getY())
            {
                return true;
            }
        }
        return false;
    }

    public void suckUpDirt()
    {
        for(Point2D d : dirtList)
        {
            if(d.getX() == agentLocation.getX() && d.getY() == agentLocation.getY())
            {
                dirtList.remove(d);
                return;
            }
        }
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
        for(Point2D o : obstacle)
        {
            if(o.getY() == agentLocation.getY() + 1 && orientation.equals("NORTH") && o.getX() == agentLocation.getX())
            {
                return true;
            }
            if(o.getY() == agentLocation.getY() - 1 && orientation.equals("SOUTH") && o.getX() == agentLocation.getX())
            {
                return true;
            }
            if(o.getX() == agentLocation.getX() + 1 && orientation.equals("EAST") && o.getY() == agentLocation.getY())
            {
                return true;
            }
            if(o.getX() == agentLocation.getX() - 1 && orientation.equals("WEST") && o.getY() == agentLocation.getY())
            {
                return true;
            }
        }
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

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof State)) {
            return false;
        }

        State state = (State) o;

        return state.orientation.equals(orientation) &&
                state.agentLocation.getY() == this.agentLocation.getY() &&
                state.agentLocation.getX() == this.agentLocation.getX() &&
                state.getDirtList().equals(this.dirtList);
    }

    @Override
    public int hashCode(){
        int result = 18;
        int c = 0;

        c += agentLocation.getX();
        c += agentLocation.getY();
        c += orientation.hashCode();
        for (Point2D p: dirtList) {
            c += p.getY();
            c += p.getX();
        }

        result = 37 * result + c;
        return result;
    }
}
