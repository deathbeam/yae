var graphics = require('non.graphics');
var gui = require('non.gui');
var math = require('non.math');
var keyboard = require('non.keyboard');

var textOutput = "Use TAB to switch between components. Enter text here...";
var showArea = true;

gui.renderer = {
    graphics: graphics,
    
    focus: function(id, x, y, width, height) {
        var color = graphics.getTint();
        graphics.tint('yellow');
        graphics.fill({shape: math.rectangle(x - 1, y - 1, width + 2, height + 2)});
        graphics.tint(color);
    },
    
    label: function(id, text, x, y) {
        var color = graphics.getTint();
        graphics.tint('white');
        graphics.print({text: text, position: [x, y]});
        graphics.tint(color);
    },
        
    button: function(id, state, text, x, y, width, height) {
        var color = graphics.getTint();
        
        if (state == 'hot') {
            graphics.tint('red');
        } else if (state == 'active') {
            graphics.tint('blue');
        } else {
            graphics.tint('darkgray');
        }
        
        var bounds = graphics.measureText(text, width);
        graphics.fill({mode: 'fill', shape: math.rectangle(x, y, width, height)});
        graphics.tint('magenta');
        graphics.print({
            text:text, wrap: width, align: 'center',
            position:[x + ((width - bounds.width) /2) - 8, y + ((height - bounds.height) /2)]
        });
        graphics.tint(color);
    },
    
    text: function(id, text, x, y, width, height) {
        color = graphics.getTint();
        graphics.tint('darkgray');
        graphics.fill({mode: 'fill', shape: math.rectangle(x, y, width, height)});
        graphics.tint('lightgray');
        graphics.print({text: text, position: [x + 5, y + 5], wrap: width - 20});
        graphics.tint(color);
    }
};

non.draw = function() {
    graphics.clear('black');
    
    if (gui.button('active', {text: 'Press to show or hide text area', position: [100,100], size: [120, 45]})) {
        showArea = !showArea;
        keyboard.setVisible(showArea);
    }
    
    if (showArea) {
        textOutput = gui.text(textOutput, {
            position: [100,150], 
            size: [non.getWidth() - 200, non.getHeight() - 250],
            onTouch: function() { keyboard.show(); }
        });
    }
};