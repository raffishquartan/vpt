package uk.co.bristlecone.voltdb.wrapgen.console;

public class SourceFileResult {
  public static enum Result {
    NOT_A_PROC, INVALID_PROC, RUNNER_WRITTEN, RUNNER_OVERWRITTEN, RUNNER_WRITE_ERROR
  }

  private final String sourceIdentifier;
  private final Result status;

  private SourceFileResult(final String sourceIdentifier, final Result status) {
    this.sourceIdentifier = sourceIdentifier;
    this.status = status;
  }

  public static SourceFileResult of(final String sourceIdentifier, final Result status) {
    return new SourceFileResult(sourceIdentifier, status);
  }

  public String identifier() {
    return sourceIdentifier;
  }

  public Result status() {
    return status;
  }
}
