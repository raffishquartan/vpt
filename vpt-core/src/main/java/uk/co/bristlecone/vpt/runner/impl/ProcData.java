package uk.co.bristlecone.vpt.runner.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import com.google.common.collect.ImmutableList;

import uk.co.bristlecone.vpt.source.ProcReturnType;
import uk.co.bristlecone.vpt.source.RunParameter;
import uk.co.bristlecone.vpt.source.SourceFile;

/**
 * An immutable class representing the metadata needed to build a vpt-runner-builder
 *
 * @author christo
 */
public class ProcData {
  private final String name;
  private final List<RunParameter> parameters;
  private final ProcReturnType returnType;
  private final String packageName;
  private final String classJavaDoc;

  private ProcData(final String name, final List<RunParameter> parameters, final ProcReturnType returnType,
      final String packageName, final String classJavaDoc) {
    this.name = name;
    this.parameters = parameters;
    this.returnType = returnType;
    this.packageName = packageName;
    this.classJavaDoc = classJavaDoc;
  }

  private ProcData(final SourceFile source) {
    name = source.voltProcedureName();
    parameters = source.runMethodParameters();
    returnType = source.runMethodReturnType();
    packageName = source.packageName();
    classJavaDoc = source.classJavaDoc();
  }

  /**
   * @return the (unqualified) name of the stored procedure class
   */
  public String name() {
    return name;
  }

  /**
   * @return the fully qualified name of the stored procedure class
   */
  public String fullyQualifiedName() {
    return packageName().equals("") ? name() : String.format("%s.%s", packageName(), name());
  }

  /**
   * @return the parameters to the stored procedure's <code>run</code> method
   */
  public List<RunParameter> parameters() {
    return parameters;
  }

  /**
   * @return the stored procedure's return type
   */
  public ProcReturnType returnType() {
    return returnType;
  }

  /**
   * @return the stored procedure's package
   */
  public String packageName() {
    return packageName;
  }

  /**
   * @return the JavaDoc of the stored procedure class
   */
  public String classJavaDoc() {
    return classJavaDoc;
  }

  /**
   * Allows clear, type-safe building of a ProcData
   *
   * @author christo
   */
  public static class Builder {
    private String name;
    private List<RunParameter> parameters;
    private ProcReturnType returnType;
    private String packageName;
    private String classJavaDoc;

    public Builder() {
      // intentionally left blank
    }

    public Builder setName(final String name) {
      checkName(name);
      this.name = name;
      return this;
    }

    public Builder setParameters(final List<RunParameter> parameters) {
      checkParameters(parameters);
      this.parameters = ImmutableList.copyOf(parameters);
      return this;
    }

    public Builder setReturnType(final ProcReturnType returnType) {
      checkReturnType(returnType);
      this.returnType = returnType;
      return this;
    }

    public Builder setPackageName(final String packageName) {
      checkPackageName(packageName);
      this.packageName = packageName;
      return this;
    }

    public Builder setClassJavaDoc(final String classJavaDoc) {
      checkClassJavaDoc(classJavaDoc);
      this.classJavaDoc = classJavaDoc;
      return this;
    }

    private void checkName(final String name) {
      checkArgument(name != null, "name must not be null");
      checkArgument(!name.equals(""), "name must not be empty String");
    }

    private void checkParameters(final List<RunParameter> parameters) {
      checkArgument(parameters != null, "parameters must not be null");
    }

    private void checkReturnType(final ProcReturnType returnType) {
      checkArgument(returnType != null, "returnType must not be null");
    }

    private void checkPackageName(final String packageName) {
      checkArgument(packageName != null, "packageName must not be null");
    }

    private void checkClassJavaDoc(final String classJavaDoc) {
      checkArgument(classJavaDoc != null, "classJavaDoc must not be null");
    }

    public ProcData build() {
      checkName(name);
      checkParameters(parameters);
      checkReturnType(returnType);
      checkPackageName(packageName);
      checkClassJavaDoc(classJavaDoc);
      return new ProcData(name, parameters, returnType, packageName, classJavaDoc);
    }
  }
}
