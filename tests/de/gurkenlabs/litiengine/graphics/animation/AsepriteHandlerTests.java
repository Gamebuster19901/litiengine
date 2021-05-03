package de.gurkenlabs.litiengine.graphics.animation;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import de.gurkenlabs.litiengine.Game;

public class AsepriteHandlerTests { 

  /**
   * Tests that Aseprite animation import works as expected when given valid input.
   */
  @Test
  public void importAsepriteAnimationTest() {
    try {
      Game.init();
      Animation animation = AsepriteHandler.importAnimation("tests/de/gurkenlabs/litiengine/graphics/animation/aseprite_test_animations/Sprite-0001.json");
    } catch (IOException e) {
      fail(e.getMessage());
    }
  } 

}