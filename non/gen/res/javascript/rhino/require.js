function require(module) {
    if (module.substr(0, module.indexOf(".")).equals("non")) {
        return Packages.non.Non.script.getModule(module.substr(module.indexOf(".") + 1, module.length));
    }
    
    var filePath = module.replace(".", Packages.java.io.File.separator) + ".js";
    var script = "(function() {";
    var toRead = new Packages.java.util.Scanner(non.file(filePath).read());
        
    for (var line; toRead.hasNextLine() && (line = toRead.nextLine()) != null; ) { 
        script += line + "\n";
    }
        
    script += "return this; }).call(this);";
    toRead.close();
    return eval(script);
}