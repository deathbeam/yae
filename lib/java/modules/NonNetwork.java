package non.modules;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import non.JModule;

public class NonNetwork extends Module {
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
    
    public Buffer buffer() {
        return new Buffer();
    }
    
    public Buffer buffer(byte[] data) {
        return new Buffer(data);
    }
    
    private void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(byte[].class);
    }

    class ScriptListener extends Listener {
        public void connected (Connection connection) {
            JModule.call("connected", connection);
        }
        
        public void received (Connection connection, Object object) {
            if (!(object instanceof byte[])) return;
            JModule.call("received", connection, (byte[])object);
        }

        public void disconnected (Connection connection) {
            JModule.call("disconnected", connection);
        }
    }

    public class Buffer {
        private ByteArrayInputStream inBytes;
        private ByteArrayOutputStream outBytes;
        private DataInputStream in;
        private DataOutputStream out;
    
        public Buffer() {
            outBytes = new ByteArrayOutputStream();
            out = new DataOutputStream(outBytes);
        }
    
        public Buffer(byte[] data) {
            inBytes = new ByteArrayInputStream(data);
            in = new DataInputStream(inBytes);
        }
    
        public Buffer write(byte[] data) throws IOException {
            out.write(data);
            return this;
        }
    
        public Buffer writeBoolean(boolean val) throws IOException {
            out.writeBoolean(val);
            return this;
        }
    
        public Buffer writeByte(int val) throws IOException {
            out.writeByte(val);
            return this;
        }
    
        public Buffer writeFloat(float val) throws IOException {
            out.writeFloat(val);
            return this;
        }
    
        public Buffer writeInt(int val) throws IOException {
            out.writeInt(val);
            return this;
        }
    
        public Buffer writeLong(long val) throws IOException {
            out.writeLong(val);
            return this;
        }
    
        public Buffer writeString(String val) throws IOException {
            out.writeUTF(val);
            return this;
        }
    
        public byte[] read() {
            return outBytes.toByteArray();
        }
    
        public boolean readBoolean() throws IOException {
            return in.readBoolean();
        }
    
        public byte readByte() throws IOException {
            return in.readByte();
        }
    
        public float readFloat() throws IOException {
            return in.readFloat();
        }
    
        public int readInt() throws IOException {
            return in.readInt();
        }
    
        public long readLong() throws IOException {
            return in.readLong();
        }
    
        public String readString() throws IOException {
            return in.readUTF();
        }
    }
}