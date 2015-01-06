package non.plugins.internal;

import non.Non;
import non.NonBuffer;
import non.plugins.Plugin;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

public class network extends Plugin {
    public String author()      { return "Thomas Slusny"; }
    public String license()     { return "MIT"; }
    public String description() { return "Simple networking plugin."; }
    
    public class ScriptListener implements Listener {
        public void connected (Connection connection) {
            Non.script.invoke("network", "connected", connection);
        }
        
        public void received (Connection connection, Object object) {
            Non.script.invoke("network", "received", connection, (byte[])object);
        }

        public void disconnected (Connection connection) {
            Non.script.invoke("network", "disconnected", connection);
        }
    }
    
    public Object connected, disconnected, received;
    
    public Client client() { 
        Client client = new Client();
        client.start();
        register(client);
        client.addListener(new ThreadedListener(new ScriptListener()));
        
        return client;
    }
    
    public Server server() { 
        Server server = new Server();
        server.start();
        register(server);
        server.addListener(new ThreadedListener(new ScriptListener()));

        return server;
    }
    
    private void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(byte[].class);
    }
}