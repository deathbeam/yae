var graphics = require('non.graphics');
var math = require('non.math');
var keyboard = require('non.keyboard');
var gui = require('non.gui');
gui.renderer = require('renderer');

var textOutput = 'Enter text here...';
var showArea = true;
var scrollR = 0;
var scrollB = 0;

non.draw = function() {
    graphics.clear(scrollR, 0, scrollB);
    
    if (gui.button({text: 'Press to show or hide text area', position: [100,100], size: [120, 45]})) {
        showArea = !showArea;
        keyboard.setVisible(showArea);
    }
    
    gui.label({
        text: 'Use TAB and SHIFT+TAB to switch to between components\n' +
              'Adjust sliders next to the text area to change background color',
        position: [225,105]
    });
    
    if (showArea) {
        textOutput = gui.text(textOutput, {
            position: [100,150], 
            size: [non.getWidth() - 200, non.getHeight() - 250],
            onTouch: function() { keyboard.show(); }
        });
        
        scrollR = gui.vscroll(scrollR, {
            max: 1, 
            position: [100 + non.getWidth() - 200, 150],
            size: [16, non.getHeight() - 250]
        });
        
        scrollB = gui.hscroll(scrollB, {
            max: 1, 
            position: [100, 150 + non.getHeight() - 250],
            size: [non.getWidth() - 200, 16]
        });
    }
};