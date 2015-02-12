var graphics = require('non.graphics');
var network = require('non.network');
var math = require('non.math');
var keyboard = require('non.keyboard');
var server, client;
var messages = [ ];
var messageColors = [ ];
var messageCount = 0;

addMessage = function(message) {
    messages[messageCount] = message;
    messageColors[messageCount] = graphics.color(math.random(), math.random(), math.random());
    messageCount++;
};

network.connected = function (connection) {
    addMessage(connection.toString() + ' connected: ' + connection.getRemoteAddressTCP().toString());
};

network.received = function (connection, data) {
    var buffer = network.buffer(data);
    addMessage(connection.getRemoteAddressTCP().toString() + ': ' + buffer.readString());
};

non.ready = function() {
    addMessage('Press any key on keyboard...');
    
    server = network.server();
    server.start();
    server.bind(54555, 54777);
    
    client = network.client();
    client.start();
    client.connect(5000, 'localhost', 54555, 54777);
    
    keyboard.show();
};

non.keydown = function(key) {
    var buffer = network.buffer();
    buffer.writeString('pressed ' + key + ' key');
    client.sendTCP(buffer.read());
};

non.draw = function() {
    graphics.clear('black');
    for(var i = 0; i < messageCount; i++) {
        graphics.tint(messageColors[i]);
        graphics.print({text: messages[i], position: [ 10, 10 + (15 * i) ]});
    }
};

non.close = function() {
    server.stop();
};