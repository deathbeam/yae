speed = 3
ball = 
  x: 0
  y: 0
  vx: -speed
  vy: speed
pad1 = 0
pad2 = 0
score1 = 0
score2 = 0
width = 800
height = 600

non.resize = (w, h) ->
  width = w
  height = h
  return

non.update = (dt) ->
  if input.keyboard.isDown('Q')
    pad1 += 7
  if input.keyboard.isDown('W')
    pad1 -= 7
  i = 0
  while i <= 1
    if input.touch.isDown(i) and input.touch.getX(i) < width / 2
      pad1 = input.touch.getY(i) - height / 2
    i++
  if pad1 < -(height / 2 - 50)
    pad1 = -(height / 2 - 50)
  if pad1 > height / 2 - 50
    pad1 = height / 2 - 50
  if input.keyboard.isDown('O')
    pad2 += 7
  if input.keyboard.isDown('P')
    pad2 -= 7
  i = 0
  while i <= 1
    if input.touch.isDown(i) and input.touch.getX(i) > width / 2
      pad2 = input.touch.getY(i) - height / 2
    i++
  if pad2 < -(height / 2 - 50)
    pad2 = -(height / 2 - 50)
  if pad2 > height / 2 - 50
    pad2 = height / 2 - 50
  ball.x += ball.vx
  ball.y += ball.vy
  if math.abs(ball.y) >= height / 2 - 15
    ball.vy = -ball.vy
  if ball.x < -(width / 2 - 75) and ball.x > -(width / 2 - 50) and math.abs(pad1 - ball.y) < 60
    speed += 0.2
    ball.x = -(width / 2 - 75)
    ball.vx = speed
    ball.vy = ball.vy * 0.5 + math.random(-10, 10) / 20 * speed
  if ball.x < -(width / 2 + 15)
    ball.x = 0
    ball.vx = -ball.vx
    score2++
  if ball.x > width / 2 - 75 and ball.x < width / 2 - 50 and math.abs(pad2 - ball.y) < 60
    speed += 0.5
    ball.x = width / 2 - 75
    ball.vx = -speed
    ball.vy = ball.vy * 0.8 + math.random(-10, 10) / 10 * speed
  if ball.x > width / 2 + 15
    ball.x = 0
    ball.vx = -ball.vx
    score1++
  if score1 > 6 or score2 > 6
    score1 = 0
    score2 = 0
    speed = 3
  return

non.draw = ->
  graphics.clear '#1A1A1A'
  graphics.tint '#E8ACE5'
  graphics.translate width / 2, height / 2
  graphics.fill 'fill', math.line(0, -height / 2, 0, height / 2)
  graphics.fill 'fill', math.rectangle(-(width / 2 - 45), pad1 - 50, 20, 100)
  graphics.fill 'fill', math.rectangle(width / 2 - 60, pad2 - 50, 20, 100)
  graphics.fill 'fill', math.circle(ball.x, ball.y, 15)
  graphics.print score1, -(width / 2 - 20), -(height / 2 - 20)
  graphics.print score2, width / 2 - 30, -(height / 2 - 20)
  return