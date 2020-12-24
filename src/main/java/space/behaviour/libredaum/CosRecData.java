package space.behaviour.libredaum;

public class CosRecData {

    public final int TIME;
    public final int HEART_RATE;
    public final float SPEED;
    public final float INCLINE;
    public final int DISTANCE;

    public CosRecData(String data) {
        String[] cosrec = data.split(new String(new byte[]{0x1d}));

        TIME = Integer.parseInt(cosrec[0]);
        HEART_RATE = Integer.parseInt(cosrec[1]);
        SPEED = Float.parseFloat(cosrec[2]);
        INCLINE = Float.parseFloat(cosrec[3]);
        DISTANCE = Integer.parseInt(cosrec[4]);
    }

    public static CosRecData[] parseCosRecReponse(byte[] data) {
        String[] response = new String(data).split(" ");

        CosRecData[] cosrecs = new CosRecData[response.length];
        for (int i = 0; i < response.length; i++) {
            cosrecs[i] = new CosRecData(response[i]);
        }
        return cosrecs;
    }
}
