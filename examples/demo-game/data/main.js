var client, lastMsg, map, pressed, server;

non.init = function() {
  map = non.tiled.newMap("map.tmx");
  non.audio.play(non.audio.newMusic("music.ogg"));
  non.network.setHost("localhost").setPort(15600).setListener(non.extend(Listener, {
    connected: function(conn) {
      lastMsg = "client connected";
    },
    disconnected: function(conn, forced) {
      lastMsg = "client disconnected";
    },
    receive: function(data, conn) {
      lastMsg = "data received: " + data.read();
    }
  }));
  (server = non.network.newServer()).listen();
  (client = non.network.newClient()).connect();
  lastMsg = "Waiting....";
};

non.update = function() {
  var buffer;
  if (non.keyboard.isKeyJustPressed("Space")) {
    buffer = non.network.newBuffer();
    buffer.write(1);
    client.send(buffer);
  }
  if (non.keyboard.isKeyPressed("Space")) {
    pressed = "Key pressed: Spacebar (release Spacebar to test)";
  } else {
    pressed = "Key pressed: None (press Spacebar to test)";
  }
};

non.draw = function() {
  non.graphics.draw(map);
  non.graphics.draw("Author: YourBestNightmare", 10, 10, non.graphics.newColor("yellow"));
  non.graphics.draw("Engine: non (no nonsense) framework", 10, 34);
  non.graphics.draw("Description: In this example we are testing music, input, tmx rendering, images and text displaying.", 10, 58);
  non.graphics.draw(pressed, 10, 82, non.graphics.newColor("cyan"));
  non.graphics.draw("FPS: " + non.getFPS(), 10, 104);
  non.graphics.draw(lastMsg, 10, 126, non.graphics.newColor("red"));
};