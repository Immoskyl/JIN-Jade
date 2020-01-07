/**
 * Created by immoskyl on 07/01/20.
 */
public class Light {

    private Way way;
    private int vehicleNb;
    private int timeSinceLastColorChange;
    private Color color;

    public static enum Color {
        GREEN,
        RED
    }

    public static enum Way {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

    public Light(Way way, Color color) {
        this.way = way;
        this.color = color;
        timeSinceLastColorChange = 0;
        vehicleNb = 0;
    }

    public int getVehicleNb() {
        return vehicleNb;
    }

    public void setVehicleNb(int vehicleNb) {
        this.vehicleNb = vehicleNb;
    }

    public int getTimeSinceLastColorChange() {
        return timeSinceLastColorChange;
    }

    public void setTimeSinceLastColorChange(int timeSinceLastColorChange) {
        this.timeSinceLastColorChange = timeSinceLastColorChange;
    }

    public Color getColor() {
        return color;
    }

    public Way getWay() {
        return way;
    }

    /**
     * This function is the startpath where the light wants to change color or not
     */
    public void compute() {}

}
