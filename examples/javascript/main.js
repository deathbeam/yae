var map, pressed, client, server, msg, light, circle;

non.ready = function() {
    map = non.tiled.newMap("data/map.tmx");
    audio.play(audio.newMusic("data/music.ogg"));
    
    network.connected = function(conn) {
        msg = "client connected: " + conn.toString();
    };
    
    network.disconnected = function(conn) {
        msg = "client disconnected";
    };
    
    network.received = function(data, conn) {
        msg = "data received: " + data.read();
    };
    
    network.setHost("localhost").setPort(15600).init();
    (server = network.newServer()).listen();
    (client = network.newClient()).connect();
    
    physics.setGravity(0,10).init();
    physics.newShape(map);
    physics.newShape(math.newRectangle(32,10,20,20));
    physics.newShape(math.newRectangle(100,10,20,20));
    physics.newShape(math.newRectangle(0,150,20,20), "static");
    physics.newShape(math.newRectangle(240,300,200,20), "static");
    circle = physics.newShape(math.newCircle(32,64,20), "dynamic", 0.5, 0.4, 0.6);
    lights.setShadows(true).init(physics);
    light = lights.newPointLight(100, graphics.newColor("red"), 500, input.mouse.getX(), input.mouse.getY());
};

non.update = function() {
    physics.update();
    lights.update();
    
    light.setPosition(input.mouse.getX(), input.mouse.getY());
    circle.setTransform(input.mouse.getX(), input.mouse.getY(), 0);
    
    if (input.keyboard.isKeyJustPressed("Space")) {
        var buffer = network.newBuffer();
        buffer.write(1);
        client.send(buffer);
    }
    
    if (input.keyboard.isKeyPressed("Space")) {
        pressed = "Key pressed: Spacebar (release Spacebar to test)";
    } else {
        pressed = "Key pressed: None (press Spacebar to test)";
    }
};

non.draw = function() {
    map.draw(graphics);
    lights.draw(graphics);
    physics.draw(graphics);
    
    graphics.draw("Author: YourBestNightmare", 10, 10, graphics.newColor("yellow"));
    graphics.draw("Engine: non (no nonsense) framework", 10, 34);
    graphics.draw("Mouse pos: [" + input.mouse.getX() + "," + input.mouse.getY() + "] Light pos: [" + light.getPosition().x + "," + light.getPosition().y + "]", 10, 58);
    graphics.draw(pressed, 10, 82, graphics.newColor("cyan"));
    graphics.draw("FPS: " + non.getFPS(), 10, 104);
    graphics.draw(msg, 10, 126, graphics.newColor("red"));
};
