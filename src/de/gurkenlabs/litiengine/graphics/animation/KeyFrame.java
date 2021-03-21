package de.gurkenlabs.litiengine.graphics.animation;

import com.google.gson.annotations.Expose;

/**
 * The {@code Keyframe} class defines the relation between a particular sprite index and its animation duration.
 */
public class KeyFrame {
  @Expose private int duration;
  @Expose private int index;

  KeyFrame(final int duration, final int sprite) {
    this.duration = duration;
    this.index = sprite;
  }

  public int getDuration() {
    return this.duration;
  }

  public int getIndex() {
    return this.index;
  }

  public void setDuration(final int duration) {
    this.duration = duration;
  }

  public void setIndex(final int sprite) {
    this.index = sprite;
  }
}
