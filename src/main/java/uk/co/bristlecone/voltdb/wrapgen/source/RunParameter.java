package uk.co.bristlecone.voltdb.wrapgen.source;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import uk.co.bristlecone.voltdb.wrapgen.WrapgenRuntimeException;

/**
 * Represents a parameter (e.g. <code>String foo</code>) to a stored procedure. Called RunParameter because JavaParser
 * also has a Parameter type and the FQCN's get messy fast...
 * 
 * @author christo
 */
public class RunParameter {
  /**
   * Will be an empty optional only for primitive parameters
   */
  private Optional<String> packageName;
  /**
   * The class name
   */
  private String typeName;
  private String variableName;

  private RunParameter(String packageName, String typeName, String variableName) {
    this.packageName = Optional.ofNullable(packageName);
    this.typeName = typeName;
    this.variableName = variableName;
  }

  public static RunParameter of(String packageName, String typeName, String variableName) {
    checkArgument(packageName != null, "packageName parameter must not be null");
    checkArgument(typeName != null, "typeName parameter must not be null");
    checkArgument(variableName != null, "variableName parameter must not be null");
    checkArgument(!packageName.equals(""), "packageName parameter must not be an empty String");
    checkArgument(!typeName.equals(""), "typeName parameter must not be an empty String");
    checkArgument(!variableName.equals(""), "variableName parameter must not be an empty String");
    return new RunParameter(packageName, typeName, variableName);
  }

  public static RunParameter ofBoolean(String variableName) {
    return ofPrimitive("boolean", variableName);
  }

  public static RunParameter ofByte(String variableName) {
    return ofPrimitive("byte", variableName);
  }

  public static RunParameter ofShort(String variableName) {
    return ofPrimitive("short", variableName);
  }

  public static RunParameter ofInt(String variableName) {
    return ofPrimitive("int", variableName);
  }

  public static RunParameter ofLong(String variableName) {
    return ofPrimitive("long", variableName);
  }

  public static RunParameter ofChar(String variableName) {
    return ofPrimitive("char", variableName);
  }

  public static RunParameter ofFloat(String variableName) {
    return ofPrimitive("float", variableName);
  }

  public static RunParameter ofDouble(String variableName) {
    return ofPrimitive("double", variableName);
  }

  private static RunParameter ofPrimitive(String primitiveType, String variableName) {
    checkArgument(primitiveType != null, "primitiveType parameter must not be null");
    checkArgument(variableName != null, "variableName parameter must not be null");
    checkArgument(!primitiveType.equals(""), "primitiveType parameter must not be an empty String");
    checkArgument(!variableName.equals(""), "variableName parameter must not be an empty String");
    return new RunParameter(null, primitiveType, variableName);
  }

  /**
   * @return true iff <code>packageName</code> is not an empty optional, i.e. there is a package
   */
  public boolean isPrimitive() {
    return !packageName.isPresent();
  }

  public String packageName() {
    return packageName.orElseThrow(() -> new WrapgenRuntimeException("Null package name - is it primitive?"));
  }

  public String typeName() {
    return typeName;
  }

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
    RunParameter rhs = (RunParameter) other;
    return new EqualsBuilder().append(packageName, rhs.packageName())
        .append(typeName, rhs.typeName())
        .append(variableName, rhs.variableName())
        .isEquals();
  }

  public String toString() {
    return new ToStringBuilder(this).append(typeName)
        .append(variableName)
        .build();
  }
}