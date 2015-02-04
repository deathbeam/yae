function require(module) {
    var filePath = module.replace(".", Packages.java.io.File.separator) + ".js";
    var script = "";
    
    try { 
        var toRead = new Packages.java.util.Scanner(non.file(filePath).read());
        
        for (var line; toRead.hasNextLine() && (line = toRead.nextLine()) != null; ) { 
            script += line + "\n";
        }
        
        toRead.close();
    } catch (e) { 
        non.log("require",e.toString());
    }
    
    return eval(script);
}