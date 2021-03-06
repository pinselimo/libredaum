package space.behaviour.libredaum.protocol;

import java.util.Arrays;

public class Response {
    private static final String TAG = "Daum.Response";

    public static class InvalidResponseException extends Exception {
        public InvalidResponseException(String message) {
            super(message);
        }
    }

    public static class ProtocolVersionResponse extends Response {
        public final String VERSION;

        ProtocolVersionResponse(byte[] data) {
            this.VERSION = new String(data);
        }
    }

    public static class CockpitVersionResponse extends Response {
        public final String VERSION;

        CockpitVersionResponse(byte[] data) {
            this.VERSION = new String(data);
        }
    }

    public static class DeviceTypeResponse extends Response {
        public static enum DeviceType {
            RUN, BIKE, LYPS
        }

        public final DeviceType DEVICE_TYPE;

        DeviceTypeResponse(byte[] data) throws InvalidResponseException {
            switch (new String(data)) {
                case "0":
                    this.DEVICE_TYPE = DeviceType.RUN;
                    break;
                case "1":
                    this.DEVICE_TYPE = DeviceType.BIKE;
                    break;
                case "7":
                    this.DEVICE_TYPE = DeviceType.LYPS;
                    break;
                default:
                    throw new InvalidResponseException("Unknown Device Type: " + new String(data));
            }
        }
    }

    public static class SafetyModeResponse extends Response {
        public final int TIMEOUT;

        public SafetyModeResponse(byte[] data) {
            TIMEOUT = Integer.parseInt(new String(data));
        }
    }

    public static class TrainingTimeResponse extends Response {
        public final int TRAINING_TIME;
        public TrainingTimeResponse(byte[] data) {
            String[] time = new String(data).split(":");
            int hours = Integer.parseInt(time[0]);
            int minutes = Integer.parseInt(time[1]);
            int seconds = Integer.parseInt(time[2]);

            TRAINING_TIME = seconds + minutes*60 + hours*3600;
        }
    }

    public static class HeartRateValidResponse extends Response {
        public final boolean VALID;

        public HeartRateValidResponse(byte[] data) throws InvalidResponseException {
            if (data.length != 1)
                throw new InvalidResponseException("Heart rate validity response malformed.");
            else
                VALID = data[0] == 0x31;
        }
    }

    public static class HeartRateResponse extends Response {
        public final int HEART_RATE;

        public HeartRateResponse(byte[] data) {
                HEART_RATE = Integer.parseInt(new String(data));
            }
    }

    public static class ButtonPressResponse extends Response {
        public final byte[] BUTTON_EVENT;

        public ButtonPressResponse(byte[] data) throws InvalidResponseException {
            if (ButtonEvent.valid(data))
                BUTTON_EVENT = data.clone();
            else
                throw new InvalidResponseException("Button press data malformed.");
        }
    }

    public static class ErrorStateResponse extends Response {
        public final int ERROR_CODE;

        public ErrorStateResponse(byte[] data) {
            ERROR_CODE = Integer.parseInt(new String(data));
        }
    }

    public static class SpeedStateResponse extends Response {
        public enum SpeedState {
            STOP, START, PAUSED
        }
        public final SpeedState STATE;

        public SpeedStateResponse(byte[] data) throws InvalidResponseException {
            switch (Integer.parseInt(new String(data))) {
                case 0:
                    STATE = SpeedState.STOP;
                    break;
                case 1:
                    STATE = SpeedState.START;
                    break;
                case 2:
                    STATE = SpeedState.PAUSED;
                    break;
                default:
                    throw new InvalidResponseException("Unknown speed state: " + new String(data));
            }
        }
    }

    public static class SpeedResponse extends Response {
        public final float SPEED;

        public SpeedResponse(byte[] data) {
            SPEED = Float.parseFloat(new String(data));
        }
    }

    public static class SpeedSetResponse extends SpeedResponse {
        public SpeedSetResponse(byte[] data) {
            super(data);
        }
    }

    public static class EmergencyStopState extends Response {
        public final boolean STOPPED;

        public EmergencyStopState(byte[] data) throws InvalidResponseException {
            switch (Integer.parseInt(new String(data))) {
                case 0:
                    STOPPED = false;
                    break;
                case 1:
                    STOPPED = true;
                    break;
                default:
                    throw new InvalidResponseException("Unknown emergency stop state: " + new String(data));
            }
        }
    }

    public static class TopSpeedResponse extends Response {
        public final float TOP_SPEED;

        public TopSpeedResponse(byte[] data) {
            TOP_SPEED = Float.parseFloat(new String(data));
        }
    }

    public static class AccelerationSafeResponse extends Response {
        public final Command.Acceleration ACCELERATION;

        public AccelerationSafeResponse(byte[] data) throws InvalidResponseException {
            switch (Integer.parseInt(new String(data))) {
                case 0:
                    ACCELERATION = Command.Acceleration.A0;
                    break;
                case 1:
                    ACCELERATION = Command.Acceleration.A131;
                    break;
                case 2:
                    ACCELERATION = Command.Acceleration.A66;
                    break;
                case 3:
                    ACCELERATION = Command.Acceleration.A33;
                    break;
                case 4:
                    ACCELERATION = Command.Acceleration.A16;
                    break;
                case 5:
                    ACCELERATION = Command.Acceleration.A8;
                    break;
                case 6:
                    ACCELERATION = Command.Acceleration.A5;
                    break;
                case 7:
                    ACCELERATION = Command.Acceleration.A3;
                    break;
                default:
                    throw new InvalidResponseException("Unknown command number: " + new String(data));
            }
        }
    }

    public static class AccelerationUnsafeResponse extends Response {
        public final int ACCELERATION;

        public AccelerationUnsafeResponse(byte[] data) {
            ACCELERATION = Integer.parseInt(new String(data));
        }
    }

    public static class DistanceResponse extends Response {
        public final int DISTANCE;

        public DistanceResponse(byte[] data) {
            DISTANCE = Integer.parseInt(new String(data));
        }
    }

    public static class InclineAvailableResponse extends Response {
        public final boolean INCLINE_AVAILABLE;

        public InclineAvailableResponse(byte[] data) throws InvalidResponseException {
            switch (Integer.parseInt(new String(data))) {
                case 0:
                    INCLINE_AVAILABLE = false;
                    break;
                case 1:
                    INCLINE_AVAILABLE = true;
                    break;
                default:
                    throw new InvalidResponseException("Unknown incline availability code: " + new String(data));
            }
        }
    }
    
    public static class InclineResponse extends Response {
        public final float INCLINE;

        public InclineResponse(byte[] data) {
            INCLINE = Float.parseFloat(new String(data));
        }
    }

    public static class CosRecEmulationResponse extends Response {
        public final CosRecData[] DATA;

        public CosRecEmulationResponse(byte[] data) {
            DATA = CosRecData.parseCosRecReponse(data);
        }
    }

    public static class TrainingDataResponse extends Response {
        public final TrainingData DATA;

        public TrainingDataResponse(byte[] data) {
            DATA = new TrainingData(data);
        }
    }

    public static class SetGradientAvailabilityReponse extends Response {
        public final boolean AVAILABLE;

        public SetGradientAvailabilityReponse(byte[] data) throws InvalidResponseException {
            switch (Integer.parseInt(new String(data))) {
                case 0:
                    AVAILABLE = false;
                    break;
                case 1:
                    AVAILABLE = true;
                    break;
                default:
                    throw new InvalidResponseException("Unknown gradient availability code: " + new String(data));
            }
        }
    }

    public static class LoadAdjustabilityReponse extends Response {
        public final boolean AVAILABLE;

        public LoadAdjustabilityReponse(byte[] data) throws InvalidResponseException {
            switch (Integer.parseInt(new String(data))) {
                case 0:
                    AVAILABLE = false;
                    break;
                case 1:
                    AVAILABLE = true;
                    break;
                default:
                    throw new InvalidResponseException("Unknown load adjustability code: " + new String(data));
            }
        }
    }

    public static class SetRPMResponse extends Response {
        public final float RPM;

        public SetRPMResponse(byte[] data) {
            RPM = Float.parseFloat(new String(data));
        }
    }

    public static class GetRPMResponse extends SetRPMResponse {
        public GetRPMResponse(byte[] data) {
            super(data);
        }
    }

    public static class SetPowerResponse extends Response {
        public final float POWER;

        public SetPowerResponse(byte[] data) {
            POWER = Float.parseFloat(new String(data));
        }
    }

    public static class DeviceLimitsResponse extends Response {
        public final DeviceLimit LIMIT;

        public DeviceLimitsResponse(byte[] data) throws InvalidResponseException {
            LIMIT = DeviceLimit.fromMessage(new String(data));
            if (LIMIT.deviceFunction == null) {
                throw new InvalidResponseException("Malformed device limits response: " + new String(data));
            }
        }
    }

    public static class M70Response extends Response {
        public final byte[] data;

        public M70Response(byte[] data) {
            this.data = data;
        }
    }

    public static class GearResponse extends Response {
        public final byte GEAR;

        public GearResponse(byte[] data) {
            GEAR = Byte.parseByte(new String(data));
        }
    }

    public static class BikeTypeResponse extends Response {
        public final Command.BikeType TYPE;

        public BikeTypeResponse(byte[] data) throws InvalidResponseException {
            switch (data[0]) {
                case 0x30:
                    TYPE = Command.BikeType.ALLROUND;
                    break;
                case 0x31:
                    TYPE = Command.BikeType.ROAD;
                    break;
                case 0x32:
                    TYPE = Command.BikeType.MOUNTAIN_BIKE;
                    break;
                default:
                    throw new InvalidResponseException("Unknown bike type code: " + new String(data));
            }
        }
    }

    public static Response fromByteArray(byte[] response) throws Response.InvalidResponseException {

        Response r = null;

        byte[] header = new byte[3];
        System.arraycopy(response, 1, header, 0, 3);
        int len_data = response.length - 4 - 3;
        byte[] data = new byte[len_data];
        System.arraycopy(response, 4, data, 0, len_data);
        byte[] checksum_provided = new byte[2];
        System.arraycopy(response, 4 + len_data, checksum_provided, 0, 2);

        if (!Arrays.equals(checksum_provided, Packet.checksum(header, data))) {
            throw new Response.InvalidResponseException("Checksum:  " + Arrays.toString(checksum_provided)
            + " does not match expected: " + Arrays.toString(Packet.checksum(header, data)));
        }

        switch (new String(header)) {
            case "V00":
                r = new ProtocolVersionResponse(data);
                break;
            case "V70":
                r = new CockpitVersionResponse(data);
                break;
            case "Y00":
                r = new DeviceTypeResponse(data);
                break;
            case "F00":
                r = new SafetyModeResponse(data);
                break;
            case "T00":
            case "T10":
                r = new TrainingTimeResponse(data);
                break;
            case "P00":
                r = new HeartRateValidResponse(data);
                break;
            case "P01":
                r = new HeartRateResponse(data);
                break;
            case "U10":
                r = new ButtonPressResponse(data);
                break;
            case "Z00":
                r = new ErrorStateResponse(data);
                break;
            case "S00":
                r = new SpeedStateResponse(data);
                break;
            case "S01":
                r = new SpeedResponse(data);
                break;
            case "S02":
                r = new SpeedSetResponse(data);
                break;
            case "S03":
                r = new EmergencyStopState(data);
                break;
            case "S04":
                r = new TopSpeedResponse(data);
                break;
            case "A00":
                r = new AccelerationSafeResponse(data);
                break;
            case "A01":
                r = new AccelerationUnsafeResponse(data);
                break;
            case "D00":
                r = new DistanceResponse(data);
                break;
            case "E00":
                r = new InclineAvailableResponse(data);
                break;
            case "E01":
                r = new InclineResponse(data);
                break;
            case "X00":
                r = new CosRecEmulationResponse(data);
                break;
            case "X70":
                r = new TrainingDataResponse(data);
                break;
            case "I00":
                r = new SetGradientAvailabilityReponse(data);
                break;
            case "S20":
                r = new LoadAdjustabilityReponse(data);
                break;
            case "S21":
                r = new GetRPMResponse(data);
                break;
            case "S22":
                r = new SetRPMResponse(data);
                break;
            case "S23":
                r = new SetPowerResponse(data);
                break;
            case "L70":
                r = new DeviceLimitsResponse(data);
                break;
            case "M70":
                r = new M70Response(data);
                break;
            case "M71":
                r = new GearResponse(data);
                break;
            case "M72":
                r = new BikeTypeResponse(data);
                break;
            default:
                throw new InvalidResponseException("Unknown header: " + new String(header));
        }
        return r;
    }
}
