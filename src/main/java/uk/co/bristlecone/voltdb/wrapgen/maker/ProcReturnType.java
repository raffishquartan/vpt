package uk.co.bristlecone.voltdb.wrapgen.maker;

/**
 * Represents the types that may be returned by a VoltDB stored procedure's run method. See the VoltDB documentation for
 * more information: "5.2.5. Returning Results from a Stored Procedure"
 * 
 * @author christo
 */
public enum ProcReturnType {
  LONG_PRIMITIVE, SINGLE_VOLTTABLE, VOLTABLE_ARRAY
}
