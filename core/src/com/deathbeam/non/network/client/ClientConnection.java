package com.deathbeam.non.network.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
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

/**
 * Used to establish a connection to a server.
 * 
 * @author Jason
 * 
 */
public class ClientConnection extends Connection {
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

    public synchronized void connect() {
        connect(0);
    }

    public synchronized void connect(int timeout) {
        if (connected) {
            Utils.warning("Networking", "Tried to connect after already connected!");
            return;
        }
    
        socket = Gdx.net.newClientSocket(Net.Protocol.TCP, host, port, null);
        input = new BufferedInputStream(socket.getInputStream());
        output = new BufferedOutputStream(socket.getOutputStream());

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    DataInputStream ret = null;
                    try {
                        ret = new DataInputStream(new ByteArrayInputStream(readTCP()));
                    } catch (IOException e) {
                        if (connected) {
                          connected = false;
                          listener.disconnected(ClientConnection.this, false);
                        } else {
                          listener.disconnected(ClientConnection.this, true);
                        }
                        break;
                    } catch (Exception e) {
                        Utils.log("Networking", e.getMessage());
                        break;
                    }
                  

                    listener.receive(ret, ClientConnection.this);

                }
            }
        }).start();
        connected = true;
    }

    @Override
    public synchronized void send(ByteArrayOutputStream data) {
        if (!connected) {
            Utils.warning("Networking", "Cannot send message when not connected!");
            return;
        }

        try {
            super.sendTCP(data.toByteArray());
        } catch (IOException e) {
            Utils.warning("Networking", "Error writing TCP data.");
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
}
