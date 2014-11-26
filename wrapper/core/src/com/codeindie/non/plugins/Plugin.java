package com.codeindie.non.plugins;

public abstract class Plugin {
    public abstract String name();
    public String author() { return "Unknown"; }
    public String license() { return "Public Domain"; }
    public String description() { return "Not specified."; }
    public String[] dependencies() { return null; }
    public void load() { }
    public void dispose() { }
}
