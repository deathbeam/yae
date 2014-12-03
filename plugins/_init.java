package com.codeindie.non.plugins;

public class _init {
    static {
        new Non();
        new Audio();
        new Graphics();
        new Input();
        new Lights();
        new Math();
        new Network();
        new Physics();
        new Tiled();
    }
    
    public void genInit(String[] plugins) {
        String text = "package com.codeindie.non.plugins; public class _init { static { ";
        for(String plug: plugins) text += "new " + plug + "(); ";
        text += "} }";
    }
}