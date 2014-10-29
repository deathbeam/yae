package com.deathbeam.nonfw.network.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.deathbeam.nonfw.Utils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.deathbeam.nonfw.network.Connection;
import com.deathbeam.nonfw.network.Listener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

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
            Utils.warning("network", "Tried to connect after already connected!");
            return;
        }
    
        socket = Gdx.net.newClientSocket(Net.Protocol.TCP, host, port, null);
        input = new BufferedInputStream(socket.getInputStream());
        output = new BufferedOutputStream(socket.getOutputStream());

        startTCPListener();
        connected = true;
    }

    @Override
    public synchronized void send(ByteArrayOutputStream data) {
        if (!connected) {
            Utils.warning("network", "Cannot send message when not connected!");
            return;
        }

        try {
            super.sendTCP(data.toByteArray());
        } catch (IOException e) {
            Utils.warning("network", "Error writing TCP data.");
        }
    }

    private void startTCPListener() {
      Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
          while (true) {
            DataInputStream ret;
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
              e.printStackTrace();
              break;
            }
            if (ret == null) {
              // the stream has ended
              if (connected) {
                connected = false;
                listener.disconnected(ClientConnection.this, false);
              } else {
                listener.disconnected(ClientConnection.this, true);
              }
              break;
            }
            try {
              listener.receive(ret, ClientConnection.this);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      });
      t.setName("Jexxus-TCPSocketListener");
      t.start();
    }

    @Override
    public void close() {
      if (!connected) {
        System.err.println("Cannot close the connection when it is not connected.");
      } else {
        try {
          socket.dispose();
          input.close();
          output.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        connected = false;
      }
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
