require 'non/input/accelerometer'
require 'non/input/keyboard'
require 'non/input/mouse'
require 'non/input/touch'

java_import 'com.badlogic.gdx.Gdx'
java_import 'non.InputHandler'
Gdx.input.setInputProcessor(InputHandler.new)