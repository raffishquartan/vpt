package uk.co.bristlecone.voltdb.wrapgen.maker;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

/**
 * An immutable class representing the metadata needed to build a wrapgen runner
 * 
 * @author christo
 */
public class ProcData {
  private String name;
  private List<Parameter> parameters;
  private ProcReturnType returnType;

  private ProcData(String name, List<Parameter> parameters, ProcReturnType returnType) {
    this.name = name;
    this.parameters = parameters;
    this.returnType = returnType;
  }

  /**
   * @return the (unqualified) name of the stored procedure class
   */
  public String name() {
    return name;
  }

  /**
   * @return the parameters to the stored procedure's <code>run</code> method
   */
  public List<Parameter> parameters() {
    return parameters;
  }

  /**
   * @return the stored procedure's return type
   */
  public ProcReturnType returnType() {
    return returnType;
  }

  /**
   * Allows clear, type-safe building of a ProcMetaData
   * 
   * @author christo
   */
  public static class Builder {
    private String name;
    private List<Parameter> parameters;
    private ProcReturnType returnType;

    public Builder() {
      // intentionally left blank
    }

    public Builder setName(String name) {
      checkArgument(name != null, "name must not be null");
      checkArgument(!name.equals(""), "name must not be empty String");
      this.name = name;
      return this;
    }

    public Builder setParameters(List<Parameter> parameters) {
      checkArgument(parameters != null, "parameters must not be null");
      this.parameters = parameters;
      return this;
    }

    public Builder setReturnType(ProcReturnType returnType) {
      checkArgument(name != null, "returnType must not be null");
      this.returnType = returnType;
      return this;
    }

    public ProcData build() {
      return new ProcData(name, parameters, returnType);
    }
  }
}
