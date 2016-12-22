package uk.co.bristlecone.voltdb.wrapgen.builder;

import java.util.function.Function;

public class RunnerBuilder {
  private Function<String, String> packageNamer;
  private Function<String, String> runnerNamer;
  private ProcData procData;

  public RunnerBuilder(ProcData procData, Function<String, String> packageNamer, Function<String, String> runnerNamer) {
    this.packageNamer = packageNamer;
    this.runnerNamer = runnerNamer;
    this.procData = procData;
  }

  public VoltRunnerJavaSource build() {
    throw new UnsupportedOperationException("NYI");
  }
}
