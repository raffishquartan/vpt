package uk.co.bristlecone.vpt.runner.console;

import uk.co.bristlecone.vpt.runner.console.impl.DirSourceFileProvider;
import uk.co.bristlecone.vpt.runner.console.impl.SourceFilePartitioner;

public class ConsoleWrapgenController {
  public ConsoleWrapgenController() {
    // intentionally empty
  }

  public void run(final DirSourceFileProvider provider, final SourceFileProcessor sourceFileProcessor) {

    new SourceFilePartitioner(provider).freshStreamOfAllSourceFiles().map(sourceFileProcessor::process)
        .forEach(SourceFileResultProcessor::process);
  }
}
