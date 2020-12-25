package space.behaviour.libredaum.protocol;

public class Request {
    
    private static Packet newRequest(final byte[] header) {
        return new Packet(header, new byte[0]);
    }

    public static final Packet PROTOCOL_VERSION = newRequest(Header.V00);
    public static final Packet COCKPIT_VERSION = newRequest(Header.V70);
    public static final Packet DEVICE_TYPE = newRequest(Header.Y00);
    public static final Packet SAFETY_MODE = newRequest(Header.F00);
    public static final Packet TRAINING_TIME = newRequest(Header.T00);
    public static final Packet HEART_RATE_VALID = newRequest(Header.P00);
    public static final Packet HEART_RATE = newRequest(Header.P01);
    public static final Packet BUTTON_PRESS = newRequest(Header.U10);
    public static final Packet ERROR_STATE = newRequest(Header.Z00);
    public static final Packet SPEED_STATE = newRequest(Header.S00);
    public static final Packet SPEED = newRequest(Header.S01);
    public static final Packet SPEED_SET = newRequest(Header.S02);
    public static final Packet EMERGENCY_STOP_STATE = newRequest(Header.S03);
    public static final Packet SPEED_MAX = newRequest(Header.S04);
    public static final Packet ACCELERATION_DECELERATION_SAFE = newRequest(Header.A00);
    public static final Packet ACCELERATION_DECELERATION_UNSAFE = newRequest(Header.A01);
    public static final Packet DISTANCE = newRequest(Header.D00);
    public static final Packet INCLINE_AVAILABLE = newRequest(Header.E00);
    public static final Packet INCLINE = newRequest(Header.E01);
    public static final Packet COSREC_EMULATION = newRequest(Header.X00); // TODO: What is that?
    public static final Packet DATA_TRAINING = newRequest(Header.X70);
    public static final Packet GRADIENT_SET_AVAILABLE = newRequest(Header.I00);
    public static final Packet LOAD_ADJUSTABILITY = newRequest(Header.S20);
    public static final Packet RPM_GET = newRequest(Header.S21);
    public static final Packet RPM_SET = newRequest(Header.S22);
    public static final Packet POWER_SET = newRequest(Header.S23);
    public static final Packet DEVICE_LIMITS = newRequest(Header.L70);
    public static final Packet M70 = newRequest(Header.M70);
    public static final Packet GEAR = newRequest(Header.M71);
    public static final Packet BIKE_TYPE = newRequest(Header.M72);
}
