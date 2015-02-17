var handle = require('non.lights');
var light = null;

this.ready = function() {
    handle.setPhysics(physics).setCulling(false);
    light = handle.point({
        rays:100,
        color: graphics.color('red'),
        distance: 600
    });
}

this.staticLight = function(body) {
    var red = math.random();
    var green = math.random();
    var blue = math.random();
        
    handle.point({
        rays: 100,
        color: graphics.color(red, green, blue),
        distance: math.random(50, 250), 0, 0
    }).attachToBody(body);
}

this.dynamicLight = function(body) {
    light.attachToBody(body);
}

this.draw = function() {
    handle.draw(graphics);
}