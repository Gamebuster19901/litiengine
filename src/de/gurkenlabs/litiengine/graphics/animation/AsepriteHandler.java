package de.gurkenlabs.litiengine.graphics.animation;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gson.GsonHelper;

public class AsepriteHandler {
  
  public static Animation importAnimation(String path) throws IOException {
    return importAnimation(Paths.get(path));
  }
  
  /**
   * Imports an Aseprite animation (.json + sprite sheet).
   *
   * @param jsonPath path (including filename) to Aseprite JSON.
   * @return Animation object represented by each key frame in Aseprite sprite sheet.
   * @throws IOException if an exception occurs
   */
  public static Animation importAnimation(Path jsonPath) throws IOException {
    try {
      Reader reader = Files.newBufferedReader(jsonPath);
      
      JsonObject animation = JsonParser.parseReader(reader).getAsJsonObject();
      JsonElement frames = animation.get("frames");
      if(frames == null || frames.isJsonNull()) {
        throw new IOException(new NullPointerException("Animation " + jsonPath + " has no frames!"));
      }
      if(frames.isJsonObject()) { //asperite doesn't use json arrays like they should for the keyframes so we have to hack it in
        animation.add("frames", GsonHelper.jsonObjectToArray(frames.getAsJsonObject()));
      }
      
      System.out.println(animation.toString());
      
      return Game.gson().fromJson(animation, Animation.class);
    }
    catch(IllegalStateException e) {
      throw new IOException(e);
    }
  }
  
  /**
   * Creates the json representation of an animation object and returns it.
   * This is the public accesible function and can/should be changed to fit into the UI.
   *
   * @param animation the animation object to export
   * @throws ExportAnimationException if the export fails
   */
  public static String exportAnimation(Animation animation) throws ExportAnimationException {
    try {
      String s = Game.gson().toJson(animation);
      System.out.println(s);
      return s;
    }
    catch(Exception e) {
      throw new ExportAnimationException(e);
    }
  }
  
  public static String exportAnimationAsAsperiteFormat() {
    throw new UnsupportedOperationException("Not implemented!");
  }
  
  /**
   * Thrown to indicate an error when exporting an animation
   */
  public static class ExportAnimationException extends IOException {
    public ExportAnimationException(String message) {
      super(message);
    }
    
    public ExportAnimationException(String message, Throwable cause) {
      super(message, cause);
    }
    
    public ExportAnimationException(Throwable cause) {
      super(cause);
    }
  }
  
  /**
   * Thrown to indicate an error when importing an animation.
   */
  public static class ImportAnimationException extends IOException {
    public ImportAnimationException(String message) {
      super(message);
    }
    
    public ImportAnimationException(String message, Throwable cause) {
      super(message, cause);
    }
    
    public ImportAnimationException(Throwable cause) {
      super(cause);
    }
  }
  
}
