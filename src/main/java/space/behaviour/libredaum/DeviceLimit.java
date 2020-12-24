package space.behaviour.libredaum;

public class DeviceLimit {
    public enum DeviceFunction {
        HEART_RATE,
        SPEED,
        POWER,
        GRADE,
        ACCELERATION
    }

    public final DeviceFunction deviceFunction;
    public final float minimum;
    public final float maximum;
    public final float defaultValue;

    public DeviceLimit(DeviceFunction deviceFunction, float minimum, float maximum, float defaultValue) {
        this.deviceFunction = deviceFunction;
        this.minimum = minimum;
        this.maximum = maximum;
        this.defaultValue = defaultValue;
    }
}
