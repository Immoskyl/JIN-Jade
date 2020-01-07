import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by immoskyl on 05/01/20.
 */
public class Manager {

    ConsoleDisplay c = new ConsoleDisplay();
    ArrayList<Vehicle> vehicleList = new ArrayList<>();
    ArrayList<Vehicle> vehicleToRemoveList = new ArrayList<>();

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


    /**
     * Method to execute at each 'frame' and that calls basically eveything
     * This is just a basic implementation to make the demo of the simulation tho
     */
    public void gameLoop() {
        while(true) {
            c.drawBaseCanvas2();

            createRandomlyNewVehicle();

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

