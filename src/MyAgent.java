import Helpers.Actions;
import Helpers.Point2D;
import Helpers.State;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyAgent implements Agent
{
    private State state = new State();

    public void init(Collection<String> percepts) {
		/*
			Possible percepts are:
			- "(SIZE x y)" denoting the size of the environment, where x,y are integers
			- "(HOME x y)" with x,y >= 1 denoting the initial position of the robot
			- "(ORIENTATION o)" with o in {"NORTH", "SOUTH", "EAST", "WEST"} denoting the initial orientation of the robot
			- "(AT o x y)" with o being "DIRT" or "OBSTACLE" denoting the position of a dirt or an obstacle
			Moving north increases the y coordinate and moving east increases the x coordinate of the robots position.
			The robot is turned off initially, so don't forget to turn it on.
		*/
        Pattern perceptNamePattern = Pattern.compile("\\(\\s*([^\\s]+).*");
        for (String percept:percepts) {
            Matcher perceptNameMatcher = perceptNamePattern.matcher(percept);
            if (perceptNameMatcher.matches()) {
                String perceptName = perceptNameMatcher.group(1);
                if (perceptName.equals("HOME")) {
                    Matcher m = Pattern.compile("\\(\\s*HOME\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
                    if (m.matches()) {
                        System.out.println("robot is at " + m.group(1) + "," + m.group(2));
                        state.setAgentX(Integer.parseInt(m.group(1)));
                        state.setAgentY(Integer.parseInt(m.group(2)));
                    }
                }
                else if (perceptName.equals("SIZE")) {
                    Matcher m = Pattern.compile("\\(\\s*SIZE\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
                    if (m.matches()) {
                        System.out.println("size is " + m.group(1) + "," + m.group(2));
                        state.setSizeX(Integer.parseInt(m.group(1)));
                        state.setSizeY(Integer.parseInt(m.group(2)));
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
    }

    public String nextAction(Collection<String> percepts) {
        System.out.print("perceiving:");
        for(String percept:percepts) {
            System.out.print("'" + percept + "', ");
        }
        System.out.println("");
        return Actions.TURN_ON;
    }
}
