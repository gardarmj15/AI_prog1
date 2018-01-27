package Helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Environment {
    private State state = new State();
    private Point2D home;
    private int sizeX;
    private int sizeY;
    private ArrayList<Point2D> obstacleList;
    public Environment()
    {
        obstacleList = new ArrayList<>();
    }

    public State init(Collection<String> percepts)
    {
        Pattern perceptNamePattern = Pattern.compile("\\(\\s*([^\\s]+).*");
        for (String percept:percepts) {
            Matcher perceptNameMatcher = perceptNamePattern.matcher(percept);
            if (perceptNameMatcher.matches()) {
                String perceptName = perceptNameMatcher.group(1);
                if (perceptName.equals("HOME")) {
                    Matcher m = Pattern.compile("\\(\\s*HOME\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
                    if (m.matches()) {
                        System.out.println("robot is at " + m.group(1) + "," + m.group(2));
                        this.home = new Point2D(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                        state.setAgentLocation(home);
                    }
                }
                else if (perceptName.equals("SIZE")) {
                    Matcher m = Pattern.compile("\\(\\s*SIZE\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
                    if (m.matches()) {
                        System.out.println("size is " + m.group(1) + "," + m.group(2));
                        this.sizeX = Integer.parseInt(m.group(1));
                        this.sizeY = Integer.parseInt(m.group(2));
                    }
                }
                else if (perceptName.equals("ORIENTATION")) {
                    Matcher m = Pattern.compile("\\(\\s*ORIENTATION\\s+([A-Z]+)\\s*\\)").matcher(percept);
                    if (m.matches()) {
                        System.out.println("ORIENTATION is " + m.group(1));
                        state.setOrientation(m.group(1));
                    }
                }
                else if (perceptName.equals("AT")) {
                    Matcher m = Pattern.compile("\\(\\s*AT\\s+([A-Z]+)\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
                    if(m.matches())
                    {
                        if (m.group(1).matches("DIRT")) {
                            System.out.println("DIRT at " + m.group(2) + "," + m.group(3));
                            state.addToDirtList(new Point2D(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3))));
                        }
                        else if (m.group(1).matches("OBSTACLE")) {
                            System.out.println("OBSTACLE at " + m.group(2) + "," + m.group(3));
                            state.addToDirtList(new Point2D(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3))));
                        }
                    }
                }
                else {
                    System.out.println("other percept:" + percept);
                }
            } else {
                System.err.println("strange percept that does not match pattern: " + percept);
            }
        }
        return state;
    }

    public ArrayList<Point2D> getObstacleList() {
        return obstacleList;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public Point2D getHome() {
        return home;
    }
}
