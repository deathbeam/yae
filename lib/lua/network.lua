--[[--------
Advanced client-server networking.

In NÖN we implemented networking engine based on KryoNet.

KryoNet is a Java library that provides a clean and simple API for efficient TCP and UDP client/server network communication using NIO.

@module non.network
]]

non.network = {}
non.network.module = NON:get("network")

--[[----------
Creates instance of client and starts listener
@tparam number timeout timeout after client stops trying to connect to server (in miliseconds)
@tparam number ipAddress address of server to connect
@tparam number tcpPort tcp port of server to connect
@tparam number udpPort udp port of server to connect
@treturn Client a client instance
@usage
-- lua -------------------------------------------------------------------------------------
client = non.network.client(5000, "192.168.0.4", 54555, 54777)
-- moonscript ------------------------------------------------------------------------------
client = non.network.client 5000, "192.168.0.4", 54555, 54777
]]
function non.network.client(timeout, ipAddress, tcpPort, udpPort)
  local client = non.network.module:client()
  client:connect(timeout, ipAddress, tcpPort, udpPort)
  client:start()
  return client
end

--[[----------
Creates instance of server and starts listener
@tparam number tcpPort tcp port on what will be server listening
@tparam number udpPort udp port on what will be server listening
@treturn Server a server instance
@usage
-- lua -------------------------------------------------------------------------------------
server = non.network.server(54555, 54777)
-- moonscript ------------------------------------------------------------------------------
server = non.network.server 54555, 54777
]]
function non.network.server(tcpPort, udpPort)
  local server = non.network.module:server()
  server:bind(tcpPort, udpPort)
  server:start()
  return server
end

--[[----------
Creates instance of byte buffer reader or writer. Buffer what can modify byte arrays used for sending data between client and server
@tparam table data if nil this function will create writeable buffer, if otherwise, this will create readable buffer
@treturn Buffer a buffer instance
@usage
-- lua -------------------------------------------------------------------------------------
buffer = non.network.buffer()
buffer:writeString("Hello World")
client:sendTCP(buffer:read())
...
buffer = non.network.buffer(data)
stringData = buffer:readString()
-- moonscript ------------------------------------------------------------------------------
buffer = non.network.buffer!
buffer\writeString "Hello World"
client\sendTCP buffer\read!
...
buffer = non.network.buffer data
stringData = buffer\readString!
]]
function non.network.buffer(data)
  if data == nil then
    return non.network.module:buffer()
  else
    return non.network.module:buffer(data)
  end
end