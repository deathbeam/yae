local speed = 3
local ball = { x = 0, y = 0, vx = -speed, vy = speed }
local pad1 = 0
local pad2 = 0
local score1 = 0
local score2 = 0
local width = 800
local height = 600

non.resize = function(w, h)
    width = w
    height = h
end

non.update = function(dt)
    if input.keyboard:isDown("Q") then pad1 = pad1 + 7 end
    if input.keyboard:isDown("W") then pad1 = pad1 - 7 end
    
    for i = 0, 1 do
        if input.touch:isDown(i) and input.touch:getX(i) < width/2 then
            pad1 = input.touch:getY(i) - height/2
        end
    end
	
    if pad1 < -(height/2 - 50) then pad1 = -(height/2 - 50) end
    if pad1 > (height/2 - 50) then pad1 = (height/2 - 50) end
    
    if input.keyboard:isDown("O") then pad2 = pad2 + 7 end
    if input.keyboard:isDown("P") then pad2 = pad2 - 7 end

    for i = 0, 1 do
        if input.touch:isDown(i) and input.touch:getX(i) > width/2 then
            pad2 = input.touch:getY(i) - height/2
        end
    end
	
    if pad2 < -(height/2 - 50) then pad2 = -(height/2 - 50) end
    if pad2 > (height/2 - 50) then pad2 = (height/2 - 50) end
	
    ball.x = ball.x + ball.vx
    ball.y = ball.y + ball.vy
	
    if math:abs(ball.y) >= height/2 - 15 then ball.vy = -ball.vy end
	
    if ball.x < -(width/2 - 75) and ball.x > -(width/2 - 50) and math:abs(pad1 - ball.y) < 60 then
        speed = speed + 0.2
        ball.x = -(width/2 - 75)
        ball.vx = speed
        ball.vy = ball.vy * 0.5 + math:random(-10, 10) / 20 * speed
    end
	
    if ball.x < -(width/2 + 15) then
        ball.x = 0
        ball.vx = -ball.vx
        score2 = score2 + 1
    end
	
    if ball.x > width/2 - 75 and ball.x < width/2 - 50 and math:abs(pad2 - ball.y) < 60 then
        speed = speed + 0.5
        ball.x = width/2 - 75
        ball.vx = -speed
        ball.vy = ball.vy * 0.8 + math:random(-10, 10) / 10 * speed
    end
	
    if ball.x > width/2 + 15 then
        ball.x = 0
        ball.vx = -ball.vx
        score1 = score1 + 1
    end
	
    if score1 > 6 or score2 > 6 then
        score1 = 0
        score2 = 0
        speed = 3
    end
end

non.draw = function()
    graphics:clear("#1A1A1A");
    graphics:tint("#E8ACE5");
    graphics:translate(width/2, height/2);
    graphics:fill("fill", math:line(0, -height/2, 0, height/2))
    graphics:fill("fill", math:rectangle(-(width/2-45), pad1 - 50, 20, 100))
    graphics:fill("fill", math:rectangle(width/2-60, pad2 - 50, 20, 100))
    graphics:fill("fill", math:circle(ball.x, ball.y, 15))
    graphics:print(score1, -(width/2-20) , -(height/2-20))
    graphics:print(score2, width/2-30 , -(height/2-20))
end