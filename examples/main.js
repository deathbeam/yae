var map, pressed, client, server, msg, light, circle;

non.ready = function() {
    map = tiled.newMap("data/map.tmx");
    audio.play(audio.newMusic("data/music.ogg"));
    
    physics.setGravity(0,10).init();
    physics.newShape(map.data.getLayers().get("Collisions").getObjects());
    physics.newShape(math.newRectangle(32,10,20,20));
    physics.newShape(math.newRectangle(100,10,20,20));
    physics.newShape(math.newRectangle(0,150,20,20), "static");
    physics.newShape(math.newRectangle(240,300,200,20), "static");
    
    circle = physics.newShape(math.newCircle(32,64,20), "dynamic", 0.5, 0.4, 0.6);
    lights.setShadows(true).init(physics);
    light = lights.newPointLight(100, graphics.newColor("red"), 500, input.touch.getX(), input.touch.getY());
};

non.update = function() {
    physics.update();
    lights.update();
    
    light.setPosition(input.touch.getX(), input.touch.getY());
    circle.setTransform(input.touch.getX(), input.touch.getY(), 0);
};

non.draw = function() {
    graphics.begin();
    map.draw(graphics);
    lights.draw(graphics);
    physics.draw(graphics);
    
    graphics.draw("Author: YourBestNightmare", 10, 10, graphics.newColor("yellow"));
    graphics.draw("Engine: non (no nonsense) framework", 10, 34, graphics.newColor("white"));
    graphics.draw("Mouse pos: [" + input.touch.getX() + "," + input.touch.getY() + "] Light pos: [" + light.getPosition().x + "," + light.getPosition().y + "]", 10, 58, graphics.newColor("white"));
    graphics.draw("FPS: " + non.getFPS(), 10, 82, graphics.newColor("white"));
    graphics.end();
};