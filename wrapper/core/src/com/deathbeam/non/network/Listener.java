package com.deathbeam.non.network;

import com.deathbeam.non.network.server.ServerConnection;

import java.io.DataInputStream;

public interface Listener {
    public void disconnected(Connection broken, boolean forced);
    public void receive(DataInputStream data, Connection from);
    public void connected(ServerConnection conn);
}
