var physics = require("non.physics");
var graphics = require("non.graphics");
var math = require("non.math");

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
    physics.setDebug(true).setGravity(0, -10).setSpeed(3);
    
    var bodyDef = physics.bodyDef();
    groundBody = physics.body(bodyDef);
    bodyDef.type = "dynamic";
    
    var ballDef = physics.fixtureDef();
    ballDef.density = 0.5;
    ballDef.friction = 0.4;
    ballDef.restitution = 0.6;
    
    for(var i = 0; i < 50; i++) {
        bodyDef.position.set(math.random(0, non.getWidth()), math.random(0, non.getHeight()- 60));
        ballDef.shape = math.circle(0, 0, math.random(8, 20));
        physics.fixture(physics.body(bodyDef), ballDef);
    }
    
    var boxDef = physics.fixtureDef();
    boxDef.density = 1.6;
    
    for(var i = 0; i < 40; i++) {
        bodyDef.position.set(math.random(0, non.getWidth()), math.random(0, non.getHeight()- 60));
        boxDef.shape = math.rectangle(0, 0, math.random(8, 20), math.random(8, 20));
        physics.fixture(physics.body(bodyDef), boxDef);
    }
    
    bodyDef.type = "static";
    
    for(var i = 0; i < 7; i++) {
        bodyDef.position.set(math.random(0, non.getWidth()), math.random(0, non.getHeight()- 60));
        boxDef.shape = math.rectangle(0, 0, math.random(8, 20), math.random(8, 20));
        physics.fixture(physics.body(bodyDef), boxDef);
    }
    
    bodyDef.position.set(0, 0);
    borders = physics.body(bodyDef);
    
    boxDef.shape = math.line(0, 0, non.getWidth(), 0);
    physics.fixture(borders, boxDef);
    
    boxDef.shape = math.line(non.getWidth(), 0, non.getWidth(), non.getHeight());
    physics.fixture(borders, boxDef);
    
    boxDef.shape = math.line(0, non.getHeight(), non.getWidth(), non.getHeight());
    physics.fixture(borders, boxDef);
    
    boxDef.shape = math.line(0, 0, 0, non.getHeight());
    physics.fixture(borders, boxDef);
};

non.draw = function() {
    graphics.clear("black");
    physics.draw(graphics);
};

non.touchdown = function(position, pointer, button) {
    graphics.unproject(testPoint.set(position));
	hitBody = null;
    area = math.rectangle(testPoint.x - 0.0001, testPoint.y - 0.0001, 0.0002, 0.0002);
    physics.queryAABB(area, callback);
    
    if (hitBody == groundBody) hitBody = null;
    if (hitBody != null && hitBody.getType() == physics.bodyType("kinematic")) return false;

	if (hitBody != null) {
		jointDef = physics.mouseJointDef();
        jointDef.bodyA = groundBody;
		jointDef.bodyB = hitBody;
        jointDef.collideConnected = true;
		jointDef.target.set(testPoint.x, testPoint.y);
		jointDef.maxForce = 1000.0 * hitBody.getMass();
        mouseJoint = physics.mouseJoint(jointDef);
		hitBody.setAwake(true);
	}
};

non.touchdragged = function(position, pointer) {
    if (mouseJoint != null) {
		graphics.unproject(testPoint.set(position));
		mouseJoint.setTarget(target.set(testPoint.x, testPoint.y));
	}
};

non.touchup = function(position, pointer, button) {
    if (mouseJoint != null) {
		physics.destroy(mouseJoint);
		mouseJoint = null;
	}
};