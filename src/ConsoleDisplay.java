import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by immoskyl on 31/12/19.
 */
public class ConsoleDisplay {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final int LeftLight_X = 9;
    public static final int LeftLight_Y = 8;
    public static final int RightLight_X = 15;
    public static final int RightLight_Y = 18;
    public static final int TopLight_X = 9;
    public static final int TopLight_Y = 18;
    public static final int BottomLight_X = 15;
    public static final int BottomLight_Y = 8;

    public static final int UpToDownWay_X = 13;
    public static final int DownToUpWay_X = 15;
    public static final int LeftToRight_Y = 14;
    public static final int RightToLeftWay_Y = 12;

    public static enum Light {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

    public static enum LightColor {
        GREEN,
        RED
    }

    private ArrayList<String> canvas = new ArrayList<>();


    public ArrayList<String> drawBaseCanvas () {

        ArrayList<String> canvas = new ArrayList<>();

        File file = new File("baseCanvas");
        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine())
                canvas.add(sc.nextLine());

        } catch (Exception e) {
            canvas.add("Exception: " + e.toString());
        }
        return canvas;
    }

    /**
     * take the current canvas and redraw one character
     * @param x x pos
     * @param y y pos
     * @param c single character as a string (to enable coloring)
     */
    public void redraw (int x, int y, String c) {
        String str = canvas.get(x);
        str = str.substring(0,y) + c + str.substring(y + 1);
        canvas.set(x, str);
    }

   public void drawLight (Light light, LightColor color) {

        String colorChar;
        if (color == LightColor.RED) {
            colorChar = ANSI_RED + 'X' + ANSI_RESET;
        } else {
            colorChar = ANSI_GREEN + 'O' + ANSI_GREEN;
        }

        switch (light) {
            case BOTTOM:
                redraw(BottomLight_X, BottomLight_Y, colorChar);
            case TOP:
                redraw(TopLight_X, TopLight_Y, colorChar);
            case LEFT:
                redraw(LeftLight_X, LeftLight_Y, colorChar);
            case RIGHT:
                redraw(RightLight_X, RightLight_Y, colorChar);
        }
   }

   public void printCanvas () {
        for (String s : canvas) {
            System.out.println(s);
        }
   }

   public void drawVehicule(Vehicle vehicle) {
        redraw(vehicle.getX(), vehicle.getY(), ANSI_BLUE + "#" + ANSI_RESET);
   }

    public static void main(String[] args) {
        ConsoleDisplay c = new ConsoleDisplay();

        System.out.println(c.canvas);
        c.drawBaseCanvas(); //IN FAUT TROUVER PK ON ARRIVE PAS A RECUPERER LES INFOS DANS CANVAS
        System.out.println(c.canvas);
        c.printCanvas();
    }

}
