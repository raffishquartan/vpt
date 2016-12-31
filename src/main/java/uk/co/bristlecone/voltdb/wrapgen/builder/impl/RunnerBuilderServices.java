package uk.co.bristlecone.voltdb.wrapgen.builder.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterSpec;

import uk.co.bristlecone.voltdb.wrapgen.builder.ProcData;
import uk.co.bristlecone.voltdb.wrapgen.source.RunParameter;
import uk.co.bristlecone.voltdb.wrapgen.source.RunParameterClass;
import uk.co.bristlecone.voltdb.wrapgen.source.RunParameterPrimitive;

/**
 * Provides methods that provide services to a RunnerBuilder, allowing it to create the full class definition.
 *
 * @author christo
 */
public class RunnerBuilderServices {
  private final Function<String, String> packageNamer;
  private final Function<String, String> runnerNamer;
  private final ProcData procData;

  public RunnerBuilderServices(final ProcData procData, final Function<String, String> packageNamer,
      final Function<String, String> runnerNamer) {
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

  public String runnerJavaDoc() {
    final StringBuilder result = new StringBuilder();
    result.append("An instance of this class can be used to run the <code>");
    result.append(procName());
    result.append("</code> VoltDB stored procedure. This stored procedure's JavaDoc is:\n\n");
    result.append("<blockquote>");
    result.append(procData.classJavaDoc());
    result.append("</blockquote>\n\n");
    result.append("<strong>This class is automatically generated. Manual edits will be overwritten.</strong>\n\n");
    result.append("@author voltdb-wrapgen\n");
    return result.toString();
  }

  /**
   * Returns the parameters to the stored procedure's run method, converting them from RunParameter's to
   * ParameterSpec's. Uses {@link RunnerBuilderServices#runParameterToParameterSpecBuilder} which internally uses
   * {@link RunParameter#isPrimitive} to dististinguish between reference/class and primitive RunParameter's. Ugly
   * manual dispatch :/
   */
  public List<ParameterSpec> runMethodParamsAsParameterSpecs() {
    return procData.parameters()
        .stream()
        .map(RunnerBuilderServices::runParameterToParameterSpecBuilder)
        .map(psb -> psb.build())
        .collect(Collectors.toList());
  }

  /**
   * @return the parameters to the stored procedure's run method as a comma-separated list of variable names
   */
  public String runMethodParamsAsVariableList() {
    return procData.parameters()
        .stream()
        .map(rp -> rp.variableName())
        .collect(Collectors.joining(", "));
  }

  /**
   * @return the number of parameters to the stored procedure's run() method
   */
  public int runMethodNumberOfParams() {
    return procData.parameters()
        .size();
  }

  /**
   * @return Return a ParameterSpec.Builder for the provided RunParameter
   */
  private static ParameterSpec.Builder runParameterToParameterSpecBuilder(final RunParameter rp) {
    if(rp.isPrimitive()) {
      final RunParameterPrimitive rpp = (RunParameterPrimitive) rp;
      return ParameterSpec.builder(rpp.typeClass(), rpp.variableName());
    } else {
      final RunParameterClass rpc = (RunParameterClass) rp;
      return ParameterSpec.builder(ClassName.get(rpc.packageName(), rpc.typeName()), rpc.variableName());
    }
  }
}
