package de.gurkenlabs.litiengine.video;


import static de.gurkenlabs.litiengine.video.Player.State.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import com.zakgof.velvetvideo.IDemuxer;
import com.zakgof.velvetvideo.IVelvetVideoLib;
import com.zakgof.velvetvideo.IVideoDecoderStream;
import com.zakgof.velvetvideo.IVideoFrame;
import com.zakgof.velvetvideo.impl.VelvetVideoLib;

final class VideoPlayer implements Player{
  
  private volatile Demuxer demuxer;
  
  VideoPlayer(String uri) {
    if(uri == null) {
      return;
    }
    setVideo(uri);
  }
  
  synchronized void setVideo(String uri) {
    if(uri == null) {
      demuxer = null;
    }
    demuxer = new Demuxer(uri);
  }
  
  @Override
  public synchronized void play() {
    if(playerValid()) {
      demuxer.play();
    }
  }
  
  @Override
  public synchronized boolean isPlaying() {
    if(playerValid()) {
      return demuxer.isPlaying();
    }
    return false;
  }
  
  @Override
  public synchronized void pause() {
    if(playerValid()) {
      demuxer.pause();
    }
  }
  
  @Override
  public synchronized boolean isPaused() {
    if(playerValid()) {
      return demuxer.isPaused();
    }
    return false;
  }
  
  @Override
  public synchronized void stop() {
    if(playerValid()) {
      demuxer.stop();
    }
  }
  
  @Override
  public synchronized boolean isInitialized() {
    if(playerValid()) {
      return demuxer.isInitialized();
    }
    return false;
  }
  
  @Override
  public synchronized boolean isStopped() {
    if(playerValid()) {
      return demuxer.isStopped();
    }
    return false;
  }
  
  @Override
  public synchronized boolean isErrored() {
    if(playerValid()) {
      return demuxer.isErrored();
    }
    return false;
  }
  
  public synchronized State getState() {
    if(playerValid()) {
      return demuxer.getState();
    }
    return UNINITIALIZED;
  }
  
  public synchronized void setPlaybackRate(double rate) {
    if(playerValid()) {
      demuxer.setPlaybackRate(rate);
    }
  }
  
  public synchronized void seek(Duration duration) {
    if(playerValid()) {
      demuxer.seek(duration);
    }
  }
  
  public synchronized Image getImage() {
    if(playerValid()) {
      return demuxer.getImage();
    }
    return null;
  }
  
  synchronized boolean playerValid() {
    return demuxer != null;
  }
  
  static final class Demuxer implements Runnable, Player {
    static final Logger log = Logger.getLogger(Demuxer.class.getName());
    static final IVelvetVideoLib VELVET = VelvetVideoLib.getInstance();
    static final Duration NONE = Duration.ofSeconds(-1);
    
    private final String videoLocation;
    private final IDemuxer demuxer;
    
    private final Lock imageLock = new ReentrantLock();
    
    //private volatile double playbackSpeed = 1.0; //TODO
    
    private volatile State state = UNINITIALIZED;
    private volatile BufferedImage image; 
    private volatile Duration seek = NONE;
    
    Demuxer (String uri) {
      videoLocation = uri;
      demuxer = VELVET.demuxer(new File(videoLocation));
      initialize();
    }
    
    public void play() {
      switch(getState()) {
        case UNINITIALIZED:
        case STOPPED:
          initialize();
          break;
        case PAUSED:
          //play
          break;          
        case PLAYING:
          log.warning("Attempted to play video that is already playing!");
          break;
        case ERRORED:
          throw new MediaException("Media player is in an errored state!");
      }
    }

    @Override
    public void run() {
      IVideoDecoderStream videoStream = demuxer.videoStream(0);
      IVideoFrame videoFrame;
      
      loop:
      while(true){
        
        switch(getState()) {
          case PLAYING:
            break;
          case PAUSED:
            continue loop;
          case ERRORED:
          case STOPPED:
            break loop;
          case UNINITIALIZED:
            throw new IllegalStateException("Uninitialized player is playing?! This should never occur!");
        }
        
        videoFrame = videoStream.nextFrame();
        if(videoFrame == null) {
          break;
        }
        
        try {
          Duration seek = getSeek();
          if(seek != NONE) {
            if(seek.compareTo(Duration.ZERO) >= 0) {
              videoStream.seekNano(seek.toNanos());
            }
            else {
              log.warning("Cannot seek to a negative time (" + seek.toString() + ")!");
            }
            seek(NONE);
          }
          if (imageLock.tryLock(1, TimeUnit.SECONDS)) { //don't overwrite the current frame if it is being read
            image = videoFrame.image();
            imageLock.unlock();
          }
        }
        catch(Throwable t) {
          setState(ERRORED);
          throw new MediaException(t);
        }
      }
      
      setState(STOPPED);
    }
    
    synchronized Image getImage() {
      try {
        if (imageLock.tryLock(1, TimeUnit.SECONDS)) {
          Image image = this.image.getScaledInstance(this.image.getWidth(), this.image.getHeight(), Image.SCALE_DEFAULT);
          imageLock.unlock();
          return image;
        }
        else {
          setState(ERRORED);
          throw new TimeoutException("Took too long to aquire lock for image! (" + videoLocation + ")");
        }
      }
      catch (Throwable t) {
        setState(ERRORED);
        throw new MediaException(t);
      }
    }
    
    private void initialize() {
      if(getState() == ERRORED) {
        return;
      }
      Thread demuxThread = new Thread(this);
      demuxThread.setName("demux " + videoLocation);
      demuxThread.setPriority(3);
      demuxThread.setDaemon(true);
      demuxThread.start();
      setState(PAUSED);
    }

    @Override
    public void pause() {
      setState(PAUSED);
    }
    
    @Override
    public void stop() {
      setState(STOPPED);
    }
    
    public boolean isInitialized() {
      return getState() != UNINITIALIZED;
    }

    @Override
    public boolean isPlaying() {
      return getState() == PLAYING;
    }
    
    @Override
    public boolean isPaused() {
      return getState() == PAUSED;
    }
    
    @Override
    public boolean isStopped() {
      return getState() == STOPPED;
    }
    
    @Override
    public boolean isErrored() {
      return getState() == ERRORED;
    }
    
    @Override
    public State getState() {
      synchronized(this.state) {
        return state;
      }
    }
    
    private void setState(State state) {
      synchronized(this.state) {
        if(this.state != ERRORED) {
          this.state = state;
        }
      }
    }

    @Override
    @Deprecated
    public synchronized void setPlaybackRate(double rate) {
      //TODO blocked upstream
    }
    
    @Override
    public void seek(Duration duration) {
      synchronized(seek) {
        this.seek = seek;
      }
    }
    
    private Duration getSeek() {
      synchronized(seek) {
        return this.seek;
      }
    }
    
  }
  
}
