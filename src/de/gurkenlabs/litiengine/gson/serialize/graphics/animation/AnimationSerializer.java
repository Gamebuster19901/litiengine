package de.gurkenlabs.litiengine.gson.serialize.graphics.animation;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.animation.Animation;

public class AnimationSerializer implements JsonSerializer<Animation> {

  @Override
  public JsonElement serialize(Animation src, Type typeOfSrc, JsonSerializationContext context) {
    String name = src.getName();
    int w = src.getSpritesheet().getSpriteWidth();
    int h = src.getSpritesheet().getSpriteHeight();
    
    if(src.getKeyFrameDurations().length < 1) {
      throw new IllegalStateException("Cannot serialize the animation " + name + " which has no keyframes!");
    }
    
    JsonObject animationData = new JsonObject();
    JsonObject frames = new JsonObject();
    JsonObject meta = new JsonObject();
    
    int[] keyFrameDurations = src.getKeyFrameDurations();
    int[] keyFrameIndexes = src.getKeyFrameIndexes();
    
    for(int i = 0; i < keyFrameDurations.length; i++) {
      System.out.println(src.getSpritesheet().getColumns());
      JsonObject frameData = new JsonObject();
      {
        int frameIndex = keyFrameIndexes[i];
        JsonObject dimensions = new JsonObject();  
        {
          if(frameIndex > w * h || i < 0) {
            throw new IndexOutOfBoundsException("Key frame out of bounds for the animation " + name + ": index:" + i + "frames: " + (w * h));
          }
          
          int x = frameIndex % w * w;
          int y = frameIndex / h * h;
          
          dimensions.addProperty("x", x);
          dimensions.addProperty("y", y);
          dimensions.addProperty("w", w);
          dimensions.addProperty("h", h);
        }
        
        JsonObject spriteSourceSize = new JsonObject();
        {
          spriteSourceSize.addProperty("x", 0);
          spriteSourceSize.addProperty("y", 0);
          spriteSourceSize.addProperty("w", w);
          spriteSourceSize.addProperty("h", h);
        }
        
        JsonObject sourceSize = new JsonObject();
        {
          sourceSize.addProperty("w", w);
          sourceSize.addProperty("h", h);
        }
        
        frameData.add("frame", dimensions);
        frameData.addProperty("rotated", false);
        frameData.addProperty("trimmed", false);
        frameData.add("spriteSourceSize", spriteSourceSize);
        frameData.add("sourceSize", sourceSize);
        frameData.addProperty("duration", keyFrameDurations[i]);
        frameData.addProperty("index", frameIndex);
      }
      
      
      frames.add(name + " " + i + ".png", frameData);
    }
    
    //This should always be UTILiti unless someone is serializing an animation inside their game... which would be weird
    meta.addProperty("app", Game.info().getName() + " " + Game.info().getVersion()); 
    JsonObject size = new JsonObject();
    {
      size.addProperty("w", src.getSpritesheet().getSpriteWidth() * src.getSpritesheet().getColumns());
      size.addProperty("h", src.getSpritesheet().getSpriteHeight() * src.getSpritesheet().getRows());
    }
    meta.add("size", size);
    
    
    animationData.addProperty("name", name);
    animationData.add("frames", frames);
    animationData.addProperty("randomizedStart", src.randomizedStart());
    animationData.addProperty("loop", src.isLooping());
    animationData.add("meta", meta);
    
    
    return animationData;
  }

}
