/**
 * Created by immoskyl on 07/01/20.
 */
public class Light {

    private Vehicle.Way way;
    private int vehicleNb;
    private int timeSinceLastColorChange;
    private LightColor color;

    public static enum LightColor {
        GREEN,
        RED
    }

    public static enum LightWay {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

    public Light(Vehicle.Way way, LightColor color) {
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

    public LightColor getColor() {
        return color;
    }

    public void setColor(LightColor color) {
        this.color = color;
    }

    public Vehicle.Way getWay() {
        return way;
    }

}
