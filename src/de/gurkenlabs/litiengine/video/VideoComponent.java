package de.gurkenlabs.litiengine.video;

import java.awt.Graphics2D;
import java.io.UncheckedIOException;
import java.net.URL;

import javax.swing.JPanel;

import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.resources.VideoResource;

public class VideoComponent extends GuiComponent{
  JPanel panel = new JPanel();
  VideoPlayer videoPlayer;
  
  public VideoComponent() {
    super(0,0);
  }
  
  public VideoComponent(VideoResource video) throws UncheckedIOException {
    this(video, false);
  }
  
  public VideoComponent(VideoResource video, boolean play) throws UncheckedIOException {
    super(0,0);
    if(play) {
      play(video);
    }
    else {
      setVideo(video);
    }
  }
  
  public VideoComponent(URL url) throws UncheckedIOException {
    this(url, false);
  }
  
  public VideoComponent(URL url, boolean play) throws UncheckedIOException {
    super(0,0);
    if(play) {
      play(url);
    }
    else {
      setVideo(url);
    }
  }
  
  public void setVideo(VideoResource video) throws UncheckedIOException {
    setVideo(video.getURI());
  }
  
  private void play(VideoResource video) throws UncheckedIOException {
    setVideo(video);
    play();
  }
  
  public void setVideo(URL url) throws UncheckedIOException {
    setVideo(url.toString());
  }
  
  public void play(URL url) throws UncheckedIOException {
    setVideo(url);
    play();
  }
  
  public void setVideo(String url) throws UncheckedIOException {
    videoPlayer = new VideoPlayer(url);
  }
  
  public void play() {
    videoPlayer.play();
  }
  
  public JPanel getPanel() {
    return panel;
  }
  
  public void render(Graphics2D g) {
    ImageRenderer.render(g, videoPlayer.getImage(), this.getX(), this.getY());
  }

}
