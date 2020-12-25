package space.behaviour.libredaum;

public class TrainingData {
    public final int TIME;
    public final int HEART_RATE;
    public final float SPEED;
    public final float INCLINE;
    public final int DISTANCE;
    public final float RPM;
    public final int POWER;
    public final float ENERGY_CONSUMPTION_PHYSICAL;
    public final float ENERGY_CONSUMPTION_REALISTIC;
    public final float TORQUE;
    public final byte GEAR;
    public final boolean DEVICE_ACTIVE;
    public final RpmState RPM_STATE;

    public enum RpmState {
        OK, LOW, HIGH, INVALID
    }

    public TrainingData(byte[] data) {

        String[] trainingData = new String(data).split(new String(new byte[]{0x1d}));

        TIME = Integer.parseInt(trainingData[0]);
        HEART_RATE = Integer.parseInt(trainingData[1]);
        SPEED = Float.parseFloat(trainingData[2]);
        INCLINE = Float.parseFloat(trainingData[3]);
        DISTANCE = Integer.parseInt(trainingData[4]);
        RPM = Float.parseFloat(trainingData[5]);
        POWER = Integer.parseInt(trainingData[6]);
        ENERGY_CONSUMPTION_PHYSICAL = Float.parseFloat(trainingData[7]);
        ENERGY_CONSUMPTION_REALISTIC = Float.parseFloat(trainingData[8]);
        TORQUE = Float.parseFloat(trainingData[9]);
        GEAR = Byte.parseByte(trainingData[10]);
        DEVICE_ACTIVE = Integer.parseInt(trainingData[11]) > 0;

        switch (Integer.parseInt(trainingData[12])) {
            case 0:
                RPM_STATE = RpmState.OK;
                break;
            case 1:
                RPM_STATE = RpmState.LOW;
                break;
            case 2:
                RPM_STATE = RpmState.HIGH;
                break;
            default:
                RPM_STATE = RpmState.INVALID;
        }
    }
}
