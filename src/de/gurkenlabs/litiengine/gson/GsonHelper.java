package de.gurkenlabs.litiengine.gson;

import java.util.Map;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.gson.serialize.graphics.animation.AnimationSerializer;

import java.util.logging.Logger;

public class GsonHelper {
  
  private static final Logger logger = Logger.getLogger(GsonHelper.class.getSimpleName());
  
  /**
   * Converts a Json object with inner json objects into a json array. 
   * 
   * The elements of the array will have a new property called "name", which
   * will be the name of the inner object.
   * 
   * If the inner json object already has a property called "name", it will
   * be overridden.
   * 
   * If the Json object contains primitive properties, they will be added to the
   * resulting array, but as Json objects with a name property, and a value property.
   * 
   * @param jsonObject the json object to convert into an array
   * @return a json array which represents the json object
   */
  public static JsonArray jsonObjectToArray(JsonObject jsonObject) {
    
    JsonArray jsonArray = new JsonArray();
    for(Map.Entry<String, JsonElement>  entry  : jsonObject.entrySet()) {
      JsonElement e = entry.getValue();
      JsonObject obj;
      if(e.isJsonObject()) {
        obj = (JsonObject) e;
        if(obj.keySet().contains("name")) {
          logger.warning("Json object " + entry.getKey() + "\" already contains an element called \"name\", overriding it! This may cause unintended side effects!");
        }
        obj.addProperty("name" , entry.getKey());
      }
      else {
        obj = new JsonObject();
        obj.addProperty("name", entry.getKey());
        obj.add("value", entry.getValue());
      }
      jsonArray.add(obj);
    }
    
    return jsonArray;
  }
  
  /**
   * Intended for internal use only
   * 
   * Adds LITIEngine's default serializers and deserializers to a GsonBuilder
   */
  @Deprecated
  public static GsonBuilder addGsonSerializers(GsonBuilder gsonBuilder) {
    return gsonBuilder;
    //return gsonBuilder.registerTypeAdapter(Animation.class, new AnimationSerializer());
  }
  
}
