non.network = {}
non.network.module = NON_MODULE:get("network")
  
function non.network.client()
  return non.network.module:client()
end
  
function non.network.server()
  return non.network.module:server()
end
  
function non.network.buffer(data)
  if data == nil then
    return non.network.module:buffer()
  else
    return non.network.module:buffer(data)
  end
end