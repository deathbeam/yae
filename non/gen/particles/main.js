var graphics = require('non.graphics');
var particles = require('non.particles').link(graphics);
var touch = require('non.touch');
var math = require('non.math');

var effect;
var touchPos = math.vector(0, 0);

non.load = function(assets) {
    assets.load('test.p', particles.effectLoader);
};

non.ready = function() {
    effect = particles.effect('test.p');
};

non.update = function(dt) {
    touchPos.set(touch.getX(), touch.getY());
    graphics.unproject(touchPos);
    
    effect.setPosition(touchPos.x, touchPos.y);
	
    if (effect.isComplete()) {
        effect.reset();
    }
};

non.draw = function() {
    graphics.clear('black');
    particles.draw(effect);
};