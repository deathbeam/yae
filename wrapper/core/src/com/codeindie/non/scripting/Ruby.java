package com.codeindie.non.scripting;

import com.badlogic.gdx.files.FileHandle;
import com.codeindie.non.Utils;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Ruby extends ScriptRuntime {
    public Ruby() {
        e = new ScriptEngineManager().getEngineByName("ruby");
        ScriptEngineFactory f = e.getFactory();
        
        System.out.println( "Engine name: " +f.getEngineName() );
        System.out.println( "Engine Version: " +f.getEngineVersion() );
        System.out.println( "Language Name: " +f.getLanguageName() );
        System.out.println( "Language Version: " +f.getLanguageVersion() );
    }
    
    @Override
    public void invoke(String pack, String funct) {
        try {
            e.eval(pack + "." + funct);
        } catch (ScriptException ex) {
            Utils.log("scripting", ex.getMessage());
        }
    }
    
    @Override
    public void invoke(String pack, String funct, String args) {
        try {
            e.eval(pack + "." + funct + " " + args);
        } catch (ScriptException ex) {
            Utils.log("scripting", ex.getMessage());
        }
    }

    @Override
    public Object eval(FileHandle file) {
        try {
            e.eval(file.reader());
        } catch (ScriptException ex) {
            Utils.warning("scripting", ex.getMessage());
        }
        return null;
    }
}
