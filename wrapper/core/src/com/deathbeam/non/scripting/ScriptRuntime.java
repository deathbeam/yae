/*
 * The MIT License
 *
 * Copyright 2014 Thomas Slusny.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.deathbeam.non.scripting;

import com.deathbeam.non.Non;
import java.util.HashMap;

public abstract class ScriptRuntime {
	public static ScriptRuntime byExtension(String ext) {
        Non.log("PluginManager", "Loading scripting language...");
		if ("js".equalsIgnoreCase(ext)) return new JavaScript();
        if ("lua".equalsIgnoreCase(ext)) return new Lua();
        // if ("coffee".equalsIgnoreCase(ext)) return new CoffeeScript();
        // if ("groovy".equalsIgnoreCase(ext)) return new Groovy();
        // if ("ts".equalsIgnoreCase(ext)) return new TypeScript();
		return null;
    }
	
    public Object invoke(String method, HashMap<String,Object> args)  { return invoke(null, method, args); }
    public abstract Object invoke(String object, String method, HashMap<String,Object> args);
    public abstract Object eval(String script);
    public abstract void put(String key, Object value);
    public abstract Object get(String key);
    
    public String merge(String object, String method, HashMap<String,Object> args, String methodJoiner, String argJoiner, String left, String right, String end) {
        String result = "";
        if(args != null) {
            Object[] set = args.keySet().toArray();
            String sArgs = (String)set[0];
            if (sArgs.length() > 1) {
            	for (int i = 1; i < sArgs.length() - 1; i++) {
            		sArgs += argJoiner + (String)set[i];
            	}
            }
            
            for(Object key: set) put((String)key, args.get(key));
            if (object != null)
                result = object + methodJoiner + method + left + sArgs + right + end;
            else
                result = method + left + sArgs + right + end;
        } else {
            if (object != null)
                result = object + methodJoiner + method + left + right + end;
            else
                result = method + left + right + end;
        }

        return result;
    } 
}