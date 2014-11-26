package com.codeindie.non.plugins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.Socket;
import com.codeindie.non.Game;
import com.codeindie.non.scripting.ScriptRuntime;
import com.codeindie.non.Utils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Network extends Plugin {
    public String name() { return "network"; }
    public String author() { return "Thomas Slusny"; }
    public String license() { return "MIT"; }
    public String description() { return "Simple TCP networking."; }
    
    private Listener listener;
    private String host;
    private int port;
    public Object connected, disconnected, received, curData, curConn;
    
    interface Listener {
        public void disconnected(Connection broken, boolean forced);
        public void receive(DataInputStream data, Connection from);
        public void connected(ServerConnection conn);
    }
    
    abstract class Connection {
        private static final int MAGIC_NUMBER = 1304231989;
        protected Listener listener;
        private final byte[] headerInput = new byte[8];
        private final byte[] headerOutput = new byte[8];
        private long bytesSent = 0;
        public abstract boolean isConnected();
        public abstract void send(ByteArrayOutputStream data);
        public abstract void close();
        protected abstract InputStream getTCPInputStream();
        protected abstract OutputStream getTCPOutputStream();

        public Connection(Listener listener) {
            if (listener == null) Utils.warning("network", "You must supply a connection listener.");
            this.listener = listener;
        }

        public long getBytesSent() { return bytesSent; }

        protected byte[] readTCP() throws IOException, Exception {
            InputStream tcpInput = getTCPInputStream();
            if (tcpInput.read(headerInput) == -1) return null;
            int magicNumber = ByteBuffer.wrap(headerInput).getInt();
            if (magicNumber != MAGIC_NUMBER) Utils.warning("network", "Bad magic number: " + magicNumber);
            int len = ByteBuffer.wrap(headerInput).getInt(4);
            byte[] data = new byte[len];
            int count = 0;
            while (count < len) count += tcpInput.read(data, count, len - count);
            return decompress(data);
        }

        protected void sendTCP(byte[] data) throws IOException {
            OutputStream tcpOutput = getTCPOutputStream();
            data = compress(data);
            ByteBuffer.wrap(headerOutput).putInt(MAGIC_NUMBER);
            ByteBuffer.wrap(headerOutput).putInt(4, data.length);
            tcpOutput.write(headerOutput);
            tcpOutput.write(data);
            tcpOutput.flush();
            bytesSent += data.length;
        }

        protected byte[] compress(byte[] data) {
            Deflater compressor = new Deflater();
            compressor.setInput(data);
            compressor.finish();
            ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
            byte[] buf = new byte[1024];
            while (!compressor.finished()) { int count = compressor.deflate(buf); bos.write(buf, 0, count); }
            return bos.toByteArray();
        }

        protected byte[] decompress(byte[] data) {
            Inflater decompressor = new Inflater();
            decompressor.setInput(data);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
            byte[] buf = new byte[1024];
            while (!decompressor.finished()) {
                try {
                    int count = decompressor.inflate(buf);
                    bos.write(buf, 0, count);
                } catch (DataFormatException e) {
                    throw new RuntimeException(e);
                }
            }

            return bos.toByteArray();
        }
    }
    
    class ClientConnection extends Connection {
        private Socket socket;
        protected final String host;
        protected final int port;
        private boolean connected = false;
        private InputStream input;
        private OutputStream output;

        public ClientConnection(Listener listener, String host, int port) {
            super(listener);
            this.listener = listener;
            this.host = host;
            this.port = port;
        }

        public boolean isConnected() { return connected; }
        protected InputStream getTCPInputStream() { return input; }
        protected OutputStream getTCPOutputStream() { return output; }
        public synchronized void connect() { connect(0); }

        public synchronized void connect(int timeout) {
            if (connected) {
                Utils.warning("Networking", "Tried to connect after already connected!");
                return;
            }

            socket = Gdx.net.newClientSocket(Net.Protocol.TCP, host, port, null);
            input = new BufferedInputStream(socket.getInputStream());
            output = new BufferedOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        DataInputStream ret = null;
                        try { ret = new DataInputStream(new ByteArrayInputStream(readTCP())); }
                        catch (IOException e) {
                            if (connected) { connected = false; listener.disconnected(ClientConnection.this, false); }
                            else listener.disconnected(ClientConnection.this, true);
                            break;
                        } catch (Exception e) { Utils.log("Networking", e.getMessage()); break; }
                        listener.receive(ret, ClientConnection.this);
                    }
                }
            }).start();
            connected = true;
        }

        public synchronized void send(ByteArrayOutputStream data) {
            if (!connected) {
                Utils.warning("Networking", "Cannot send message when not connected!");
                return;
            }

            try { super.sendTCP(data.toByteArray()); }
            catch (IOException e) { Utils.warning("Networking", "Error writing TCP data."); }
        }

        public void close() {
            if (!connected) {
                Utils.warning("Networking", "Cannot close the connection when it is not connected.");
                return;
            }

            socket.dispose();
            try { input.close(); output.close(); }
            catch (IOException ex) { Utils.log("Networking", ex.getMessage()); }
            connected = false;
        }
    }
    
    class ServerConnection extends Connection {
        private final Server controller;
        private final Socket socket;
        private final OutputStream output;
        private final InputStream input;
        private boolean connected = true;
        private final String ip;

        public ServerConnection(Server controller, Listener listener, Socket socket) throws IOException {
            super(listener);
            this.controller = controller;
            this.socket = socket;
            this.ip = socket.getRemoteAddress();
            output = new BufferedOutputStream(socket.getOutputStream());
            input = new BufferedInputStream(socket.getInputStream());
            startTCPListener();
        }

        public boolean isConnected() { return connected; }
        protected InputStream getTCPInputStream() { return input; }
        protected OutputStream getTCPOutputStream() { return output; }
        public String toString() { return ip; }

        private void startTCPListener() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        DataInputStream ret;
                        try { ret = new DataInputStream(new ByteArrayInputStream(readTCP())); }
                        catch (SocketException e) {
                            if (connected) {
                                connected = false;
                                controller.connectionDied(ServerConnection.this, false);
                                listener.disconnected(ServerConnection.this, false);
                            } else {
                                controller.connectionDied(ServerConnection.this, true);
                                listener.disconnected(ServerConnection.this, true);
                            }
                            break;
                        } catch (Exception e) { Utils.log("Networking", e.getMessage()); break; }
                        listener.receive(ret, ServerConnection.this);
                    }
                }
            }).start();
        }

        public synchronized void send(ByteArrayOutputStream data) {
            if (!connected) {
                Utils.warning("Networking", "Cannot send message when not connected!");
                return;
            }

            try { sendTCP(data.toByteArray()); }
            catch (IOException e) { Utils.warning("Error writing TCP data.", e.getMessage()); }
        }

        public void close() {
            if (!connected) {
                Utils.warning("Networking", "Cannot close the connection when it is not connected.");
                return;
            }

            socket.dispose();
            try { input.close(); output.close(); }
            catch (IOException ex) { Utils.log("Networking", ex.getMessage()); }
            connected = false;
        }
    }
    
    class Server {
        private final Listener listener;
        private final ServerSocket socket;
        private boolean running = false;
        protected final int port;
        private final HashMap<String, ServerConnection> clients = new HashMap<String, ServerConnection>();

        public Server(Listener listener, int port) {
            this.listener = listener;
            this.port = port;
            socket = Gdx.net.newServerSocket(Net.Protocol.TCP, port, null);
        }

        public synchronized void listen() {
            if (running) {
                Utils.warning("Networking", "Cannot start server when already running!");
                return;
            }

            running = true;
            new Thread(new Runnable() {
                public void run() {
                    while (running) {
                        Socket sock = socket.accept(null);
                        ServerConnection sc;
                        try {
                            sc = new ServerConnection(Server.this, listener, sock);
                            clients.put(sc.toString(), sc);
                            listener.connected(sc);
                        } catch (IOException ex) { Utils.log("Networking", ex.getMessage()); }
                    }
                }
            }).start();
        }

        public void shutdown(boolean closeAllConnections) {
            running = false;
            socket.dispose();

            synchronized (clients) {
                LinkedList<String> ips = new LinkedList<String>();
                for (String ip : clients.keySet()) ips.add(ip);
                for (String ip : ips) { ServerConnection sc = clients.get(ip); sc.close(); }
            }
        }

        public void connectionDied(ServerConnection conn, boolean forced) {
            synchronized (clients) {
                clients.remove(conn.toString());
            }
        }
    }
    
    public Network setHost(String host) { this.host = host; return this; }
    public Network setPort(int port) { this.port = port; return this; }
    public ByteArrayOutputStream newBuffer() { return new ByteArrayOutputStream(); }
    public Server newServer() { return new Server(listener, port); }
    public ClientConnection newClient() { return new ClientConnection(listener, host, port); }
    
    public Network init() {
        this.listener = new Listener() {
            public void disconnected(Connection broken, boolean forced) {
                curConn = broken;
                ScriptRuntime.getCurrent().invoke(name(), "disconnected", name() + ".curConn");
            }

            public void receive(DataInputStream data, Connection from) {
                curData = data;
                curConn = from;
                ScriptRuntime.getCurrent().invoke(name(), "received", name() + ".curData", name() + ".curConn");
            }

            public void connected(ServerConnection conn) {
                curConn = conn;
                ScriptRuntime.getCurrent().invoke(name(), "connected", name() + ".curConn");
            }
        };
        return this;
    }
}
