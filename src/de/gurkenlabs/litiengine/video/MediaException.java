package de.gurkenlabs.litiengine.video;

public class MediaException extends RuntimeException {

  public MediaException() {
    super();
  }
  
  public MediaException(String message) {
    super(message);
  }
  
  public MediaException(String message, Throwable cause) {
    super(message, cause);
  }
  
  protected MediaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
  
  public MediaException(Throwable cause) {
    super(cause);
  }
  
}
