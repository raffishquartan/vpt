package uk.co.bristlecone.voltdb.wrapgen.console.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import uk.co.bristlecone.voltdb.wrapgen.WrapgenRuntimeException;
import uk.co.bristlecone.voltdb.wrapgen.console.SourceFileProvider;
import uk.co.bristlecone.voltdb.wrapgen.source.SourceFile;
import uk.co.bristlecone.voltdb.wrapgen.source.impl.JavaparserSourceFile;

public class DirSourceFileProvider implements SourceFileProvider {
  private Path rootDir;

  public DirSourceFileProvider(Path rootDir) {
    this.rootDir = rootDir;
    checkArgument(Files.isDirectory(rootDir), "rootDir must be a valid directory");
  }

  @Override
  public Stream<SourceFile> freshSourceFileStream() {
    try {
      return Files.find(rootDir, 999, (p, bfa) -> p.getFileName()
          .toString()
          .matches(".*\\.java") && bfa.isRegularFile(), FileVisitOption.FOLLOW_LINKS)
          .map(JavaparserSourceFile::make);
    } catch (IOException e) {
      throw new WrapgenRuntimeException(String.format("Error finding files in %s", rootDir), e);
    }
  }
}
