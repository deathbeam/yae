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