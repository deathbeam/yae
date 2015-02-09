var graphics = require('non.graphics');
var math = require('non.math');
var keyboard = require('non.keyboard');
var touch = require('non.touch');

var speed = 3;
var ball = { x: 0, y: 0, vx: -speed, vy: speed };
var pad1 = 0;
var pad2 = 0;
var score1 = 0;
var score2 = 0;
var width = 800;
var height = 600;

non.resize = function(w, h) {
    width = w;
    height = h;
};

non.update = function(dt) {
    if (keyboard.isDown('Q')) pad1 += 7;
    if (keyboard.isDown('W')) pad1 -= 7;
    
    for (var i = 0; i <= 1; i++) {
        if (touch.isDown(i) && touch.getX(i) < width/2)
            pad1 = touch.getY(i) - height/2;
    }
    
    if (pad1 < -(height/2 - 50)) pad1 = -(height/2 - 50);
    if (pad1 > (height/2 - 50)) pad1 = (height/2 - 50);

    if (keyboard.isDown('O')) pad2 += 7;
    if (keyboard.isDown('P')) pad2 -= 7;
    
    for (var i = 0; i <= 1; i++) {
        if (touch.isDown(i) && touch.getX(i) > width/2)
            pad2 = touch.getY(i) - height/2;
    }
    
    if (pad2 < -(height/2 - 50)) pad2 = -(height/2 - 50);
    if (pad2 > (height/2 - 50)) pad2 = (height/2 - 50);
	
    ball.x += ball.vx;
    ball.y += ball.vy;
	
    if (math.abs(ball.y) >= height/2 - 15) ball.vy = -ball.vy;
	
    if ((ball.x < -(width/2 - 75)) && (ball.x > -(width/2 - 50)) && (math.abs(pad1 - ball.y) < 60)){
        speed += 0.2;
        ball.x = -(width/2 - 75);
        ball.vx = speed;
        ball.vy = ball.vy * 0.5 + math.random(-10, 10) / 20 * speed;
    }
	
    if (ball.x < -(width/2 + 15)) {
        ball.x = 0;
        ball.vx = -ball.vx;
        score2++;
    }
	
    if ((ball.x > width/2 - 75) && (ball.x < width/2 - 50) && (math.abs(pad2 - ball.y) < 60)) {
        speed += 0.5;
        ball.x = width/2 - 75;
        ball.vx = -speed;
        ball.vy = ball.vy * 0.8 + math.random(-10, 10) / 10 * speed;
    }
	
    if (ball.x > width/2 + 15) {
        ball.x = 0;
        ball.vx = -ball.vx;
        score1++;
    }
	
    if (score1 > 6 || score2 > 6) {
        score1 = 0;
        score2 = 0;
        speed = 3;
    }
};

non.draw = function() {
    graphics.clear('#1A1A1A');
    graphics.tint('#E8ACE5');
    graphics.translate(width/2, height/2);
    graphics.fill({mode: 'fill', shape: math.line(0, -height/2, 0, height/2)});
    graphics.fill({mode: 'fill', shape: math.rectangle(-(width/2-45), pad1 - 50, 20, 100)});
    graphics.fill({mode: 'fill', shape: math.rectangle(width/2-60, pad2 - 50, 20, 100)});
    graphics.fill({mode: 'fill', shape: math.circle(ball.x, ball.y, 15)});
    graphics.print({text: 'Player 1: ' + score1, position: [-(width/2-20) , -(height/2-20)]});
    graphics.print({text: 'Player 2: ' + score2, position: [width/2 + 20 , -(height/2-20)]});
};