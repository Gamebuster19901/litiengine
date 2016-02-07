/***************************************************************
 * Copyright (c) 2014 - 2015 , gurkenlabs, All rights reserved *
 ***************************************************************/
package de.gurkenlabs.litiengine.graphics.particles.emitters;

import java.awt.Color;
import java.awt.geom.Point2D;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.annotation.EmitterInfo;
import de.gurkenlabs.litiengine.graphics.particles.Particle;

// TODO: Auto-generated Javadoc
/**
 * Represents snow fall.
 *
 */
@EmitterInfo(maxParticles = 5000, spawnAmount = 50, particleMinTTL = 5000, particleMaxTTL = 10000, activateOnInit = false)
public class SnowEmitter extends WeatherEmitter {

  /** The last camera focus. */
  private Point2D lastCameraFocus;

  /**
   * Creates a new Particle object.
   *
   * @return the particle
   */
  @Override
  public Particle createNewParticle() {
    final float xCoord = (float) (Math.random() * (this.getScreenDimensions().width * 2 + Game.getScreenManager().getCamera().getFocus().getX()) + (this.getScreenDimensions().width * Math.random() > 0.5 ? 1 : -1));
    final float yCoord = (float) Game.getScreenManager().getCamera().getFocus().getY() + WeatherEmitter.WeatherEffectStartingY;
    final float delta = (float) (Math.random() * 0.5);
    final float dx = -delta;
    final float dy = delta;
    final float gravityX = -0.01f;
    final float gravityY = 0.05f;
    final byte size = (byte) (Math.random() * 4);
    final int life = this.getRandomParticleTTL();

    return new Particle(xCoord, yCoord, dx, dy, gravityX, gravityY, size, size, life, new Color(255, 255, 255, 180));
  }

  /*
   * (non-Javadoc)
   *
   * @see de.gurkenlabs.liti.graphics.particles.Emitter#update()
   */
  @Override
  public void update() {
    super.update();
    if (this.lastCameraFocus != null) {
      final double cameraDeltaX = this.lastCameraFocus.getX() - Game.getScreenManager().getCamera().getFocus().getX();
      final double cameraDeltaY = this.lastCameraFocus.getY() - Game.getScreenManager().getCamera().getFocus().getY();
      this.getParticles().forEach(particle -> particle.setxCurrent(particle.getxCurrent() - (float) cameraDeltaX));
      this.getParticles().forEach(particle -> particle.setyCurrent(particle.getyCurrent() - (float) cameraDeltaY));
      this.getParticles().forEach(particle -> particle.setGravityX(particle.getGravityX() * (Math.random() > 0.5 ? 1 : -1)));
    }

    this.lastCameraFocus = Game.getScreenManager().getCamera().getFocus();
  }
}
