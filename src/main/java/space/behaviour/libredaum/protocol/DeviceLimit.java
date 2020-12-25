package space.behaviour.libredaum.protocol;

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

    public static DeviceLimit fromMessage(String msg) {
        String[] response = msg.split(new String(new byte[]{0x1d}));
        DeviceFunction func = null;
        switch (response[0]) {
            case "L":
                func = DeviceFunction.HEART_RATE;
                break;
            case "S":
                func = DeviceFunction.SPEED;
                break;
            case "W":
                func = DeviceFunction.POWER;
                break;
            case "E":
                func = DeviceFunction.GRADE;
                break;
            case "A":
                func = DeviceFunction.ACCELERATION;
        }
        return new DeviceLimit(func, Float.parseFloat(response[1]), Float.parseFloat(response[2]), Float.parseFloat(response[3]));
    }
}
