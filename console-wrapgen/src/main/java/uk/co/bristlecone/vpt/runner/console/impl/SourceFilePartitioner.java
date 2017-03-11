package uk.co.bristlecone.vpt.runner.console.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.stream.Stream;

import uk.co.bristlecone.vpt.runner.console.SourceFileProvider;
import uk.co.bristlecone.vpt.source.SourceFile;

public class SourceFilePartitioner {
  SourceFileProvider sourceFiles;

  public SourceFilePartitioner(final SourceFileProvider sourceFiles) {
    checkNotNull(sourceFiles);
    this.sourceFiles = sourceFiles;
  }

  public Stream<SourceFile> freshStreamOfAllSourceFiles() {
    return sourceFiles.freshSourceFileStream();
  }

  public Stream<SourceFile> freshStreamOfNotProcSourceFiles() {
    return sourceFiles.freshSourceFileStream().filter(sf -> !sf.isIntendedVoltProcedure());
  }

  public Stream<SourceFile> freshStreamOfValidProcSourceFiles() {
    return sourceFiles.freshSourceFileStream().filter(sf -> sf.isValidVoltProcedure());
  }

  public Stream<SourceFile> freshStreamOftreamInvalidProcSourceFiles() {
    return sourceFiles.freshSourceFileStream().filter(sf -> sf.isIntendedVoltProcedure() && !sf.isValidVoltProcedure());
  }
}
