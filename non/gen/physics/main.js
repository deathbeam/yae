var physics = require('non.physics');
var graphics = require('non.graphics');
var math = require('non.math');

var groundBody = null;
var mouseJoint = null;
var hitBody = null;
var testPoint = math.vector();
var target = math.vector();

callback = function(fixture) {
    if (fixture.testPoint(testPoint.x, testPoint.y)) {
        hitBody = fixture.getBody();
        return false;
    } else {
        return true;
    }
};

non.ready = function() {
    physics.setGravity(0, -10).setSpeed(3);
    groundBody = physics.body({type: 'static'});
    
    for(var i = 0; i < 50; i++) {
        var body = physics.body({
            type: 'dynamic',
            position: [math.random(0, non.getWidth()), math.random(0, non.getHeight())]
        });

        physics.fixture(body, {
            shape: math.circle(0, 0, math.random(8, 20)),
            density: 0.5,
            friction: 0.4,
            restitution: 0.6
        });
    }
    
    for(var i = 0; i < 40; i++) {
        var body = physics.body({
            type: 'dynamic',
            position: [math.random(0, non.getWidth()), math.random(0, non.getHeight())]
        });

        physics.fixture(body, {
            shape: math.rectangle(0, 0, math.random(8, 20), math.random(8, 20)),
            density: 1.6
        });
    }

    for(var i = 0; i < 7; i++) {
        var body = physics.body({
            type: 'static',
            position: [math.random(0, non.getWidth()), math.random(0, non.getHeight())]
        });

        physics.fixture(body, {
            shape: math.rectangle(0, 0, math.random(8, 20), math.random(8, 20)),
        });
    }
    
    var borders = physics.body({type: 'static'});
    physics.fixture(borders, {shape: math.line(0, 0, non.getWidth(), 0)});
    physics.fixture(borders, {shape: math.line(non.getWidth(), 0, non.getWidth(), non.getHeight())});
    physics.fixture(borders, {shape: math.line(0, non.getHeight(), non.getWidth(), non.getHeight())});
    physics.fixture(borders, {shape: math.line(0, 0, 0, non.getHeight())});
};

non.draw = function() {
    graphics.clear('black');
    physics.draw(graphics);
};

non.touchdown = function(x, y, pointer, button) {
    graphics.unproject(testPoint.set(x, y));
    hitBody = null;
    area = math.rectangle(testPoint.x - 0.0001, testPoint.y - 0.0001, 0.0002, 0.0002);
    physics.queryAABB(area, callback);
    
    if (hitBody == groundBody) hitBody = null;
    if (hitBody != null && hitBody.getType() == physics.bodyType('kinematic')) return false;

    if (hitBody != null) {
        mouseJoint = physics.joint({
            type: 'mouse',
            bodyA: groundBody,
            bodyB: hitBody,
            collideConnected: true,
            target: [testPoint.x, testPoint.y],
            maxForce: 1000.0 * hitBody.getMass()
        });
        
        hitBody.setAwake(true);
    }
};

non.touchdragged = function(x, y, pointer) {
    if (mouseJoint == null) return false;
    graphics.unproject(testPoint.set(x, y));
    mouseJoint.setTarget(target.set(testPoint.x, testPoint.y));
};

non.touchup = function(position, pointer, button) {
    if (mouseJoint == null) return false;
    physics.destroy(mouseJoint);
    mouseJoint = null;
};