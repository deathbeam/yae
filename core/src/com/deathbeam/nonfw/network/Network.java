/*
 * The MIT License
 *
 * Copyright 2014 Thomas Slusny.
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
 * @author Thomas Slusny
 */
public class Network {
    private Listener listener;
    private String host;
    private int port;
    
    public Object connected, disconnected, received, curData, curConn;
    
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
        this.listener = new Listener() {
            @Override
            public void disconnected(Connection broken, boolean forced) {
                curConn = broken;
                Game.scripting.invoke("non.network", "disconnected", "non.network.curConn");
            }

            @Override
            public void receive(DataInputStream data, Connection from) {
                curData = data;
                curConn = from;
                Game.scripting.invoke("non.network", "received", "non.network.curData, non.network.curConn");
            }

            @Override
            public void connected(ServerConnection conn) {
                curConn = conn;
                Game.scripting.invoke("non.network", "connected", "non.network.curConn");
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
