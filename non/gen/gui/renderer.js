this.focus = function(id, x, y, width, height) {
    var color = graphics.getTint();
    graphics.tint(1, 1, 1, 0.4);
    graphics.fill({shape: math.rectangle(x - 0.5, y - 0.5, width + 1, height + 1)});
    graphics.tint(color);
};
    
this.label = function(id, text, x, y) {
    var color = graphics.getTint();
    graphics.tint('white');
    graphics.print({text: text, position: [x, y]});
    graphics.tint(color);
};
        
this.button = function(id, state, text, x, y, width, height) {
    var color = graphics.getTint();
        
    if (state == 'hot') {
        graphics.tint('lightgray');
    } else if (state == 'active') {
        graphics.tint('black');
    } else {
        graphics.tint('darkgray');
    }
        
    var bounds = graphics.measureText(text, width);
    graphics.fill({mode: 'fill', shape: math.rectangle(x, y, width, height)});
    graphics.tint('white');
    graphics.print({
        text:text, wrap: width, align: 'center',
        position:[x + ((width - bounds.width) /2) - 8, y + ((height - bounds.height) /2)]
    });
    graphics.tint(color);
};
    
this.text = function(id, text, x, y, width, height) {
    var color = graphics.getTint();
    graphics.tint('darkgray');
    graphics.fill({mode: 'fill', shape: math.rectangle(x, y, width, height)});
    graphics.tint('lightgray');
    graphics.print({text: text, position: [x + 5, y + 5], wrap: width - 20});
    graphics.tint(color);
}
    
this.vscroll = function(id, ypos, x, y, width, height) {
    var color = graphics.getTint();
    graphics.tint('lightgray');
    graphics.fill({mode: 'fill', shape: math.rectangle(x, y, width, height)});
    graphics.tint('darkgray');
    graphics.fill({mode: 'fill', shape: math.rectangle(x, y + ypos - 8, width, 16)});
    graphics.tint(color);
};

this.hscroll = function(id, xpos, x, y, width, height) {
    var color = graphics.getTint();
    graphics.tint('lightgray');
    graphics.fill({mode: 'fill', shape: math.rectangle(x, y, width, height)});
    graphics.tint('darkgray');
    graphics.fill({mode: 'fill', shape: math.rectangle(x + xpos - 8, y, 16, height)});
    graphics.tint(color);
};