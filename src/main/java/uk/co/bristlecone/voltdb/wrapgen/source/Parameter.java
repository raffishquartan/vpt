package uk.co.bristlecone.voltdb.wrapgen.source;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents a parameter (e.g. <code>String foo</code>) to a stored procedure
 * 
 * @author christo
 */
public class Parameter {
  private String type;
  private String name;

  private Parameter(String type, String name) {
    this.type = type;
    this.name = name;
  }

  public static Parameter of(String type, String name) {
    checkArgument(type != null, "type parameter must not be null");
    checkArgument(name != null, "name parameter must not be null");
    checkArgument(!type.equals(""), "type parameter must not be an empty String");
    checkArgument(!name.equals(""), "name parameter must not be an empty String");

    return new Parameter(type, name);
  }

  public String type() {
    return type;
  }

  public String name() {
    return name;
  }

  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(type)
        .append(name)
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
    Parameter rhs = (Parameter) other;
    return new EqualsBuilder().append(type, rhs.type())
        .append(name, rhs.name())
        .isEquals();
  }

  public String toString() {
    return new ToStringBuilder(this).append(type)
        .append(name)
        .build();
  }
}