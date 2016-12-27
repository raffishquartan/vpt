package uk.co.bristlecone.voltdb.wrapgen.builder.impl;

import java.util.function.Function;

import uk.co.bristlecone.voltdb.wrapgen.builder.ProcData;

/**
 * Provides methods that provide services to a RunnerBuilder, allowing it to create the full class definition.
 * 
 * @author christo
 */
public class RunnerBuilderServices {
  private Function<String, String> packageNamer;
  private Function<String, String> runnerNamer;
  private ProcData procData;

  public RunnerBuilderServices(ProcData procData, Function<String, String> packageNamer,
      Function<String, String> runnerNamer) {
    this.packageNamer = packageNamer;
    this.runnerNamer = runnerNamer;
    this.procData = procData;
  }

  public String procName() {
    return procData.name();
  }

  public String runnerName() {
    return runnerNamer.apply(procName());
  }

  public String procPackageName() {
    return procData.packageName();
  }

  public String runnerPackageName() {
    return packageNamer.apply(procPackageName());
  }

}
