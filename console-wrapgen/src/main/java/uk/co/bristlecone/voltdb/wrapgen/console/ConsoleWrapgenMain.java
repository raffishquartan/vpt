package uk.co.bristlecone.voltdb.wrapgen.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.bristlecone.voltdb.wrapgen.console.impl.DirSourceFileProvider;

public class ConsoleWrapgenMain {
  private static Logger LOGGER = LoggerFactory.getLogger(ConsoleWrapgenMain.class);

  private ConsoleWrapgenMain() {
    // intentionally empty
  }

  public static void main(final String[] args) {
    LOGGER.info("Execution started");
    final Configuration config = new Configuration(args);
    new ConsoleWrapgenController().run(new DirSourceFileProvider(config.sourceDir()), new SourceFileProcessor(config));
    LOGGER.info("Execution completed");
  }
}
