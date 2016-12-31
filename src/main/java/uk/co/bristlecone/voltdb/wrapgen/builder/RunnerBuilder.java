package uk.co.bristlecone.voltdb.wrapgen.builder;

import java.io.IOException;
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

import uk.co.bristlecone.voltdb.wrapgen.builder.impl.RunnerBuilderServices;
import uk.co.bristlecone.voltdb.wrapgen.runner.VoltRunner;
import uk.co.bristlecone.voltdb.wrapgen.runner.WrapgenUtil;

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
    final TypeName runnerRunReturnType = ParameterizedTypeName.get(CompletableFuture.class, ClientResponse.class);
    final String callProcedureParamsAsVariableList = String.format("handler, \"%s\", %s", rbs.procName(),
        rbs.runMethodParamsAsVariableList());
    final MethodSpec runMethod = MethodSpec.methodBuilder("run")
        .addParameter(Client.class, "client", Modifier.FINAL)
        .addParameters(rbs.runMethodParamsAsParameterSpecs())
        .addException(NoConnectionsException.class)
        .addException(IOException.class)
        .returns(runnerRunReturnType)
        .addStatement("$T result = new $T()", runnerRunReturnType, runnerRunReturnType)
        .addStatement("$T handler = $T.getHandler(result)", ProcedureCallback.class, WrapgenUtil.class)
        .addStatement("client.callProcedure($L)", callProcedureParamsAsVariableList)
        .addStatement("return result")
        .build();
    final TypeSpec runnerClassBuilder = TypeSpec.classBuilder(rbs.runnerName())
        .addModifiers(Modifier.PUBLIC)
        .addAnnotation(VoltRunner.class)
        .addJavadoc(rbs.runnerJavaDoc())
        .addMethod(runMethod)
        // .addMethod(runWithTimeoutMethod)
        .build();
    final JavaFile fileBuilder = JavaFile.builder(rbs.runnerPackageName(), runnerClassBuilder)
        .build();
    return new VoltRunnerJavaSource(fileBuilder.toString());
  }
}
