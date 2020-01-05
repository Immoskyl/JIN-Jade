/**
 * Created by immoskyl on 31/12/19.
 */
public class Vehicle {

    private int X;
    private int Y;
    private boolean isStopped;
    private Way way;

    public enum Way {
        UpToDown,
        DownToUp,
        LeftToRight,
        RightToLeft
    }

    public Vehicle(Way way) {
        this.way = way;
        isStopped = false;

        switch (way) {
            case UpToDown:
                X = ConsoleDisplay.UpToDownWay_X;
                Y = 0;
                break;
            case DownToUp:
                X = ConsoleDisplay.DownToUpWay_X;
                Y = ConsoleDisplay.max_Y;
                break;
            case RightToLeft:
                X = ConsoleDisplay.max_X;
                Y = ConsoleDisplay.RightToLeftWay_X;
                break;
            case LeftToRight:
                X = 0;
                Y = ConsoleDisplay.LeftToRightWay_X;
                break;
        }
    }

    public Vehicle(int x, int y) throws Exception {
        X = x;
        Y = y;
        isStopped = false;

        if      (x == 13) {way = Way.LeftToRight;}
        else if (x == 11) {way = Way.RightToLeft;}
        else if (y == 12) {way = Way.UpToDown;}
        else if (y == 14) {way = Way.DownToUp;}
        else {
            System.out.println("WARNING IT MAY NOT WORK BECAUSE THE VEHICLE IS NOT ON A PROPER WAY");
        }
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    public Way getWay() {
        return way;
    }

    public void setWay(Way way) {
        this.way = way;
    }
}
