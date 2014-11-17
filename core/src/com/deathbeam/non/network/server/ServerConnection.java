package com.deathbeam.non.network.server;

import com.badlogic.gdx.net.Socket;
import com.deathbeam.non.Utils;
import com.deathbeam.non.network.Connection;
import com.deathbeam.non.network.Listener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

/**
 * Represents a server's connection to a client.
 */
public class ServerConnection extends Connection {
    private final Server controller;
    private final Socket socket;
    private final OutputStream output;
    private final InputStream input;
    private boolean connected = true;
    private final String ip;

    ServerConnection(Server controller, Listener listener, Socket socket) throws IOException {
        super(listener);

        this.controller = controller;
        this.socket = socket;
        this.ip = socket.getRemoteAddress();
        output = new BufferedOutputStream(socket.getOutputStream());
        input = new BufferedInputStream(socket.getInputStream());

        startTCPListener();
    }

    private void startTCPListener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    DataInputStream ret;
                    try {
                        ret = new DataInputStream(new ByteArrayInputStream(readTCP()));
                    } catch (SocketException e) {
                        if (connected) {
                            connected = false;
                            controller.connectionDied(ServerConnection.this, false);
                            listener.disconnected(ServerConnection.this, false);
                        } else {
                            controller.connectionDied(ServerConnection.this, true);
                            listener.disconnected(ServerConnection.this, true);
                        }
                        break;
                    } catch (Exception e) {
                        Utils.log("Networking", e.getMessage());
                        break;
                    }
                    listener.receive(ret, ServerConnection.this);
                }
            }
        }).start();
    }

    @Override
    public synchronized void send(ByteArrayOutputStream data) {
        if (!connected) {
            Utils.warning("Networking", "Cannot send message when not connected!");
            return;
        }
        
        try {
            sendTCP(data.toByteArray());
        } catch (IOException e) {
            Utils.warning("Error writing TCP data.", e.getMessage());
        }
    }

    @Override
    public void close() {
        if (!connected) {
            Utils.warning("Networking", "Cannot close the connection when it is not connected.");
            return;
        }
      
        socket.dispose();
        try {
            input.close();
            output.close();
        } catch (IOException ex) {
            Utils.log("Networking", ex.getMessage());
        }
        connected = false;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    protected InputStream getTCPInputStream() {
        return input;
    }

    @Override
    protected OutputStream getTCPOutputStream() {
        return output;
    }

    @Override
    public String toString() {
        return ip;
    }
}
