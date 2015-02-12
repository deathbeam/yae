var handle = require('non.lights');
var light = null;

this.ready = function() {
    handle.setPhysics(physics).setCulling(false);
    light = handle.point(100, graphics.color('red'), 600, 400, 300);
}

this.staticLight = function(body) {
    var red = math.random();
    var green = math.random();
    var blue = math.random();
        
    handle.point(100, graphics.color(red, green, blue), math.random(50, 250), 0, 0).attachToBody(body);
}

this.dynamicLight = function(body) {
    light.attachToBody(body);
}

this.draw = function() {
    handle.draw(graphics);
}