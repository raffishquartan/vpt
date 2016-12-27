package uk.co.bristlecone.voltdb.wrapgen.builder;

import java.util.function.Function;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import uk.co.bristlecone.voltdb.wrapgen.builder.impl.RunnerBuilderServices;
import uk.co.bristlecone.voltdb.wrapgen.runner.VoltRunner;

/**
 * Allows Java class definitions for VoltRunner's to be easily built from ProcData and supporting metadata
 * 
 * @author christo
 */
public class RunnerBuilder {
  private RunnerBuilderServices rbs;

  public RunnerBuilder(ProcData procData, Function<String, String> packageNamer, Function<String, String> runnerNamer) {
    rbs = new RunnerBuilderServices(procData, packageNamer, runnerNamer);
  }

  public VoltRunnerJavaSource build() {
    TypeSpec runnerClassBuilder = TypeSpec.classBuilder(rbs.runnerName())
        .addModifiers(Modifier.PUBLIC)
        .addAnnotation(VoltRunner.class)
        .addJavadoc(rbs.runnerJavaDoc())
        .build();
    JavaFile fileBuilder = JavaFile.builder(rbs.runnerPackageName(), runnerClassBuilder)
        .build();
    return new VoltRunnerJavaSource(fileBuilder.toString());
  }
}
