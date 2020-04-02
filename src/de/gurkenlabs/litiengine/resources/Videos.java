package de.gurkenlabs.litiengine.resources;

import java.net.URL;

import de.gurkenlabs.litiengine.video.VideoComponent;

public class Videos extends ResourcesContainer<VideoComponent>{

  Videos(){}
  
  public VideoComponent load(VideoResource video) {
    return load(video, false);
  }
  
  public VideoComponent load(VideoResource video, boolean play) {
    VideoComponent VideoComponent = new VideoComponent(video, play);
    this.add(video.getName(), VideoComponent);
    return VideoComponent;
  }
  
  @Override
  protected VideoComponent load(URL resourceName) {
    return new VideoComponent(resourceName);
  }

}
