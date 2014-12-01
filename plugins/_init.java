package com.codeindie.non.plugins;

public class _init {
    static {
        Plugins.add(new Non()); // Core plugin, do not remove!
        Plugins.add(new Audio());
        Plugins.add(new Graphics());
        Plugins.add(new Input());
        Plugins.add(new Lights());
        Plugins.add(new Math());
        Plugins.add(new Network());
        Plugins.add(new Physics());
		Plugins.add(new Tiled());
    }
}