package non.modules;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;

import non.Non;
import non.Buffer;

public class NonNetwork extends Module {
    class ScriptListener extends Listener {
        public void connected (Connection connection) {
            Non.script.callMethod(Non.receiver, "connected", connection);
        }
        
        public void received (Connection connection, Object object) {
            if (!(object instanceof byte[])) return;
            Non.script.callMethod(Non.receiver, "received", connection, (byte[])object);
        }

        public void disconnected (Connection connection) {
            Non.script.callMethod(Non.receiver, "disconnected", connection);
        }
    }
    
    private Listener listener;
    
    public NonNetwork() {
        setListener(new ScriptListener());
    }
    
    public Client client() { 
        Client client = new Client();
        register(client);
        client.addListener(listener);
        
        return client;
    }
    
    public Server server() { 
        Server server = new Server();
        register(server);
        server.addListener(listener);

        return server;
    }
    
    public Buffer buffer() {
        return new Buffer();
    }
    
    public Buffer buffer(byte[] data) {
        return new Buffer(data);
    }
    
    public void setListener(Listener listener) {
        this.listener = listener;
    }
    
    private void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(byte[].class);
    }
}