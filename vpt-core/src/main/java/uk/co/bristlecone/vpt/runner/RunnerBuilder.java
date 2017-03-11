package uk.co.bristlecone.vpt.runner;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import javax.lang.model.element.Modifier;

import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcedureCallback;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import uk.co.bristlecone.vpt.VoltRunner;
import uk.co.bristlecone.vpt.VptUtil;
import uk.co.bristlecone.vpt.runner.impl.ProcData;
import uk.co.bristlecone.vpt.runner.impl.RunnerBuilderServices;

/**
 * Allows Java class definitions for VoltRunner's to be easily built from ProcData and supporting metadata
 *
 * @author christo
 */
public class RunnerBuilder {
  private final RunnerBuilderServices rbs;

  public RunnerBuilder(final ProcData procData, final Function<String, String> packageNamer,
      final Function<String, String> runnerNamer) {
    rbs = new RunnerBuilderServices(procData, packageNamer, runnerNamer);
  }

  public VoltRunnerJavaSource build() {
    final TypeSpec runnerClassBuilder = TypeSpec.classBuilder(rbs.runnerName()).addModifiers(Modifier.PUBLIC)
        .addAnnotation(VoltRunner.class).addJavadoc(rbs.runnerJavaDoc()).addMethod(buildRunMethodSpec())
        .addMethod(buildRunWithTimeoutMethodSpec()).build();
    final JavaFile fileBuilder = JavaFile.builder(rbs.runnerPackageName(), runnerClassBuilder).build();
    return new VoltRunnerJavaSource(fileBuilder.toString());
  }

  private static TypeName runnerRunReturnType() {
    return ParameterizedTypeName.get(CompletableFuture.class, ClientResponse.class);
  }

  private MethodSpec buildRunMethodSpec() {
    return MethodSpec.methodBuilder("run").addJavadoc(rbs.runnerRunMethodJavaDoc())
        .addParameter(Client.class, "client", Modifier.FINAL).addParameters(rbs.runMethodParamsAsParameterSpecs())
        .addException(NoConnectionsException.class).addException(IOException.class).returns(runnerRunReturnType())
        .addStatement("$T result = new $T()", runnerRunReturnType(), runnerRunReturnType())
        .addStatement("$T handler = $T.getHandler(result)", ProcedureCallback.class, VptUtil.class)
        .addStatement("client.callProcedure($L)", callProcedureParamsAsVariableList()).addStatement("return result")
        .build();
  }

  private MethodSpec buildRunWithTimeoutMethodSpec() {
    return MethodSpec.methodBuilder("runWithTimeout").addJavadoc(rbs.runnerRunMethodJavaDoc())
        .addParameter(Client.class, "client", Modifier.FINAL).addParameter(Duration.class, "timeout", Modifier.FINAL)
        .addParameters(rbs.runMethodParamsAsParameterSpecs()).addException(NoConnectionsException.class)
        .addAnnotation(Deprecated.class).addException(IOException.class).returns(runnerRunReturnType())
        .addStatement("$T result = new $T()", runnerRunReturnType(), runnerRunReturnType())
        .addStatement("$T handler = $T.getHandler(result)", ProcedureCallback.class, VptUtil.class)
        .addStatement("client.callProcedure($L)", callProcedureWithTimeoutParamsAsVariableList("timeout"))
        .addStatement("return result").build();
  }

  private String callProcedureParamsAsVariableList() {
    if(rbs.runMethodNumberOfParams() > 0) {
      return String.format("handler, \"%s\", %s", rbs.procName(), rbs.runMethodParamsAsVariableList());
    } else {
      return String.format("handler, \"%s\"", rbs.procName());
    }
  }

  private String callProcedureWithTimeoutParamsAsVariableList(final String timeoutVariableName) {
    if(rbs.runMethodNumberOfParams() > 0) {
      return String.format("handler, Math.toIntExact(%s.toMillis()), \"%s\", %s", timeoutVariableName, rbs.procName(),
          rbs.runMethodParamsAsVariableList());
    } else {
      return String.format("handler, Math.toIntExact(%s.toMillis()), \"%s\"", timeoutVariableName, rbs.procName());
    }
  }
}
