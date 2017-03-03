package uk.co.bristlecone.voltdb.wrapgen.console;

import java.util.stream.Stream;

import uk.co.bristlecone.voltdb.wrapgen.source.SourceFile;

/**
 * Provides a fresh stream of source files from where, e.g. a directory, archive, in-memory. These streams are created
 * on-demand using parameters passed to the constructor.
 * 
 * @author christo
 */
public interface SourceFileProvider {
  public Stream<SourceFile> freshSourceFileStream();
}
