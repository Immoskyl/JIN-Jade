import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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


    public static final int max_X = 26;
    public static final int max_Y = 24;

    public static final int LeftLight_Y = 8;
    public static final int LeftLight_X = 7;
    public static final int RightLight_Y = 16;
    public static final int RightLight_X = 19;
    public static final int TopLight_Y = 8;
    public static final int TopLight_X = 19;
    public static final int BottomLight_Y = 16;
    public static final int BottomLight_X = 7;

    public static final int UpToDownWay_X = 12;
    public static final int DownToUpWay_X = 14;
    public static final int LeftToRightWay_Y = 13;
    public static final int RightToLeftWay_Y = 11;

    private ArrayList<String> canvas = new ArrayList<>();

    private ArrayList<ConsoleChange> changesToRedraw = new ArrayList<>();

    private class ConsoleChange implements Comparable {

        private String str;
        private int posX;
        private int posY;
        private String color;


        public ConsoleChange(String str, int posX, int posY) {
            this.str = str;
            this.posX = posX;
            this.posY = posY;
            this.color = "";
        }

        public ConsoleChange(String str, int posX, int posY, String color) {
            this.str = str;
            this.posX = posX;
            this.posY = posY;
            this.color = color;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public int getPosX() {
            return posX;
        }

        public void setPosX(int posX) {
            this.posX = posX;
        }

        public int getPosY() {
            return posY;
        }

        public void setPosY(int posY) {
            this.posY = posY;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return "ConsoleChange{" +
                    "str='" + str + '\'' +
                    ", posX=" + posX +
                    ", posY=" + posY +
                    ", color='" + color + '\'' +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
            return this.getPosX() < ((ConsoleChange) o).getPosX() ? -1 :
                   this.getPosX() == ((ConsoleChange) o).getPosX() ? 0 : 1;
        }
    }


    /**
     * Returns the base canvas at each frame.
     * /!\ This implementation cannot find the baseCanvas file so do not use it. /!\
     * @return A new base canvas as a List of String
     */
    public void drawBaseCanvas () {

        ArrayList<String> canvas = new ArrayList<>();

        File file = new File("baseCanvas");
        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine())
                canvas.add(sc.nextLine());

        } catch (Exception e) {
            canvas.add("Exception: " + e.toString());
        }
        this.canvas = canvas;
    }

    /**
     * Returns the base canvas at each frame.
     * This is a KISS implementation
     */
     public void drawBaseCanvas2 () {
         ArrayList<String> canvas = new ArrayList<>();

         canvas.add("          |     |           ");
         canvas.add("          |     |           ");
         canvas.add("          |     |           ");
         canvas.add("          |     |           ");
         canvas.add("          |     |           ");
         canvas.add("          |     |           ");
         canvas.add("          |     |  |        ");
         canvas.add("          |     |  |        ");
         canvas.add("     --X  |     |  O        ");
         canvas.add("__________|     |__________ ");
         canvas.add("                            ");
         canvas.add("                            ");
         canvas.add("                            ");
         canvas.add("                            ");
         canvas.add("__________       __________ ");
         canvas.add("          |     |           ");
         canvas.add("       O  |     |  X--      ");
         canvas.add("       |  |     |           ");
         canvas.add("       |  |     |           ");
         canvas.add("          |     |           ");
         canvas.add("          |     |           ");
         canvas.add("          |     |           ");
         canvas.add("          |     |           ");
         canvas.add("          |     |           ");
         canvas.add("          |     |           ");

         this.canvas = canvas;
     }


    /**
     * Replace the character at pos (x,y) with the character c
     * @param x x pos
     * @param y y pos
     * @param c single character as a string (we will maybe change that to a char)
     */
    public void addToRedraw(int x, int y, String c) {
        changesToRedraw.add(new ConsoleChange(c, x, y));
    }

    /**
     * Replace the character at pos (x,y) with the character c colored.
     * @param x x pos
     * @param y y pos
     * @param c single character as a string (we will maybe change that to a char)
     * @param color ANSI code of the color
     */
    public void addToRedraw(int x, int y, String c, String color) {
        changesToRedraw.add(new ConsoleChange(c, x, y, color));
    }


    /**
     * Take the current canvas and redraw all changes made
     *
     * This is possible by applying every change from the last (biggest x) to the first
     * (lowest x) on each line so the String indexation does not change for further changes
     *
     */
    public void redraw() {
        Collections.sort(changesToRedraw, Collections.reverseOrder()); //sorting on posX

        for (ConsoleChange change : changesToRedraw) {

            String modifiedLine = canvas.get(change.getPosY());
            String resetColor = change.color.equals("") ? "" : ANSI_RESET;

            modifiedLine = modifiedLine.substring(0,change.getPosX())
                         + change.getColor()
                         + change.getStr()
                         + resetColor
                         +  modifiedLine.substring(change.getPosX() + 1);


            canvas.set(change.getPosY(), modifiedLine);
        }

        changesToRedraw.clear();

    }

   public void drawLight (Light.LightWay light, Light.LightColor color) {

       String c;
       String colorChar;
       if (color == Light.LightColor.RED) {
           c = "X";
           colorChar = ANSI_RED;
       } else {
           c = "O";
           colorChar = ANSI_GREEN;
       }

       switch (light) {
           case BOTTOM:
               addToRedraw(BottomLight_X, BottomLight_Y, c, colorChar);
               break;
           case TOP:
               addToRedraw(TopLight_X, TopLight_Y, c, colorChar);
               break;
           case LEFT:
               addToRedraw(LeftLight_X, LeftLight_Y, c, colorChar);
               break;
           case RIGHT:
               addToRedraw(RightLight_X, RightLight_Y, c, colorChar);
               break;
       }
   }

   public void printCanvas () {
        redraw();

        for (String s : canvas) {
            System.out.println(s);
        }
   }


   public void drawVehicule(Vehicle vehicle) {
        addToRedraw(vehicle.getX(), vehicle.getY(), "#", ANSI_BLUE);
   }


    public static void main(String[] args) {
        //instatiate the console display, (that will initialize everything needed)
        ConsoleDisplay c = new ConsoleDisplay();

        //lets crate a bunch of vehicles driving in the junction
        Vehicle v1 = new Vehicle(Vehicle.Way.UpToDown); //we want to put one car going downwards
        Vehicle v2 = new Vehicle(Vehicle.Way.DownToUp);
        Vehicle v3 = new Vehicle(Vehicle.Way.LeftToRight);
        Vehicle v4 = new Vehicle(Vehicle.Way.RightToLeft);


        //at each frame, start refreshing the base canvas...
        c.drawBaseCanvas2();

        //...and print everything on it
        //lets set the lights' colors
        c.drawLight(Light.LightWay.BOTTOM, Light.LightColor.GREEN);
        c.drawLight(Light.LightWay.TOP, Light.LightColor.GREEN);
        c.drawLight(Light.LightWay.RIGHT, Light.LightColor.RED);
        c.drawLight(Light.LightWay.LEFT, Light.LightColor.RED);

        //lets draw the vehicles
        c.drawVehicule(v1);
        c.drawVehicule(v2);
        c.drawVehicule(v3);
        c.drawVehicule(v4);

        c.printCanvas();
    }

}
