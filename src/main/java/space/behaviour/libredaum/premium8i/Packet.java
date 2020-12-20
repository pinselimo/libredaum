package space.behaviour.libredaum.premium8i;

import java.nio.ByteBuffer;

public class Packet {

    private final static byte SOH = 0x01b;

    public Packet() {

    }

    public byte[] build() {

    }

    private byte[] header() {

    }

    private byte[] checksum(byte[] data) {
        int cs = (4+data.length)%100;
        return ByteBuffer.allocate(2).putInt(cs).array();
    }
}
