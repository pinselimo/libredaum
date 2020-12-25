package space.behaviour.libredaum;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import space.behaviour.libredaum.protocol.Packet;
import space.behaviour.libredaum.protocol.Response;

public class ResponseHandler implements Runnable {
    private final static String TAG = "Daum.ResponseHandler";

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
        Logger.getLogger(TAG).log(Level.INFO, "Listening for Daum responses....");

        try
        {
            byte[] buffer  = new byte[1024];
            int buffCount  = 0;
            int pos = 0;

            while (true)
            {
                buffCount = this.INPUT_STREAM.read(buffer);
                Logger.getLogger(TAG).log(Level.INFO, "Received response: " + Arrays.toString(Arrays.copyOf(buffer, buffCount)));

                while (buffCount > 0) {
                    Response r = null;

                    switch (buffer[pos]) {
                        case Packet.NAK:
                            CONNECTION_LISTENER.receivedNAK();
                        case Packet.ACK:
                            buffCount -= 1;
                            pos += 1;
                            break;
                        case Packet.SOH:
                            int end = responseLength(pos, buffer);
                            int length = end-pos;

                            byte[] responseBytes = new byte[length];
                            System.arraycopy(buffer, pos, responseBytes, 0, length);
                            try {
                                r = Response.fromByteArray(responseBytes);
                                RESPONSE_LISTENER.onResponse(r);
                            } catch (Response.InvalidResponseException requiresNAK) {
                                Logger.getLogger(TAG).log(Level.SEVERE, requiresNAK.getMessage());
                                CONNECTION_LISTENER.requireNAK();
                            }
                            buffCount -= length;
                            pos += length;
                            CONNECTION_LISTENER.requireACK();
                    }
                }
                pos = 0;

                Thread.sleep(10l,0);
            }

        } catch (IOException e)
        {
            Logger.getLogger(TAG).log(Level.SEVERE, e.getMessage());

        } catch (InterruptedException e)
        {
            Logger.getLogger(TAG).log(Level.SEVERE, e.getMessage());

        }
    }

    private static int responseLength(int pos, byte[] buffer) {
        int end = pos;
        while (end < buffer.length && buffer[end] != Packet.ETB)
            end++;
        return end+1;
    }
}
