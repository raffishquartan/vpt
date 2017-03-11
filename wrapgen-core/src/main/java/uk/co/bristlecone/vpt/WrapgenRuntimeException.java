package uk.co.bristlecone.vpt;

/**
 * A catch-all (haha) exception class for runtime errors in WrapGen.
 * 
 * @author christo
 */
public class WrapgenRuntimeException extends RuntimeException {
  /**
   * serialVersionUID not used
   */
  private static final long serialVersionUID = 1L;

  public WrapgenRuntimeException() {
    super();
  }

  public WrapgenRuntimeException(String message) {
    super(message);
  }

  public WrapgenRuntimeException(Throwable t) {
    super(t);
  }

  public WrapgenRuntimeException(String message, Throwable t) {
    super(message, t);
  }
}
