package com.deathbeam.non.plugins;

public class _init {
    public void build() {
        Plugins.add(new com.deathbeam.non.plugins.Non());
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