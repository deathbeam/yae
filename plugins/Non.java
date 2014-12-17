package com.codeindie.non.plugins;

public class Non extends Plugin {
    public String author()      { return "Thomas Slusny"; }
    public String license()     { return "MIT"; }
    public String description() { return "Core plugin of NON."; }
    
    public int getWidth()                          { return com.codeindie.non.Non.getWidth(); }
    public int getHeight()                         { return com.codeindie.non.Non.getHeight(); }
    public int getFPS()                            { return com.codeindie.non.Non.getFPS(); }
    public float getDelta()                        { return com.codeindie.non.Non.getDelta(); }
    public String getPlatform()                    { return com.codeindie.non.Non.getPlatform(); }
    public boolean checkPlatform(String p)         { return getPlatform().equalsIgnoreCase(p); }
    public Object config()                         { return com.codeindie.non.Non.config(); }
    public Object warning(String type, String msg) { return com.codeindie.non.Non.warning(type, msg); }
    public Object error(String type, String msg)   { return com.codeindie.non.Non.error(type, msg); }
    public Object log(String type, String msg)     { return com.codeindie.non.Non.log(type, msg); }
    public Object debug(String type, String msg)   { return com.codeindie.non.Non.debug(type, msg); }
    
    public Object ready, draw, update, resize, close, pause, resume;
}