package de.gurkenlabs.litiengine.video;

import java.time.Duration;

interface Player {
  void play();
  void pause();
  void stop();
  boolean isInitialized();
  boolean isPlaying();
  boolean isPaused();
  boolean isStopped();
  boolean isErrored();
  void setPlaybackRate(double rate);
  void seek(Duration time);
  State getState();
  
  static enum State {
    UNINITIALIZED,
    PAUSED,
    PLAYING,
    STOPPED,
    ERRORED;
  }
}
