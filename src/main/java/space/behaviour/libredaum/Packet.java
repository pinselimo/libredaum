package space.behaviour.libredaum;

import java.nio.ByteBuffer;

public class Packet {

    private static final byte SOH = 0x01;
    private static final byte ETB = 0x17;

    public static final byte ACK = 0x06;
    public static final byte NAK = 0x15;

    public final byte[] bytes;

    protected Packet(final byte[] header, final byte[] data) {
        int packet_len = 4 + data.length + 2 + 1;
        bytes = new byte[packet_len];
        bytes[0] = SOH;
        System.arraycopy(header, 0, bytes, 1, header.length);
        System.arraycopy(data, 0, bytes, 4, data.length);
        byte[] cs = checksum(data);
        System.arraycopy(cs, 0, bytes, 4+data.length, cs.length);
        bytes[packet_len-1] = ETB;
    }

    static byte[] checksum(byte[] data) {
        int cs = (4+data.length)%100;
        return ByteBuffer.allocate(2).putInt(cs).array();
    }
}
