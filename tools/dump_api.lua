-- rename this file to main.lua
-- and place it to your assets/ directory

function dump(prefix, x)
	for k,v in pairs(x) do
		if type(v) == "table" then
			dump(prefix .. "." .. k, v)
		else
			print(prefix .. "." .. k)
		end
	end 
end

dump("non", non)

non.system.quit()