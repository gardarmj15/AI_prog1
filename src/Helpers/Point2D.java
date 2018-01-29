package Helpers;

public class Point2D
{
    private int x;
    private int y;

    public Point2D(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void incrementX()
    {
        x++;
    }

    public void incrementY()
    {
        y++;
    }

    public void decrementX()
    {
        x--;
    }

    public void decrementY()
    {
        y--;
    }

    public double manhattanDistance(Point2D A1)
    {
        int dx = Math.abs(x - A1.x);
        int dy = Math.abs(y - A1.y);
        return dx+dy;
    }
}
