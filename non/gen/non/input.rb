require_relative 'input/accelerometer'
require_relative 'input/keyboard'
require_relative 'input/mouse'
require_relative 'input/touch'

java_import 'com.badlogic.gdx.Gdx'
java_import 'non.InputHandler'
Gdx.input.setInputProcessor(InputHandler.new)