NON.network = {}
NON.network.module = NON_MODULE:get("network")
  
function NON.network.client()
  return NON.network.module:client()
end
  
function NON.network.server()
  return NON.network.module:server()
end
  
function NON.network.buffer(data)
  if data == nil then
    return NON.network.module:buffer()
  else
    return NON.network.module:buffer(data)
  end
end