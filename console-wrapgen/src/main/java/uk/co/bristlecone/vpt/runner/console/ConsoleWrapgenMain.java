package uk.co.bristlecone.vpt.runner.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.bristlecone.vpt.VptRuntimeException;
import uk.co.bristlecone.vpt.runner.SourceFileProcessor;
import uk.co.bristlecone.vpt.runner.console.impl.ConsoleConfiguration;
import uk.co.bristlecone.vpt.runner.console.impl.DirSourceFileProvider;

public class ConsoleWrapgenMain {
  private static Logger LOGGER = LoggerFactory.getLogger(ConsoleWrapgenMain.class);

  private ConsoleWrapgenMain() {
    // intentionally empty
  }

  public static void main(final String[] args) {
    try {
      final ConsoleConfiguration config = new ConsoleConfiguration(args);
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
    } catch (final VptRuntimeException e) {
      LOGGER.error("Aborting execution", e);
      System.exit(1);
    }
  }
}
