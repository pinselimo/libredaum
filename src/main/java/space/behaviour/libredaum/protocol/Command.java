package space.behaviour.libredaum.protocol;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static space.behaviour.libredaum.protocol.Packet.SEP;

public class Command {

        private static final NumberFormat FLOAT_4_2 = new DecimalFormat("#0000.00");
        private static final NumberFormat FLOAT_4_1 = new DecimalFormat("#0000.0");
        private static final NumberFormat FLOAT_5_2 = new DecimalFormat("#00000.00");

        public static final Packet PRESS_UP = buttonAction(ButtonEvent.PRESS_UP);
        public static final Packet PRESS_DOWN = buttonAction(ButtonEvent.PRESS_DOWN);
        public static final Packet PRESS_START_ENTER = buttonAction(ButtonEvent.PRESS_START_ENTER);
        public static final Packet PRESS_STOP = buttonAction(ButtonEvent.PRESS_STOP);
        public static final Packet PRESS_EMERGENCY_STOP = buttonAction(ButtonEvent.PRESS_EMERGENCY_STOP);
        public static final Packet PRESS_PLUS = buttonAction(ButtonEvent.PRESS_PLUS);
        public static final Packet PRESS_MINUS = buttonAction(ButtonEvent.PRESS_MINUS);

        public static final Packet RELEASE_UP = buttonAction(ButtonEvent.RELEASE_UP);
        public static final Packet RELEASE_DOWN = buttonAction(ButtonEvent.RELEASE_DOWN);
        public static final Packet RELEASE_START_ENTER = buttonAction(ButtonEvent.RELEASE_START_ENTER);
        public static final Packet RELEASE_STOP = buttonAction(ButtonEvent.RELEASE_STOP);
        public static final Packet RELEASE_EMERGENCY_STOP = buttonAction(ButtonEvent.RELEASE_EMERGENCY_STOP);
        public static final Packet RELEASE_PLUS = buttonAction(ButtonEvent.RELEASE_PLUS);
        public static final Packet RELEASE_MINUS = buttonAction(ButtonEvent.RELEASE_MINUS);

        private static Packet buttonAction(byte[] button) {
                return new Packet(Header.U10, button);
        }

        public static Packet setSafetyMode(byte timeout) {
                byte[] to = Byte.toString(timeout).getBytes(StandardCharsets.US_ASCII);
                return new Packet(Header.F00, to);
        }

        public static Packet setSpeed(double speed) {
                byte[] new_speed = FLOAT_4_2.format(speed).getBytes(StandardCharsets.US_ASCII);
                return new Packet(Header.S02, new_speed);
        }

        public enum Acceleration {
                A0, A131, A66, A33, A16, A8, A5, A3
        }

        public static Packet setAccelerationSafe(Acceleration accel) {
                byte[] accel_data = new byte[1];
                switch (accel) {
                        case A0:
                                accel_data[0] = 0x30;
                                break;
                        case A131:
                                accel_data[0] = 0x31;
                                break;
                        case A66:
                                accel_data[0] = 0x32;
                                break;
                        case A33:
                                accel_data[0] = 0x33;
                                break;
                        case A16:
                                accel_data[0] = 0x34;
                                break;
                        case A8:
                                accel_data[0] = 0x35;
                                break;
                        case A5:
                                accel_data[0] = 0x36;
                                break;
                        case A3:
                                accel_data[0] = 0x37;
                                break;
                }
                return new Packet(Header.A00, accel_data);
        }

        public static Packet setAccelerationUnsafe(int acceleration) {
                byte[] accel = Integer.toString(acceleration).getBytes(StandardCharsets.US_ASCII);
                return new Packet(Header.A01, accel);
        }

        public static Packet setLoadAdjustability(boolean state) {
                byte[] load_adj_state = new byte[1];
                if (state) {
                        load_adj_state[0] = 0x31;
                } else {
                        load_adj_state[0] = 0x30;
                }
                return new Packet(Header.S20, load_adj_state);
        }

        public static Packet setRPM(float rpm) {
                byte[] new_rpm = FLOAT_4_1.format(rpm).getBytes(StandardCharsets.US_ASCII);
                return new Packet(Header.S22, new_rpm);
        }

        public static Packet setPower(double power) {
                byte[] new_pwr = FLOAT_5_2.format(power).getBytes(StandardCharsets.US_ASCII);
                return new Packet(Header.S23, new_pwr);
        }

        public static Packet setLimit(DeviceLimit limit) {
                String msg = "";

                switch (limit.deviceFunction) {
                        case HEART_RATE:
                                msg += "L";
                                break;
                        case SPEED:
                                msg += "S";
                                break;
                        case POWER:
                                msg += "W";
                                break;
                        case GRADE:
                                msg += "E";
                                break;
                        case ACCELERATION:
                                msg += "A";
                                break;
                }
                msg += SEP;
                msg += FLOAT_5_2.format(limit.minimum) + SEP;
                msg += FLOAT_5_2.format(limit.maximum) + SEP;
                msg += FLOAT_5_2.format(limit.defaultValue);

                return new Packet(Header.L70, msg.getBytes(StandardCharsets.US_ASCII));
        }

        public static Packet M70() {
                throw new NotImplementedException();
        }

        public static Packet setGear(byte gear) {
                byte[] new_gear = Byte.toString(gear).getBytes(StandardCharsets.US_ASCII);
                return new Packet(Header.M71, new_gear);
        }

        public enum BikeType {
                ALLROUND, ROAD, MOUNTAIN_BIKE
        }

        public static Packet setBikeType(BikeType bt) {
                byte[] new_bikeType = new byte[1];
                switch (bt) {
                        case ALLROUND:
                                new_bikeType[0] = 0x30;
                                break;
                        case ROAD:
                                new_bikeType[0] = 0x31;
                                break;
                        case MOUNTAIN_BIKE:
                                new_bikeType[0] = 0x32;
                                break;
                }
                return new Packet(Header.M72, new_bikeType);
        }
}