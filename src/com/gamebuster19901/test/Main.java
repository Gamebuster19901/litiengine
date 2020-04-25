package com.gamebuster19901.test;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.video.JavaFXVideoManager;

public class Main {

  public static void main(String[] args) {
    Game.init(args);
    JavaFXVideoManager.register();
    Game.start();
    Game.screens().add(new VideoScreen());
  }
  
}
