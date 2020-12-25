package space.behaviour.libredaum;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import space.behaviour.libredaum.protocol.Packet;
import space.behaviour.libredaum.protocol.Response;

public class ConnectionProvider implements ResponseHandler.ConnectionListener  {
    private Socket socket;
    private Thread inputThread;
    private OutputStream outputStream;

    private Packet lastPacketSent = null;

    private boolean connected = false;

    public interface ConnectionUpdateListener {
        void onConnected();
        void onConnectionError(Exception e);
        void onDisconnected();
    }

    public interface ResponseListener {
        void onResponse(Response response);
    }

    private final ConnectionUpdateListener connectionUpdateListener;
    private final ResponseListener responseListener;

    public ConnectionProvider(ConnectionUpdateListener connectionListener, ResponseListener responseListener) {
        this.connectionUpdateListener = connectionListener;
        this.responseListener = responseListener;
    }

    public boolean sendPacket(Packet packet) {

        try {
            if (isConnected()) {
                outputStream.write(packet.bytes);
                lastPacketSent = packet;
                return true;
            }
        } catch (IOException e) {
            connectionUpdateListener.onConnectionError(e);
            return false;
        }
        return false;
    }

    public void close() {
        this.connected = false;
        if (inputThread.isAlive()) inputThread.interrupt();
        try {
            this.socket.close();
            connectionUpdateListener.onDisconnected();

        } catch (IOException e) {
            connectionUpdateListener.onConnectionError(e);
        }
    }

    public void connect(final InetSocketAddress daum)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Socket sock = new Socket();
                try
                {
                    sock.connect(daum);
                    onSocket(sock);
                }
                catch (IOException e)
                {
                    connectionUpdateListener.onConnectionError(e);
                }
            }
        }).start();
    }

    private void onSocket(final Socket socket) {

        this.socket = socket;
        try
        {
            this.outputStream = socket.getOutputStream();

            inputThread = new Thread(new ResponseHandler(socket.getInputStream(), this.responseListener, this));
            inputThread.start();

            this.connected = true;
            connectionUpdateListener.onConnected();
        }
        catch (IOException e)
        {
            connectionUpdateListener.onConnectionError(e);
        }
    }

    public boolean isConnected() {
        return this.connected;
    }

    @Override
    public void requireNAK() {
        try {
            outputStream.write(Packet.NAK);
        } catch (IOException e) {
            connectionUpdateListener.onConnectionError(e);
        }
    }

    @Override
    public void requireACK() {
        try {
            outputStream.write(Packet.ACK);
        } catch (IOException e) {
            connectionUpdateListener.onConnectionError(e);
        }
    }

    @Override
    public void receivedNAK() {
        try {
            outputStream.write(lastPacketSent.bytes);
        } catch (IOException e) {
            connectionUpdateListener.onConnectionError(e);
        }
    }
}