package space.behaviour.libredaum;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ResponseHandler implements Runnable {

    private final InputStream INPUT_STREAM;
    private final ConnectionProvider.ResponseListener RESPONSE_LISTENER;
    private final ConnectionListener CONNECTION_LISTENER;

    public interface ConnectionListener {
        void requireNAK();
        void receivedNAK();
        void requireACK();
    }

    public ResponseHandler(InputStream inputStream, ConnectionProvider.ResponseListener responseListener,
                           ConnectionListener listener) {
        this.INPUT_STREAM = inputStream;
        this.RESPONSE_LISTENER = responseListener;
        this.CONNECTION_LISTENER = listener;

    }

    @Override
    public void run() {

        try
        {
            InputStreamReader isr = new InputStreamReader(this.INPUT_STREAM, StandardCharsets.US_ASCII);
            byte[] buffer  = new byte[256];
            int buffCount  = 0;
            String message = "";

            while (true)
            {
                buffCount = this.INPUT_STREAM.read(buffer);

                Response r = null;
                switch (buffer[0]) {
                    case Packet.NAK:
                        CONNECTION_LISTENER.receivedNAK();
                    case Packet.ACK:
                        dropByte(buffer);
                        break;
                    case Packet.SOH:
                        r = Response.fromByteArray(buffer);
                        dropResponse(buffer);
                        RESPONSE_LISTENER.onResponse(r);
                        CONNECTION_LISTENER.requireACK();
                }

                Thread.sleep(10l,0);
            }

        } catch (IOException ignored)
        {

        } catch (InterruptedException ignored)
        {

        } catch (Response.InvalidResponseException requiresNAK) {
            CONNECTION_LISTENER.requireNAK();
        }

    }

    private static void dropByte(byte[] buffer) {
        if (buffer.length - 1 >= 0) System.arraycopy(buffer, 1, buffer, 0, buffer.length - 1);
        buffer[buffer.length-1] = 0;
    }

    private static void dropResponse(byte[] buffer) {
        int end = 0;
        for (; end < buffer.length && end != Packet.ETB; end++);
        if (buffer.length - end >= 0) System.arraycopy(buffer, end, buffer, 0, buffer.length - end);
        for (int i = end; i < buffer.length; i++) {
            buffer[i] = 0;
        }
    }
}
