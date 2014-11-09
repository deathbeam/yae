local map, pressed, testText, client, server, lastMsg
non:load("testText.lua")

non.ready = function()
  map = non.tiled:newMap("../data/map.tmx")
  non.audio:play(non.audio:newMusic("../data/music.ogg"))
  non.network.connected = function(conn) 
	lastMsg = "client connected: "..conn:toString()
  end
  non.network.disconnected = function(conn)
	lastMsg = "client disconnected"
  end
  non.network.received = function(data, conn)
	lastMsg = "data received: "..data:read()
  end
  non.network:setHost("localhost"):setPort(15600):init()
  server = non.network:newServer()
  server:listen()
  client = non.network:newClient()
  client:connect()
end

non.update = function()
  if (non.keyboard:isKeyJustPressed("Space")) then
    local buffer = non.network:newBuffer()
    buffer:write(1)
    client:send(buffer)
  else
    
  end
  if non.keyboard:isKeyPressed("Space") then
    pressed = "Key pressed: Spacebar (release Spacebar to test)"
  else
    pressed = "Key pressed: None (press Spacebar to test)"
  end
end

non.draw = function()
  non.graphics:draw(map)
  non.graphics:draw("Author: YourBestNightmare", 10, 10, non.graphics:newColor("yellow"))
  non.graphics:draw("Engine: non (no nonsense) framework", 10, 34)
  non.graphics:draw("Description: In this example we are testing music, input, tmx rendering, images and text displaying.", 10, 58)
  non.graphics:draw(pressed, 10, 82, non.graphics:newColor("cyan"))
  non.graphics:draw("FPS: "..non:getFPS(), 10, 104)
  non.graphics:draw(lastMsg, 10, 126, non.graphics:newColor("red"))
end