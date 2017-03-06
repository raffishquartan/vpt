package uk.co.bristlecone.voltdb.wrapgen.console;

import uk.co.bristlecone.voltdb.wrapgen.console.impl.DirSourceFileProvider;
import uk.co.bristlecone.voltdb.wrapgen.console.impl.SourceFilePartitioner;

public class ConsoleWrapgenController {
  public ConsoleWrapgenController() {
    // intentionally empty
  }

  public void run(final DirSourceFileProvider provider, final SourceFileProcessor sourceFileProcessor) {
    new SourceFilePartitioner(provider).freshStreamOfAllSourceFiles()
        .map(sourceFileProcessor::process)
        .forEach(SourceFileResultProcessor::process);
  }
}
