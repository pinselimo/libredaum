package space.behaviour.libredaum.protocol;

import java.nio.charset.StandardCharsets;

public class ButtonEvent {

    public static final byte[] PRESS_UP = buttonAction("UP");
    public static final byte[] PRESS_DOWN = buttonAction("DP");
    public static final byte[] PRESS_START_ENTER = buttonAction("EP");
    public static final byte[] PRESS_STOP = buttonAction("SP");
    public static final byte[] PRESS_EMERGENCY_STOP = buttonAction("FP");
    public static final byte[] PRESS_PLUS = buttonAction("+P");
    public static final byte[] PRESS_MINUS = buttonAction("-P");

    public static final byte[] RELEASE_UP = buttonAction("UR");
    public static final byte[] RELEASE_DOWN = buttonAction("DR");
    public static final byte[] RELEASE_START_ENTER = buttonAction("ER");
    public static final byte[] RELEASE_STOP = buttonAction("SR");
    public static final byte[] RELEASE_EMERGENCY_STOP = buttonAction("FR");
    public static final byte[] RELEASE_PLUS = buttonAction("+R");
    public static final byte[] RELEASE_MINUS = buttonAction("-R");

    private static byte[] buttonAction(String code) {
        return code.getBytes(StandardCharsets.US_ASCII);
    }

    public static boolean valid(byte[] event) {
        switch (new String(event)) {
            case "UP":
            case "DP":
            case "EP":
            case "SP":
            case "FP":
            case "+P":
            case "-P":

            case "UR":
            case "DR":
            case "ER":
            case "SR":
            case "FR":
            case "+R":
            case "-R":

                return true;

            default:
                return false;
        }
    }
}
