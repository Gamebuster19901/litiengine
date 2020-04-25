package com.gamebuster19901.test;

import java.awt.Graphics2D;

import de.gurkenlabs.litiengine.graphics.VideoRenderer;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.VideoResource;
import de.gurkenlabs.litiengine.video.VideoManager;
import de.gurkenlabs.litiengine.video.VideoManagerFactory;

public class VideoScreen extends Screen{

  VideoResource video = new VideoResource("file:///~/Desktop/video.mp4", "video");
  VideoManager videoPlayer = VideoManagerFactory.create(video);
  
  protected VideoScreen() {
    super("video");
    videoPlayer.setDimension(300, 300);
    while(!videoPlayer.isReady()) {
      System.out.println(videoPlayer.getStatus());
    }
    videoPlayer.play();
  }
  
  @Override
  public void render(Graphics2D g) {
    VideoRenderer.render(g, videoPlayer);
  }

}
