package non.rhino.modules;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import org.mozilla.javascript.Function;

import non.Non;
import non.Buffer;
import non.rhino.RhinoNon;
import non.modules.NonNetwork;

public class RhinoNetwork extends NonNetwork {
    class ScriptListener extends Listener {
        public void connected (Connection connection) {
            RhinoNon.script.call(connected, connection);
        }
        
        public void received (Connection connection, Object object) {
            if (!(object instanceof byte[])) return;
            RhinoNon.script.call(received, connection, (byte[])object);
        }

        public void disconnected (Connection connection) {
            RhinoNon.script.call(disconnected, connection);
        }
    }
    
    public Function connected, disconnected, received;
    
    public RhinoNetwork() {
        setListener(new ScriptListener());
    }
}