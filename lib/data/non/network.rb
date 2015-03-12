class Network
    java_import 'non.ModuleHandler'

    def initialize
        @module = ModuleHandler.get("network")
    end
    
    def client
        @module.client()
    end
    
    def server
        @module.server()
    end
    
    def buffer(data = nil)
        data == nil ? @module.buffer() : @module.buffer(data)
    end
end