package non.plugins;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;

import non.Non;
import non.NonBuffer;

public class network extends Plugin {
    public String author()      { return "Thomas Slusny"; }
    public String license()     { return "MIT"; }
    public String description() { return "Simple networking plugin."; }
    
    public class ScriptListener extends Listener {
        public void connected (Connection connection) {
            Non.script.invoke("network", "connected", connection);
        }
        
        public void received (Connection connection, Object object) {
            if (!object instanceof byte[]) return;
            Non.script.invoke("network", "received", connection, (byte[])object);
        }

        public void disconnected (Connection connection) {
            Non.script.invoke("network", "disconnected", connection);
        }
    }
    
    public Object connected, disconnected, received;
    
    public Client client() { 
        Client client = new Client();
        register(client);
        client.addListener(new ScriptListener());
        
        return client;
    }
    
    public Server server() { 
        Server server = new Server();
        register(server);
        server.addListener(new ScriptListener());

        return server;
    }
    
    private void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(byte[].class);
    }
}