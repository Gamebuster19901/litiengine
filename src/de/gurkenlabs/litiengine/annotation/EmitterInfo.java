/***************************************************************
 * Copyright (c) 2014 - 2015 , gurkenlabs, All rights reserved *
 ***************************************************************/
package de.gurkenlabs.litiengine.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc
/**
 * The Interface EmitterInfo.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EmitterInfo {

  /**
   * Activate on init.
   *
   * @return true, if successful
   */
  boolean activateOnInit() default true;

  /**
   * The time to live in milliseconds.
   *
   * @return the int
   */
  int emitterTTL() default 0;

  /**
   * Max particles.
   *
   * @return the int
   */
  int maxParticles();

  boolean particleFade() default true;

  int particleMaxTTL() default 0;

  int particleMinTTL() default 0;

  /**
   * Update particle 30 times per second.
   *
   * @return
   */
  int particleUpdateDelay() default 1000 / 30;

  /**
   * Spawn amount.
   *
   * @return the int
   */
  int spawnAmount();

  /**
   * Spawn rate.
   *
   * @return the int
   */
  int spawnRate() default 40;
}
