package de.gurkenlabs.litiengine.configuration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import de.gurkenlabs.litiengine.configuration.ConfigurationGroup.ConfigurationChangedListener;
import de.gurkenlabs.litiengine.util.ReflectionUtilities;

public class ConfigurationGroupTests {

  @Test
  public void testPropertyChangedClientConfig() {
    this.testConfigurationChanged(new ClientConfiguration());
  }

  @Test
  public void testPropertyChangedGraphicsConfig() {
    this.testConfigurationChanged(new GraphicConfiguration());
  }

  @Test
  public void testPropertyChangedSoundConfig() {
    this.testConfigurationChanged(new SoundConfiguration());
  }

  @Test
  public void testPropertyChangedInputConfig() {
    this.testConfigurationChanged(new InputConfiguration());
  }

  @Test
  public void testPropertyChangedDebugConfig() {
    this.testConfigurationChanged(new DebugConfiguration());
  }

  @Test
  public void testPropertyChangedEventValues() {
    GraphicConfiguration config = new GraphicConfiguration();
    TestConfigurationChangedListener listener = new TestConfigurationChangedListener();

    config.onChanged(listener);
    
    boolean old = config.antiAlising();
    config.setAntiAliasing(true);
    

    assertEquals("antiAliasing", listener.name);
    assertEquals(true, listener.newVal);
    assertEquals(old, listener.oldVal);
    assertEquals(config, listener.source);
    
    DisplayMode oldDisplayMode = config.getDisplayMode();
    config.setDisplayMode(DisplayMode.FULLSCREEN);
    
    assertEquals("displayMode", listener.name);
    assertEquals(DisplayMode.FULLSCREEN, listener.newVal);
    assertEquals(oldDisplayMode, listener.oldVal);
    assertEquals(config, listener.source);
  }

  @Test
  public void testReflectionBasedSetters() {
    GraphicConfiguration config = new GraphicConfiguration();
    config.setAntiAliasing(true);
    config.setColorInterpolation(true);
    config.setDisplayMode(DisplayMode.FULLSCREEN);
    config.setGraphicQuality(Quality.VERYLOW);
    config.setResolutionHeight(123);

    assertEquals(true, config.antiAlising());
    assertEquals(true, config.colorInterpolation());
    assertEquals(DisplayMode.FULLSCREEN, config.getDisplayMode());
    assertEquals(Quality.VERYLOW, config.getGraphicQuality());
    assertEquals(123, config.getResolutionHeight());
  }

  private <T extends ConfigurationGroup> void testConfigurationChanged(T instance) {
    TestConfigurationChangedListener listener = new TestConfigurationChangedListener();

    instance.onChanged(listener);

    for (Method method : ReflectionUtilities.getSetters(instance.getClass())) {
      Object value = ReflectionUtilities.getDefaultValue(method.getParameters()[0].getType());
      assertDoesNotThrow(() -> method.invoke(instance, value));
      assertNotNull(method.getName());
      assertNotNull(listener.name, method.getName());
      assertTrue(method.getName().toLowerCase().contains(listener.name.toLowerCase()), method.getName() + " == " + listener.name);
    }
  }

  class TestConfigurationChangedListener implements ConfigurationChangedListener {
    Object newVal;
    Object oldVal;
    Object source;
    String name;

    @Override
    public void configurationChanged(PropertyChangeEvent event) {
      newVal = event.getNewValue();
      name = event.getPropertyName();
      source = event.getSource();
      oldVal = event.getOldValue();
    }
  }
}
