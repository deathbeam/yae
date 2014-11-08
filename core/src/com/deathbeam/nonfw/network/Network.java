/*
 * The MIT License
 *
 * Copyright 2014 Tomas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.deathbeam.nonfw.network;

import com.deathbeam.nonfw.Game;
import com.deathbeam.nonfw.network.client.ClientConnection;
import com.deathbeam.nonfw.network.server.Server;
import com.deathbeam.nonfw.network.server.ServerConnection;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

/**
 *
 * @author Tomas
 */
public class Network {
    private Listener listener;
    private String host;
    private int port;
    
    public Object connected;
    public Object disconnected;
    public Object received;
    
    public ByteArrayOutputStream newBuffer() {
        return new ByteArrayOutputStream();
    }
    
    public Server newServer() {
        return new Server(listener, port);
    }
    
    public ClientConnection newClient() {
        return new ClientConnection(listener, host, port);
    }
    
    public Network init() {
        Game.scripting.put("int_connected", connected);
        Game.scripting.put("int_disconnected", disconnected);
        Game.scripting.put("int_received", received);
        this.listener = new Listener() {
            @Override
            public void disconnected(Connection broken, boolean forced) {
                Game.scripting.put("int_connection", broken);
                Game.scripting.invoke("int_disconnected", "int_connection");
            }

            @Override
            public void receive(DataInputStream data, Connection from) {
                Game.scripting.put("int_connection", from);
                Game.scripting.put("int_data", data);
                Game.scripting.invoke("int_received", "int_data", "int_connection");
            }

            @Override
            public void connected(ServerConnection conn) {
                Game.scripting.put("int_connection", conn);
                Game.scripting.invoke("int_connected", "int_connection");
            }
        };
        return this;
    }
    
    public Network setHost(String host) {
        this.host = host;
        return this;
    }
    
    public Network setPort(int port) {
        this.port = port;
        return this;
    }
}
