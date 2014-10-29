package com.deathbeam.nonfw.network.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.ServerSocket;
import com.deathbeam.nonfw.network.Listener;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Acts as a server for incoming client connections. The server can send and receive data from all clients who connect to this server.
 * 
 * @author Jason
 * 
 */
public class Server {

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

  /**
   * After the server has started, it is open for accepting new client connections.
   */
  public synchronized void listen() {
    if (running) {
      System.err.println("Cannot start server when already running!");
      return;
    }
    running = true;
    startTCPConnectionListener();
  }

  private void startTCPConnectionListener() {
    Thread t = new Thread(new Runnable() {
      public void run() {
        while (running) {
            Socket sock = socket.accept(null);
            ServerConnection sc;
            try {
                sc = new ServerConnection(Server.this, listener, sock);
                clients.put(sc.getIP(), sc);
                listener.connected(sc);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      }
    });
    t.setName("Jexxus-TCPConnectionListener");
    t.start();
  }

  void connectionDied(ServerConnection conn, boolean forced) {
    synchronized (clients) {
      clients.remove(conn.getIP());
    }
  }
  /**
   * After the server has shut down, no new client connections can be established.
   * 
   * @param closeAllConnections
   *            If this is true, all previously opened client connections will be closed.
   */
  public void shutdown(boolean closeAllConnections) {
    running = false;
    socket.dispose();
    
    synchronized (clients) {
      LinkedList<String> ips = new LinkedList<String>();
      for (String ip : clients.keySet()) {
        ips.add(ip);
      }
      for (String ip : ips) {
        ServerConnection sc = clients.get(ip);
        sc.close();
      }
    }
  }
}
