package uk.co.bristlecone.voltdb.wrapgen.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SourceFileResultProcessor {
  private static final Logger LOGGER = LoggerFactory.getLogger(SourceFileResultProcessor.class);

  public static void process(final SourceFileResult sfr) {
    switch (sfr.status()) {
    case NOT_A_PROC:
      LOGGER.info(String.format("%s - not a VoltDB stored procedure, ignored", sfr.identifier()));
      break;
    case INVALID_PROC:
      LOGGER.warn(String.format("%s - invalid VoltDB stored procedure, no runner generated", sfr.identifier()));
      break;
    case RUNNER_OVERWRITTEN:
      LOGGER.info(String.format("%s - class at destination existed, backed up, runner written", sfr.identifier()));
      break;
    case RUNNER_WRITTEN:
      LOGGER.info(String.format("%s - runner written", sfr.identifier()));
      break;
    case RUNNER_WRITE_ERROR:
      LOGGER.warn(String.format("%s - error writing runner, see earlier log messages", sfr.identifier()));
      break;
    }
  }
}
