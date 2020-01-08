import java.util.ArrayList;
import java.util.Random;

/**
 * Created by immoskyl on 05/01/20.
 */
public class Manager {

    private int frame = 0;

    private ConsoleDisplay c = new ConsoleDisplay();
    private ArrayList<Vehicle> vehicleList = new ArrayList<>();
    private ArrayList<Vehicle> vehicleToRemoveList = new ArrayList<>();

    private Light bottomLight = new Light(Light.Way.BOTTOM, Light.Color.GREEN);
    private Light topLight = new Light(Light.Way.TOP, Light.Color.GREEN);
    private Light rightLight = new Light(Light.Way.RIGHT, Light.Color.RED);
    private Light leftLight = new Light(Light.Way.LEFT, Light.Color.RED);

    /**
     * Create randomly a new vehicle if the position is empty
     * This is just a basic implementation to make the demo of the simulation tho
     *
     * 1/8 chance to create a new vehicle driving from top to bot
     * 1/8 chance to create a new vehicle driving from bot to top
     * 1/8 chance to create a new vehicle driving from left to right
     * 1/8 chance to create a new vehicle driving from right to left
     */
    private void createRandomlyNewVehicle() {
        Random random = new Random();
        int randRes = random.nextInt(8) + 1;
        switch (randRes) {
            case 1:
                if (isPosEmpty(ConsoleDisplay.max_X, ConsoleDisplay.UpToDownWay_X))
                    vehicleList.add(new Vehicle(Vehicle.Way.UpToDown));
                break;
            case 2:
                if (isPosEmpty(0, ConsoleDisplay.DownToUpWay_X))
                    vehicleList.add(new Vehicle(Vehicle.Way.DownToUp));
                break;
            case 3:
                if (isPosEmpty(ConsoleDisplay.RightToLeftWay_Y, ConsoleDisplay.max_Y))
                    vehicleList.add(new Vehicle(Vehicle.Way.LeftToRight));
                break;
            case 4:
                if (isPosEmpty(ConsoleDisplay.RightToLeftWay_Y, 0))
                vehicleList.add(new Vehicle(Vehicle.Way.RightToLeft));
                break;
            default:
                break;
        }
    }

    public Vehicle getVehicleAtPos(int x, int y) {
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getX() == x && vehicle.getY() == y)
                return vehicle;
        }
        return null;
    }

    public boolean isPosEmpty(int x, int y) {
        return getVehicleAtPos(x, y) == null;
    }


    /**
     * Make the vehicles move forward if possible
     * If a vehicle moves out of the scope of the simulation, remove it
     */
    private void animateVehicles() {
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.isStopped()) {
                continue;
            }

            int x;
            int y;

            switch (vehicle.getWay()) {
                case UpToDown:
                    y = vehicle.getY();

                    //if the vehicle is too far, remove it from the simulation
                    if (y == ConsoleDisplay.max_Y)
                        vehicleToRemoveList.add(vehicle);

                    // if the vehicle has room to go forward, it does
                    if (isPosEmpty(ConsoleDisplay.UpToDownWay_X, y + 1))
                        vehicle.setY(y + 1);
                    break;

                case DownToUp:
                    y = vehicle.getY();

                    if (y - 1 == 0)
                        vehicleToRemoveList.add(vehicle);

                    if (isPosEmpty(ConsoleDisplay.DownToUpWay_X, y - 1))
                        vehicle.setY(y - 1);
                    break;

                case LeftToRight:
                    x = vehicle.getX();

                    if (x == ConsoleDisplay.max_X)
                        vehicleToRemoveList.add(vehicle);

                    if (isPosEmpty(x + 1, ConsoleDisplay.LeftToRightWay_Y))
                        vehicle.setX(x + 1);
                    break;

                case RightToLeft:
                    x = vehicle.getX();

                    if (x - 1 == 0)
                        vehicleToRemoveList.add(vehicle);

                    if (isPosEmpty(x - 1, ConsoleDisplay.RightToLeftWay_Y))
                        vehicle.setX(x - 1);
                    break;
            }
        }
        for (Vehicle vehicle : vehicleToRemoveList) {
            vehicleList.remove(vehicle);
        }
    }

    private int countVehicleNbOnWay(Vehicle.Way way) {
        int count = 0;
        switch (way) {

            case UpToDown:
                for (int i = 0; i != 10; i++) {
                    if (isPosEmpty(ConsoleDisplay.UpToDownWay_X, i))
                        count++;
                }
                break;

            case DownToUp:
                for (int i = 16; i != ConsoleDisplay.max_Y; i++) {
                    if (! isPosEmpty(ConsoleDisplay.DownToUpWay_X, i))
                        count++;
                }
               break;

            case LeftToRight:
                for (int i = 0; i != 10; i++) {
                    if (! isPosEmpty(i, ConsoleDisplay.LeftToRightWay_Y))
                        count++;
                }
               break;

            case RightToLeft:
                for (int i = 18; i != ConsoleDisplay.max_Y; i++) {
                    if (! isPosEmpty(i, ConsoleDisplay.RightToLeftWay_Y))
                        count++;
                }
               break;
       }
       return count;
    }


    private void updateLights() {
        notifyLights();
        computeLights();
        displayLights();
    }

    private void notifyLights() {
        bottomLight.setTimeSinceLastColorChange(frame);
        bottomLight.setVehicleNb(countVehicleNbOnWay(Vehicle.Way.DownToUp));
        topLight.setTimeSinceLastColorChange(frame);
        topLight.setVehicleNb(countVehicleNbOnWay(Vehicle.Way.UpToDown));
        leftLight.setTimeSinceLastColorChange(frame);
        leftLight.setVehicleNb(countVehicleNbOnWay(Vehicle.Way.LeftToRight));
        rightLight.setTimeSinceLastColorChange(frame);
        rightLight.setVehicleNb(countVehicleNbOnWay(Vehicle.Way.RightToLeft));
    }


    private void computeLights() {
        bottomLight.compute();
        topLight.compute();
        rightLight.compute();
        leftLight.compute();

    }

    private void displayLights() {

        Light.Color lightBottom = bottomLight.getColor();
        Light.Color lightTop = topLight.getColor();
        Light.Color lightLeft = leftLight.getColor();
        Light.Color lightRight = rightLight.getColor();

        c.drawLight(Light.Way.BOTTOM, lightBottom);
        c.drawLight(Light.Way.TOP, lightTop);
        c.drawLight(Light.Way.RIGHT, lightRight);
        c.drawLight(Light.Way.LEFT, lightLeft);

        Vehicle v1 = getVehicleAtPos(ConsoleDisplay.UpToDownWay_X,
                                     ConsoleDisplay.TopWayVehicleStop_Y);
        if (v1 != null)
            v1.setStopped(lightTop == Light.Color.RED);

        Vehicle v2 = getVehicleAtPos(ConsoleDisplay.DownToUpWay_X,
                                     ConsoleDisplay.BottomLight_Y);
        if (v2 != null)
            v2.setStopped(lightBottom == Light.Color.RED);

        Vehicle v3 = getVehicleAtPos(ConsoleDisplay.LeftWayVehicleStop_X,
                                     ConsoleDisplay.LeftToRightWay_Y);
        if (v3 != null)
            v3.setStopped(lightLeft == Light.Color.RED);

        Vehicle v4 = getVehicleAtPos(ConsoleDisplay.RightWayCehicleStop_X,
                                     ConsoleDisplay.RightToLeftWay_Y);
        if (v4 != null)
            v4.setStopped(lightRight == Light.Color.RED);
    }


    /**
     * Method to execute at each 'frame' and that calls basically eveything
     * This is just a basic implementation to make the demo of the simulation tho
     */
    public void gameLoop() {
        while(true) {
            frame++;

            // this is just an example that shows how a light change affects
            // all traffic at the crossing in the simulation
            if (frame % 20 == 0) {
                leftLight.switchColor();
                rightLight.switchColor();
                bottomLight.switchColor();
                topLight.switchColor();
            }

            c.drawBaseCanvas2();

            createRandomlyNewVehicle();

            updateLights();

            animateVehicles();

            displayVehicules();

            c.printCanvas();

            delay(500);
        }
    }

    /**
     * Do you really need an explanation for this?
     * @param miliseconds time for each frame
     */
    private void delay(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            return;
        }
    }

    /**
     * Draw the vehicles on the console
     */
    private void displayVehicules() {
        for (Vehicle vehicle : vehicleList) {
            c.drawVehicule(vehicle);
        }
    }

    public static void main(String[] args) {

        //quite simple to use it, isn't it?
        Manager manager = new Manager();
        manager.gameLoop();
    }
}

