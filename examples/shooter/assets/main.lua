debug = true

-- Timers
-- We declare these here so we don't have to edit them multiple places
canShoot = true
canShootTimerMax = 0.2 
canShootTimer = canShootTimerMax
createEnemyTimerMax = 0.4
createEnemyTimer = createEnemyTimerMax

-- Player Object
player = { x = 200, y = 710, speed = 150, img = nil }
isAlive = true
score  = 0

-- Image Storage
bulletImg = nil
enemyImg = nil

-- Entity Storage
bullets = {} -- array of current bullets being drawn and updated
enemies = {} -- array of current enemies on screen

-- Collision detection
function CheckCollision(x1,y1,w1,h1,x2,y2,w2,h2)
  return x1 < x2+w2 and
         x2 < x1+w1 and
         y1 < y2+h2 and
         y2 < y1+h1
end

-- Load images on loading screen
player.img = non.graphics.newImage('plane.png')
enemyImg = non.graphics.newImage('enemy.png')
bulletImg = non.graphics.newImage('bullet.png')

-- Updating
function non.update(dt)
	if non.keyboard.isDown('escape') then
		non.system.quit()
	end

	-- Time out how far apart our shots can be.
	canShootTimer = canShootTimer - dt
	if canShootTimer < 0 then
		canShoot = true
	end

	-- Time out enemy creation
	createEnemyTimer = createEnemyTimer - dt
	if createEnemyTimer < 0 then
		createEnemyTimer = createEnemyTimerMax

		-- Create an enemy
		randomNumber = math.random(10, non.window.getWidth() - 10)
		newEnemy = { x = randomNumber, y = -10, img = enemyImg }
		table.insert(enemies, newEnemy)
	end


	-- update the positions of bullets
	for i, bullet in ipairs(bullets) do
		bullet.y = bullet.y - (250 * dt)

		if bullet.y < 0 then -- remove bullets when they pass off the screen
			table.remove(bullets, i)
		end
	end

	-- update the positions of enemies
	for i, enemy in ipairs(enemies) do
		enemy.y = enemy.y + (200 * dt)

		if enemy.y > 850 then -- remove enemies when they pass off the screen
			table.remove(enemies, i)
		end
	end

	-- run our collision detection
	-- Since there will be fewer enemies on screen than bullets we'll loop them first
	-- Also, we need to see if the enemies hit our player
	for i, enemy in ipairs(enemies) do
		for j, bullet in ipairs(bullets) do
			if CheckCollision(enemy.x, enemy.y, enemy.img:getWidth(), enemy.img:getHeight(), bullet.x, bullet.y, bullet.img:getWidth(), bullet.img:getHeight()) then
				table.remove(bullets, j)
				table.remove(enemies, i)
				score = score + 1
			end
		end

		if CheckCollision(enemy.x, enemy.y, enemy.img:getWidth(), enemy.img:getHeight(), player.x, player.y, player.img:getWidth(), player.img:getHeight()) 
		and isAlive then
			table.remove(enemies, i)
			isAlive = false
		end
	end

	if non.keyboard.isDown('left','a') then
		if player.x > 0 then -- binds us to the map
			player.x = player.x - (player.speed*dt)
		end
	elseif non.keyboard.isDown('right','d') then
		if player.x < (non.window.getWidth() - player.img:getWidth()) then
			player.x = player.x + (player.speed*dt)
		end
	end

	if non.keyboard.isDown('space', 'rctrl', 'lctrl') and canShoot then
		-- Create some bullets
		newBullet = { x = player.x + (player.img:getWidth()/2), y = player.y, img = bulletImg }
		table.insert(bullets, newBullet)
		canShoot = false
		canShootTimer = canShootTimerMax
	end

	if not isAlive and non.keyboard.isDown('r') then
		-- remove all our bullets and enemies from screen
		bullets = {}
		enemies = {}

		-- reset timers
		canShootTimer = canShootTimerMax
		createEnemyTimer = createEnemyTimerMax

		-- move player back to default position
		player.x = 50
		player.y = 710

		-- reset our game state
		score = 0
		isAlive = true
	end
end

-- Drawing
function non.draw()
	for i, bullet in ipairs(bullets) do
		non.graphics.draw(bullet.img, bullet.x, bullet.y)
	end

	for i, enemy in ipairs(enemies) do
		non.graphics.draw(enemy.img, enemy.x, enemy.y)
	end

	non.graphics.setColor(255, 255, 255)
	non.graphics.print("SCORE: " .. tostring(score), 400, 10)

	if isAlive then
		non.graphics.draw(player.img, player.x, player.y)
	else
		non.graphics.print("Press 'R' to restart", non.window.getWidth()/2-50, non.window.getHeight()/2-10)
	end

	if debug then
		fps = tostring(non.timer.getFPS())
		non.graphics.print("Current FPS: "..fps, 9, 10)
	end
end