package uk.co.bristlecone.voltdb.wrapgen.builder.impl;

import java.util.function.Function;
import java.util.stream.Collectors;

import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

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

  public String runnerJavaDoc() {
    StringBuilder result = new StringBuilder();
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
   * @return the parameters to the stored procedure's run method, converting them from RunParameter's to ParameterSpec's
   */
  public Iterable<ParameterSpec> runMethodParamsAsParameterSpecs() {
    return procData.parameters()
        .stream()
        .map(rp -> ParameterSpec.builder(TypeName.BYTE, "foo"))
        // .map(rp -> ParameterSpec.builder(ClassName.get(rp.packageName(), rp.typeName()), rp.variableName()))
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
}
