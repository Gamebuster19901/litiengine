package de.gurkenlabs.litiengine.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import org.junit.jupiter.api.Test;

import de.gurkenlabs.litiengine.entities.Rotation;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.io.ImageSerializer;

public class ImagingTests {

  @Test
  public void testSubImage() {
    BufferedImage image = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag.png");
    BufferedImage[][] subImages = Imaging.getSubImages(image, 1, 2);

    assertEquals(2, subImages[0].length);
    assertEquals(15, subImages[0][0].getWidth());
    assertEquals(16, subImages[0][0].getHeight());
    assertEquals(15, subImages[0][1].getWidth());
    assertEquals(16, subImages[0][1].getHeight());
  }

  @Test
  public void testHorizontalFlip() {
    BufferedImage image = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag.png");
    BufferedImage flippedReferenceImage = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag-flip-hor.png");
    int[] expectedPixels = ((DataBufferInt) flippedReferenceImage.getData().getDataBuffer()).getData();

    BufferedImage flipped = Imaging.horizontalFlip(image);
    int[] actualPixels = ((DataBufferInt) flipped.getData().getDataBuffer()).getData();

    assertArrayEquals(expectedPixels, actualPixels);
  }

  @Test
  public void testVerticalFlip() {
    BufferedImage image = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag.png");
    BufferedImage flippedReferenceImage = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag-flip-vert.png");
    int[] expectedPixels = ((DataBufferInt) flippedReferenceImage.getData().getDataBuffer()).getData();

    BufferedImage flipped = Imaging.verticalFlip(image);
    int[] actualPixels = ((DataBufferInt) flipped.getData().getDataBuffer()).getData();

    assertArrayEquals(expectedPixels, actualPixels);
  }

  @Test
  public void testRotation() {
    BufferedImage image = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag.png");
    BufferedImage expectedRotate90 = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag-90.png");
    BufferedImage expectedRotate180 = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag-180.png");
    BufferedImage expectedRotate270 = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag-270.png");
    int[] expectedPixels90 = ((DataBufferInt) expectedRotate90.getData().getDataBuffer()).getData();
    int[] expectedPixels180 = ((DataBufferInt) expectedRotate180.getData().getDataBuffer()).getData();
    int[] expectedPixels270 = ((DataBufferInt) expectedRotate270.getData().getDataBuffer()).getData();

    BufferedImage rotated90 = Imaging.rotate(image, Rotation.ROTATE_90);
    BufferedImage rotated180 = Imaging.rotate(image, Rotation.ROTATE_180);
    BufferedImage rotated270 = Imaging.rotate(image, Rotation.ROTATE_270);

    int[] actualPixels90 = ((DataBufferInt) rotated90.getData().getDataBuffer()).getData();
    int[] actualPixels180 = ((DataBufferInt) rotated180.getData().getDataBuffer()).getData();
    int[] actualPixels270 = ((DataBufferInt) rotated270.getData().getDataBuffer()).getData();

    assertArrayEquals(expectedPixels90, actualPixels90);
    assertArrayEquals(expectedPixels180, actualPixels180);
    assertArrayEquals(expectedPixels270, actualPixels270);
  }

  @Test
  public void testScaling() {
    BufferedImage image = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag.png");
    BufferedImage expectedx2 = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag-scale-x2.png");
    BufferedImage expectedStreched = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag-stretch.png");
    int[] expectedPixelsx2 = ((DataBufferInt) expectedx2.getData().getDataBuffer()).getData();
    int[] expectedPixelsStreched = ((DataBufferInt) expectedStreched.getData().getDataBuffer()).getData();

    BufferedImage scaledx2 = Imaging.scale(image, 2.0);
    BufferedImage scaledMaxDouble = Imaging.scale(image, 60);
    BufferedImage stretched = Imaging.scale(image, 30, 32, false);

    int[] actualPixelsx2 = ((DataBufferInt) scaledx2.getData().getDataBuffer()).getData();
    int[] actualPixelsMaxDouble = ((DataBufferInt) scaledMaxDouble.getData().getDataBuffer()).getData();
    int[] actualPixelsStreched = ((DataBufferInt) stretched.getData().getDataBuffer()).getData();

    assertArrayEquals(expectedPixelsx2, actualPixelsx2);
    assertArrayEquals(expectedPixelsx2, actualPixelsMaxDouble);
    assertArrayEquals(expectedPixelsStreched, actualPixelsStreched);
  }

  @Test
  public void testOpacity() {
    BufferedImage image = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag.png");
    BufferedImage expected25 = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag-opacity-25.png");
    BufferedImage expected50 = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag-opacity-50.png");
    int[] expectedPixels25 = ((DataBufferInt) expected25.getData().getDataBuffer()).getData();
    int[] expectedPixels50 = ((DataBufferInt) expected50.getData().getDataBuffer()).getData();

    BufferedImage opacity25 = Imaging.setOpacity(image, .25f);
    BufferedImage opacity50 = Imaging.setOpacity(image, .5f);

    int[] actualPixels25 = ((DataBufferInt) opacity25.getData().getDataBuffer()).getData();
    int[] actualPixels50 = ((DataBufferInt) opacity50.getData().getDataBuffer()).getData();

    assertArrayEquals(expectedPixels25, actualPixels25);
    assertArrayEquals(expectedPixels50, actualPixels50);
  }

  @Test
  public void testFlash() {
    BufferedImage image = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag.png");
    BufferedImage expectedFlash = Resources.images().get("tests/de/gurkenlabs/litiengine/util/prop-flag-flash.png");
    int[] expectedPixelsFlash = ((DataBufferInt) expectedFlash.getData().getDataBuffer()).getData();

    BufferedImage flash = Imaging.flashVisiblePixels(image, Color.RED);

    int[] actualPixelsFlash = ((DataBufferInt) flash.getData().getDataBuffer()).getData();

    assertArrayEquals(expectedPixelsFlash, actualPixelsFlash);
  }
}