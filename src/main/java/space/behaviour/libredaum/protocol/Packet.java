package space.behaviour.libredaum.protocol;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Packet {

    public static final byte SOH = 0x01;
    public static final byte ETB = 0x17;
    public static final byte ACK = 0x06;
    public static final byte NAK = 0x15;
    public static final byte GS = 0x1d;

    public static final String SEP = new String(new byte[]{GS});

    public final byte[] bytes;

    protected Packet(final byte[] header, final byte[] data) {
        int packet_len = 4 + data.length + 2 + 1;
        bytes = new byte[packet_len];
        bytes[0] = SOH;
        System.arraycopy(header, 0, bytes, 1, header.length);
        System.arraycopy(data, 0, bytes, 4, data.length);
        byte[] cs = checksum(header, data);
        System.arraycopy(cs, 0, bytes, 4+data.length, cs.length);
        bytes[packet_len-1] = ETB;
    }

    static byte[] checksum(byte[] header, byte[] data) {
        byte[] cs = new byte[header.length + data.length];
        System.arraycopy(header, 0 , cs, 0, header.length);
        System.arraycopy(data, 0, cs, header.length, data.length);

        int sum = 0;
        for (byte b : cs) {
            sum += b;
        }
        sum %= 100;
        return Integer.toString(sum).getBytes(StandardCharsets.US_ASCII);
    }
}
