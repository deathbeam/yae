var Class = {
    extend: function (properties) {
        var superProto = this.prototype || Class;
        var proto = Object.create(superProto);
        Class.copyTo(properties, proto);
        
        var constr = proto.constructor;
        if (constr != undefined) {
            if (!(constr instanceof Function)) {
                throw new Error("'constructor' must be method and not variable!");
            }
        }
        
        constr.prototype = proto;
        constr.super = superProto;
        constr.extend = this.extend;
        return constr;
    },

    copyTo: function(source, target) {
        Object.getOwnPropertyNames(source).forEach(function(propName) {
            Object.defineProperty(target, propName,
                Object.getOwnPropertyDescriptor(source, propName));
        });
        return target;
    }
};

function require(module) {
    if (module.substr(0, module.indexOf(".")).equals("non")) {
        return Packages.non.modules.Module.get(module.substr(module.indexOf(".") + 1, module.length));
    }
    
    var filePath = module.replace(".", Packages.java.io.File.separator) + ".js";
    var script = "(function() {";
    
    try { 
        var toRead = new Packages.java.util.Scanner(non.file(filePath).read());
        
        for (var line; toRead.hasNextLine() && (line = toRead.nextLine()) != null; ) { 
            script += line + "\n";
        }
        
        script += "return this; }).call(this);";
        
        toRead.close();
    } catch (e) { 
        non.log("non.js/require",e.toString());
    }
    return eval(script);
}