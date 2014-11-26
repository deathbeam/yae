local map, pressed, client, server, msg, light, circle

non.ready = function()
    map = non.tiled:newMap("data/map.tmx")
    audio:play(audio:newMusic("data/music.ogg"))

    network.connected = function(conn) 
        msg = "client connected: "..conn:toString()
    end
  
    network.disconnected = function(conn)
        msg = "client disconnected"
    end

    network.received = function(data, conn)
        msg = "data received: "..data:read()
    end
  
    network:setHost("localhost"):setPort(15600):init()
    server = network:newServer()
    server:listen()
    client = network:newClient()
    client:connect()
    
    physics:setGravity(0,10):init()
    physics:newShape(map)
    circle = physics:newShape(math:newCircle(32,64,20), "dynamic", 0.5, 0.4, 0.6)
    
    lights:setShadows(true):init(physics)
    light = lights:newPointLight(100, graphics:newColor("red"), 500, input.mouse:getX(), input.mouse:getY())
end

non.update = function()
    physics:update()
    lights:update()
    
    light:setPosition(input.mouse:getX(), input.mouse:getY())
    circle:setTransform(input.mouse:getX(), input.mouse:getY(), 0)
    
    if input.keyboard:isKeyJustPressed("Space") then
        local buffer = network:newBuffer()
        buffer:write(1)
        client:send(buffer)
    end
    
    if input.keyboard:isKeyPressed("Space") then
        pressed = "Key pressed: Spacebar (release Spacebar to test)"
    else
        pressed = "Key pressed: None (press Spacebar to test)"
    end
end

non.draw = function()
    map:draw(graphics)
    lights:draw(graphics)
    physics:draw(graphics)
    
    graphics:draw("Author: YourBestNightmare", 10, 10, graphics:newColor("yellow"))
    graphics:draw("Engine: non (no nonsense) framework", 10, 34)
    graphics:draw("Mouse pos: [" + input.mouse:getX() + "," + input.mouse:getY() + "] Light pos: [" + light:getPosition().x + "," + light:getPosition().y + "]", 10, 58)
    graphics:draw(pressed, 10, 82, graphics:newColor("cyan"))
    graphics:draw("FPS: " + non:getFPS(), 10, 104)
    graphics:draw(msg, 10, 126, graphics:newColor("red"))
end
