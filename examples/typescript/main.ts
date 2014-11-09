declare var map, pressed, testText, client, server, lastMsg;
non.load("testText.ts");

non.ready = () => {
  map = non.tiled.newMap("map.tmx");
  non.audio.play(non.audio.newMusic("music.ogg"));
  non.network.connected = (conn) => {
	lastMsg = "client connected: " + conn.toString();
  };
  non.network.disconnected = (conn) => {
	lastMsg = "client disconnected";
  };
  non.network.received = (data, conn) => {
	lastMsg = "data received: " + data.read();
  };
  non.network.setHost("localhost").setPort(15600).init();
  (server = non.network.newServer()).listen();
  (client = non.network.newClient()).connect();
};

non.update = () => {
  if (non.keyboard.isKeyJustPressed("Space")) {
    var buffer = non.network.newBuffer();
    buffer.write(1);
    client.send(buffer);
  }
  if (non.keyboard.isKeyPressed("Space")) {
    pressed = "Key pressed: Spacebar (release Spacebar to test)";
  } else {
    pressed = "Key pressed: None (press Spacebar to test)";
  }
};

non.draw = () => {
  non.graphics.draw(map);
  non.graphics.draw("Author: YourBestNightmare", 10, 10, non.graphics.newColor("yellow"));
  non.graphics.draw("Engine: non (no nonsense) framework", 10, 34);
  non.graphics.draw("Description: In this example we are testing music, input, tmx rendering, images and text displaying.", 10, 58);
  non.graphics.draw(pressed, 10, 82, non.graphics.newColor("cyan"));
  non.graphics.draw("FPS: " + non.getFPS(), 10, 104);
  non.graphics.draw(lastMsg, 10, 126, non.graphics.newColor("red"));
};