var graphics = require('non.graphics'); // loads non.graphics module

// Event used for pre-loading game data
non.load = function(assets) {
	
};

// Event used for initialization
non.ready = function() {
	
};

// Event used for updating game objects
non.update = function(deltaTime) {
	
};

// Event used for drawing images, text and objects
non.draw = function() {
    graphics.clear('black'); // clears the screen with black color
    graphics.print({text: 'Hello World', position: [10, 15]}); // draws Hello World text on x:10 and y:15 with default color (white)
};