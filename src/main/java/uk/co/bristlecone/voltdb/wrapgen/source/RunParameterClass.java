package uk.co.bristlecone.voltdb.wrapgen.source;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents a non-primitive, class parameter (e.g. <code>String foo</code>) to a stored procedure.
 * 
 * @author christo
 */
public class RunParameterClass implements RunParameter {
  private String packageName;
  private String typeName;
  private String variableName;

  public static RunParameterClass of(String packageName, String typeName, String variableName) {
    checkArgument(packageName != null, "packageName parameter must not be null");
    checkArgument(typeName != null, "typeName parameter must not be null");
    checkArgument(variableName != null, "variableName paramter must not be null");
    checkArgument(!typeName.equals(""), "typeName parameter must not be an empty String");
    checkArgument(!variableName.equals(""), "variableName parameter must not be an empty String");
    return new RunParameterClass(packageName, typeName, variableName);
  }

  private RunParameterClass(String packageName, String typeName, String variableName) {
    this.packageName = packageName;
    this.typeName = typeName;
    this.variableName = variableName;
  }

  /**
   * @return false, by definition
   */
  @Override
  public boolean isPrimitive() {
    return false;
  }

  public String packageName() {
    return packageName;
  }

  @Override
  public String typeName() {
    return typeName;
  }

  @Override
  public String variableName() {
    return variableName;
  }

  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(packageName)
        .append(typeName)
        .append(variableName)
        .toHashCode();
  }

  public boolean equals(Object other) {
    if(other == null) {
      return false;
    }
    if(other == this) {
      return true;
    }
    if(getClass() != other.getClass()) {
      return false;
    }
    RunParameterClass rhs = (RunParameterClass) other;
    return new EqualsBuilder().append(packageName, rhs.packageName())
        .append(typeName, rhs.typeName())
        .append(variableName, rhs.variableName())
        .isEquals();
  }

  public String toString() {
    return new ToStringBuilder(this).append(packageName)
        .append(typeName)
        .append(variableName)
        .build();
  }
}