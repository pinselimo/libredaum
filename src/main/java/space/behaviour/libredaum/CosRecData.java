package space.behaviour.libredaum;

public class CosRecData {

    public final int TIME;
    public final int HEART_RATE;
    public final float SPEED;
    public final float INCLINE;
    public final int DISTANCE;

    public CosRecData(String data) {
        String[] cosrec = data.split("GS");

        TIME = Integer.parseInt(cosrec[0]);
        HEART_RATE = Integer.parseInt(cosrec[1]);
        SPEED = Float.parseFloat(cosrec[2]);
        INCLINE = Float.parseFloat(cosrec[3]);
        DISTANCE = Integer.parseInt(cosrec[4]);
    }

    public CosRecData[] parseCosRecReponse(byte[] data) {
        String[] datas = new String(data).split(" ");

        CosRecData[] cosrecs = new CosRecData[datas.length];
        for (int i = 0; i < datas.length; i++) {
            cosrecs[i] = new CosRecData(datas[i]);
        }
        return cosrecs;
    }
}
