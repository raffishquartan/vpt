package uk.co.bristlecone.voltdb.wrapgen.builder;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.bristlecone.voltdb.wrapgen.source.SourceFile;

public class WrapgenController {
  public static final Logger LOGGER = LoggerFactory.getLogger(WrapgenController.class);

  /**
   * Transforms a stream of {@link SourceFile}'s into {@link RunnerBuilder}'s. Note that this filters out and logs any
   * SourceFile's which do not specify a valid VoltDB stored procedure, so the <code>sourceFiles</code> stream is
   * terminated and a new stream of <code>RunnerBuilder</code>'s returned.
   *
   * @param sourceFiles a stream of source files, those containing valid VoltDB procs are converted into RunnerBuilder's
   * @param packageNamer a function mapping proc package name to runner package name
   * @param runnerName a function mapping proc class name to runner class name
   * @return a stream of RunnerBuilder objects for consumption by the caller
   */
  public static Stream<RunnerBuilder> transform(final Stream<SourceFile> sourceFiles,
      final Function<String, String> packageNamer, final Function<String, String> runnerNamer) {
    final Map<Boolean, List<SourceFile>> validAndInvalidSourceFiles = sourceFiles
        .collect(Collectors.partitioningBy(SourceFile::isValidVoltProcedure));
    LOGGER.info("Valid VoltDB stored procedures (n={}):", validAndInvalidSourceFiles.get(true)
        .size());
    logSourceFiles(validAndInvalidSourceFiles.get(true));
    LOGGER.info("Invalid VoltDB stored procedures (n={}):", validAndInvalidSourceFiles.get(false)
        .size());
    logSourceFiles(validAndInvalidSourceFiles.get(false));
    return validAndInvalidSourceFiles.get(true)
        .stream()
        .map(WrapgenController::buildProcData)
        .map(pd -> new RunnerBuilder(pd, packageNamer, runnerNamer));
  }

  private static void logSourceFiles(final Iterable<SourceFile> sourceFilesToLog) {
    for(final SourceFile sf : sourceFilesToLog) {
      LOGGER.info("{}.{}", sf.packageName(), sf.voltProcedureName());
    }
  }

  private static ProcData buildProcData(final SourceFile sf) {
    return new ProcData.Builder().setClassJavaDoc(sf.classJavaDoc())
        .setName(sf.voltProcedureName())
        .setPackageName(sf.packageName())
        .setParameters(sf.runMethodParameters())
        .setReturnType(sf.runMethodReturnType())
        .build();
  }
}
