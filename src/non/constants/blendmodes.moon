GL20 = non.java.require "com.badlogic.gdx.graphics.GL20"

{
  "alpha": { GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA }
  "multiplicative": { GL20.GL_DST_COLOR, GL20.GL_ZERO }
  "premultiplied": { GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA }
  "subtractive": { GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE }
  "additive": { GL20.GL_SRC_ALPHA, GL20.GL_ONE }
  "screen": { GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_COLOR }
  "replace": { GL20.GL_ONE, GL20.GL_ZERO }
}