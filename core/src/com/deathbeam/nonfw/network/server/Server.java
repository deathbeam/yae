package com.deathbeam.nonfw.network.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.ServerSocket;
import com.deathbeam.nonfw.Utils;
import com.deathbeam.nonfw.network.Listener;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

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
  
    public synchronized void listen() {
        if (running) {
            Utils.warning("Networking", "Cannot start server when already running!");
            return;
        }
        
        running = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    Socket sock = socket.accept(null);
                    ServerConnection sc;
                    try {
                        sc = new ServerConnection(Server.this, listener, sock);
                        clients.put(sc.toString(), sc);
                        listener.connected(sc);
                    } catch (IOException ex) {
                        Utils.log("Networking", ex.getMessage());
                    }
                }
            }
        }).start();
    }
    
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

    void connectionDied(ServerConnection conn, boolean forced) {
        synchronized (clients) {
            clients.remove(conn.toString());
        }
    }
}