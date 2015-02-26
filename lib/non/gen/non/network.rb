module Network
    java_import 'non.ModuleHandler'
    Module = ModuleHandler.get("network")
    
    def self.client
        Module.client()
    end
    
    def self.server
        Module.server()
    end
    
    def self.buffer(data = nil)
        data == nil ? Module.buffer() : Module.buffer(data)
    end
end