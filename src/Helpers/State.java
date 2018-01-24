package Helpers;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class State
{
    private int agentX;
    private int agentY;
    private int sizeX;
    private int sizeY;
    private String orientation;
    private ArrayList<Point2D> dirtList;
    private ArrayList<Point2D> obstacleList;

    public State()
    {
        dirtList = new ArrayList<>();
        obstacleList = new ArrayList<>();
    }

    public void setAgentX(int agentX) {
        this.agentX = agentX;
        //System.out.println(this.agentX);
    }

    public void setAgentY(int agentY) {
        this.agentY = agentY;
        //System.out.println(this.agentY);
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
        //System.out.println(this.sizeX);
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
        //System.out.println(this.sizeY);
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
        //System.out.println(this.orientation);
    }

    public void addToDirtList(Point2D dirt) {
        dirtList.add(dirt);
        //System.out.println(dirt.getX() + " " + dirt.getY());
    }

    public void addToObstacleList(Point2D obstacle) {
        obstacleList.add(obstacle);
        //System.out.println(obstacle.getX() + " " + obstacle.getY());
    }
}
