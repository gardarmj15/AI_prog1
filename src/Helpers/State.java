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
}
