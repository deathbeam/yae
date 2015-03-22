class Network
  java_import 'non.Buffer'
  
  def initialize
    @module = NON::JModule.get("network")
  end
  
  def client
    @module.client()
  end
  
  def server
    @module.server()
  end
  
  def buffer(data = nil)
    data == nil ? Buffer.new : Buffer.new(data)
  end
end