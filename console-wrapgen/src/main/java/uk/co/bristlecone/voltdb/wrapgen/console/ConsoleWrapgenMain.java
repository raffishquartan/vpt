package uk.co.bristlecone.voltdb.wrapgen.console;

import uk.co.bristlecone.voltdb.wrapgen.console.impl.DirSourceFileProvider;

public class ConsoleWrapgenMain {
  private ConsoleWrapgenMain() {
    // intentionally empty
  }

  public static void main(final String[] args) {
    final Configuration config = new Configuration(args);
    new ConsoleWrapgenController().run(new DirSourceFileProvider(config.sourceDir()), new SourceFileProcessor(config));
  }
}
