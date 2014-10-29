package com.deathbeam.nonfw.network.server;

import java.io.*;
import com.badlogic.gdx.net.Socket;
import com.deathbeam.nonfw.network.Connection;
import com.deathbeam.nonfw.network.Listener;
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

  ServerConnection(Server controller, Listener listener, Socket socket)
      throws IOException {
    super(listener);

    this.controller = controller;
    this.socket = socket;
    this.ip = socket.getRemoteAddress();
    output = new BufferedOutputStream(socket.getOutputStream());
    input = new BufferedInputStream(socket.getInputStream());

    startTCPListener();
  }

  private void startTCPListener() {
    Thread t = new Thread(new Runnable() {
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
            e.printStackTrace();
            break;
          }
          if (ret == null) {
            // the stream has ended
            if (connected) {
              connected = false;
              controller.connectionDied(ServerConnection.this, false);
              listener.disconnected(ServerConnection.this, false);
            } else {
              controller.connectionDied(ServerConnection.this, true);
              listener.disconnected(ServerConnection.this, true);
            }
            break;
          }
          try {
            listener.receive(ret, ServerConnection.this);
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
  public synchronized void send(ByteArrayOutputStream data) {
    if (connected == false) {
      throw new RuntimeException("Cannot send message when not connected!");
    }
      try {
        sendTCP(data.toByteArray());
      } catch (IOException e) {
        System.err.println("Error writing TCP data.");
        System.err.println(e.toString());
      }
  }

  /**
   * @return The IP of this client.
   */
  public String getIP() {
    return ip;
  }

  @Override
  public void close() {
    if (!connected) {
      throw new RuntimeException("Cannot close the connection when it is not connected.");
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

  @Override
  public String toString() {
    return ip;
  }
}
