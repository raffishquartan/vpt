package uk.co.bristlecone.voltdb.wrapgen.source;

/**
 * Represents a parameter to a VoltDB stored procedure's run method. Core functionality is implemented by subtypes, see
 * those for details.
 * 
 * Called RunParameter and not Parameter because JavaParser also has a Parameter type and the FQCN's get messy fast...
 * 
 * @author christo
 */
public interface RunParameter {
  /**
   * @return true iff the parameter is of primitive type, false otherwise
   */
  boolean isPrimitive();

  /**
   * @return the type of the parameter, as a String, e.g. "String", "int"
   */
  public String typeName();

  /**
   * @return the name of the variable, as a String, e.g. "foo", "aParameter", "i"
   */
  public String variableName();
}
