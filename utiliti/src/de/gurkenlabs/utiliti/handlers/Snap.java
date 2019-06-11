package de.gurkenlabs.utiliti.handlers;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.environment.tilemap.IMap;
import de.gurkenlabs.litiengine.util.MathUtilities;
import de.gurkenlabs.utiliti.Program;

public class Snap {
  private static final int DEFAULT_PRECISION = 2;

  public static float x(double x) {
    final IMap map = Game.world() != null && Game.world().environment() != null ? Game.world().environment().getMap() : null;
    int gridSize = map != null ? map.getTileSize().width : 1;

    return x(x, gridSize, Program.preferences().snapToGrid(), Program.preferences().snapToPixels());
  }

  protected static float x(double x, int gridSize, boolean snapToGrid, boolean snapToPixel) {
    return snap(x, gridSize, snapToGrid, snapToPixel);
  }

  public static float y(double y) {
    final IMap map = Game.world() != null && Game.world().environment() != null ? Game.world().environment().getMap() : null;
    int gridSize = map != null ? map.getTileSize().height : 1;

    return y(y, gridSize, Program.preferences().snapToGrid(), Program.preferences().snapToPixels());
  }

  protected static float y(double y, int gridSize, boolean snapToGrid, boolean snapToPixel) {
    return snap(y, gridSize, snapToGrid, snapToPixel);
  }

  private static float snap(double value, int gridSize, boolean snapToGrid, boolean snapToPixel) {
    double snapped = value;

    if (gridSize > 1 && snapToGrid) {
      snapped = snapToGrid(value, gridSize);
    } else if (snapToPixel) {
      snapped = snapToPixels(value);
    }

    // apply default precision
    return MathUtilities.round((float) snapped, DEFAULT_PRECISION);
  }

  private static int snapToPixels(double value) {
    return (int) Math.round(value);
  }

  private static int snapToGrid(double value, int gridSize) {
    long snapped = (Math.round((value / gridSize)) * gridSize);
    return (int) snapped;
  }
}