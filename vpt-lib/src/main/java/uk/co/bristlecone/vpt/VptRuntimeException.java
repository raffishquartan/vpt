package uk.co.bristlecone.vpt;

/**
 * A catch-all (haha) exception class for runtime errors in VPT.
 *
 * @author christo
 */
public class VptRuntimeException extends RuntimeException {
  /**
   * serialVersionUID not used
   */
  private static final long serialVersionUID = 1L;

  public VptRuntimeException() {
    super();
  }

  public VptRuntimeException(final String message) {
    super(message);
  }

  public VptRuntimeException(final Throwable t) {
    super(t);
  }

  public VptRuntimeException(final String message, final Throwable t) {
    super(message, t);
  }
}
