package uk.co.bristlecone.voltdb.wrapgen.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.bristlecone.voltdb.wrapgen.console.impl.DirSourceFileProvider;
import uk.co.bristlecone.vpt.WrapgenRuntimeException;

public class ConsoleWrapgenMain {
  private static Logger LOGGER = LoggerFactory.getLogger(ConsoleWrapgenMain.class);

  private ConsoleWrapgenMain() {
    // intentionally empty
  }

  public static void main(final String[] args) {
    try {
      final Configuration config = new Configuration(args);
      if(config.showHelp()) {
        config.printHelp();
      } else if(config.showVersion()) {
        config.printVersion();
      } else {
        LOGGER.info("Execution started");
        new ConsoleWrapgenController().run(new DirSourceFileProvider(config.sourceDir()),
            new SourceFileProcessor(config));
        LOGGER.info("Execution completed");
      }
    } catch (final WrapgenRuntimeException e) {
      LOGGER.error("Aborting execution", e);
      System.exit(1);
    }
  }
}
