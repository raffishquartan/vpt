package uk.co.bristlecone.voltdb.wrapgen.builder;

import java.util.function.Function;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import uk.co.bristlecone.voltdb.wrapgen.runner.VoltRunner;

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
    TypeSpec runnerClassBuilder = TypeSpec.classBuilder(runnerNamer.apply(procData.name()))
        .addModifiers(Modifier.PUBLIC)
        .addAnnotation(VoltRunner.class)
        .build();
    JavaFile fileBuilder = JavaFile.builder(packageNamer.apply(procData.packageName()), runnerClassBuilder)
        .build();
    return new VoltRunnerJavaSource(fileBuilder.toString());
  }
}
