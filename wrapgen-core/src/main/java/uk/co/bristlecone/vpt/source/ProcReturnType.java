package uk.co.bristlecone.vpt.source;

import uk.co.bristlecone.vpt.VptRuntimeException;

/**
 * Represents the types that may be returned by a VoltDB stored procedure's run method. See the VoltDB documentation for
 * more information: "5.2.5. Returning Results from a Stored Procedure"
 * 
 * @author christo
 */
public enum ProcReturnType {
  LONG_PRIMITIVE("long"), SINGLE_VOLTTABLE("VoltTable"), VOLTABLE_ARRAY("VoltTable[]");

  private final String javaType;

  ProcReturnType(String javaType) {
    this.javaType = javaType;
  }

  @Override
  public String toString() {
    return this.javaType;
  }

  /**
   * @param type The Java type (as a String) to check
   * @return true iff <code>type</code> can be parsed to a valid ProcReturnType, false otherwise
   */
  public static boolean isValidJavaType(String type) {
    switch (type) {
    case "long":
      return true;
    case "VoltTable":
      return true;
    case "VoltTable[]":
      return true;
    default:
      return false;
    }
  }

  /**
   * @param type The Java type (as a String) to convert
   * @return the ProcReturnType value corresponding to the Java type, or throws a WrapgenRuntimeException
   */
  public static ProcReturnType parseJavaType(String type) {
    switch (type) {
    case "long":
      return ProcReturnType.LONG_PRIMITIVE;
    case "VoltTable":
      return ProcReturnType.SINGLE_VOLTTABLE;
    case "VoltTable[]":
      return ProcReturnType.VOLTABLE_ARRAY;
    default:
      throw new VptRuntimeException(String.format("Invalid stored procedure return type %s", type));
    }
  }
}
