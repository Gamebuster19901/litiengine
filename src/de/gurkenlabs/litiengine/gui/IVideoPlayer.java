package de.gurkenlabs.litiengine.gui;

import java.awt.Component;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;

import de.gurkenlabs.litiengine.resources.VideoResource;

interface IVideoPlayer {
  void dispose();
  
  boolean isStatusUnknown();
  boolean isReady();
  boolean isErrored();
  boolean isPlaying();
  boolean isPaused();
  boolean isBuffering();
  boolean isStopped();
  Status getStatus();
  
  Throwable getError();
  double getBalance();
  Duration getBufferProgressTime();
  int getCurrentCount();
  double getCurrentRate();
  Duration getCurrentTime();
  double getRate();
  Duration getStartTime();
  Duration getStopTime();
  Duration getTotalDuration();
  double getVolume();
  void pause();
  void setVideo(VideoResource video);
  void setVideo(URL url) throws IOException;
  void play(VideoResource video);
  void play(URL url) throws IOException;
  void play();
  void seek(Duration seekTime);
  void setBalance(double value);
  void setRate(double value);
  void setStartTime(Duration value);
  void setStopTime(Duration value);
  void setVolume(double value);
  void stop();
  Component getPanel();
  
  public static enum Status {
    DISPOSED,
    ERRORED, //HALTED
    PAUSED,
    PLAYING,
    READY,
    STALLED,
    STOPPED,
    UNKNOWN
  }
  
}
