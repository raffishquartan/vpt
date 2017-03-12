package uk.co.bristlecone.vpt.runner.console.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.bristlecone.vpt.VptRuntimeException;
import uk.co.bristlecone.vpt.runner.SourceFileProvider;
import uk.co.bristlecone.vpt.source.SourceFile;
import uk.co.bristlecone.vpt.source.impl.JavaparserSourceFile;

public class DirSourceFileProvider implements SourceFileProvider {
  private static final Logger LOGGER = LoggerFactory.getLogger(DirSourceFileProvider.class);

  private final Path rootDir;

  public DirSourceFileProvider(final Path rootDir) {
    LOGGER.info("Constructing DirSourceFileProvider for path: {}", rootDir);
    this.rootDir = rootDir;
    checkArgument(Files.isDirectory(rootDir), "rootDir must be a valid directory");
  }

  @Override
  public Stream<SourceFile> freshSourceFileStream() {
    try {
      LOGGER.info("Generating fresh source file stream");
      return Files
          .find(rootDir, 999, (p, bfa) -> p.getFileName().toString().matches(".*\\.java") && bfa.isRegularFile(),
              FileVisitOption.FOLLOW_LINKS)
          .map(JavaparserSourceFile::make);
    } catch (final IOException e) {
      throw new VptRuntimeException(String.format("Error finding files in %s", rootDir), e);
    }
  }
}
