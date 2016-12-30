package uk.co.bristlecone.voltdb.wrapgen.source;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents a primitive parameter (e.g. <code>int foo</code>) to a stored procedure.
 * 
 * @author christo
 */
public class RunParameterPrimitive implements RunParameter {
  private String typeName;
  private String variableName;

  public static RunParameterPrimitive ofBoolean(String variableName) {
    return ofPrimitive("boolean", variableName);
  }

  public static RunParameterPrimitive ofByte(String variableName) {
    return ofPrimitive("byte", variableName);
  }

  public static RunParameterPrimitive ofShort(String variableName) {
    return ofPrimitive("short", variableName);
  }

  public static RunParameterPrimitive ofInt(String variableName) {
    return ofPrimitive("int", variableName);
  }

  public static RunParameterPrimitive ofLong(String variableName) {
    return ofPrimitive("long", variableName);
  }

  public static RunParameterPrimitive ofChar(String variableName) {
    return ofPrimitive("char", variableName);
  }

  public static RunParameterPrimitive ofFloat(String variableName) {
    return ofPrimitive("float", variableName);
  }

  public static RunParameterPrimitive ofDouble(String variableName) {
    return ofPrimitive("double", variableName);
  }

  private static RunParameterPrimitive ofPrimitive(String primitiveType, String variableName) {
    checkArgument(primitiveType != null, "primitiveType parameter must not be null");
    checkArgument(variableName != null, "variableName parameter must not be null");
    checkArgument(!primitiveType.equals(""), "primitiveType parameter must not be an empty String");
    checkArgument(!variableName.equals(""), "variableName parameter must not be an empty String");
    return new RunParameterPrimitive(primitiveType, variableName);
  }

  private RunParameterPrimitive(String typeName, String variableName) {
    this.typeName = typeName;
    this.variableName = variableName;
  }

  /**
   * @return true, by definition
   */
  @Override
  public boolean isPrimitive() {
    return true;
  }

  public String typeName() {
    return typeName;
  }

  public String variableName() {
    return variableName;
  }

  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(typeName)
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
    RunParameterPrimitive rhs = (RunParameterPrimitive) other;
    return new EqualsBuilder().append(typeName, rhs.typeName())
        .append(variableName, rhs.variableName())
        .isEquals();
  }

  public String toString() {
    return new ToStringBuilder(this).append(typeName)
        .append(variableName)
        .build();
  }
}
