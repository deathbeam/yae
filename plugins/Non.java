package com.deathbeam.non.plugins;

public class Non extends Plugin {
    public String author()      { return "Thomas Slusny"; }
    public String license()     { return "MIT"; }
    public String description() { return "Core plugin of NON."; }
    
    public int getWidth()                        { return com.deathbeam.non.Non.getWidth(); }
    public int getHeight()                       { return com.deathbeam.non.Non.getHeight(); }
    public int getFPS()                          { return com.deathbeam.non.Non.getFPS(); }
    public float getDelta()                      { return com.deathbeam.non.Non.getDelta(); }
    public void warning(String type, String msg) { com.deathbeam.non.Non.warning(type, msg); }
    public void error(String type, String msg)   { com.deathbeam.non.Non.error(type, msg); }
    public void log(String type, String msg)     { com.deathbeam.non.Non.log(type, msg); }
    public void debug(String type, String msg)   { com.deathbeam.non.Non.debug(type, msg); }
    public void dump(Object obj)                 { com.deathbeam.non.Non.dump(obj); }
    
    public Object ready, draw, update, resize, close, pause, resume;
}