package uk.co.bristlecone.voltdb.wrapgen.source;

import java.util.List;

import uk.co.bristlecone.voltdb.wrapgen.VoltRunner;
import uk.co.bristlecone.voltdb.wrapgen.WrapgenRuntimeException;

/**
 * Represents a Java source file and provides accessor methods for extracting metadata needed to build a
 * {@link VoltRunner}. These accessor methods may throw a {@link WrapgenRuntimeException} if the associated Java source
 * does not contain a valid VoltDB stored procedure, so the {@link SourceFile#isValidVoltProcedure} method should be
 * used to guard against this.
 * 
 * @author christo
 */
public interface SourceFile {
  /**
   * @return true iff this SourceFile contains a valid VoltDB stored procedure
   */
  boolean isValidVoltProcedure();

  /**
   * @return the name of the VoltDB stored procedure in this source file; may throw a {@link WrapgenRuntimeException} if
   *         the associated Java source does not contain a valid VoltDB stored procedure
   */
  String voltProcedureName();

  /**
   * @return the parameters to the VoltDB stored procedure's run method; may throw a {@link WrapgenRuntimeException} if
   *         the associated Java source does not contain a valid VoltDB stored procedure
   */
  List<RunParameter> runMethodParameters();

  /**
   * @return the return type of the VoltDB stored procedure in this source file, may throw a
   *         {@link WrapgenRuntimeException} if the associated Java source does not contain a valid VoltDB stored
   *         procedure
   */
  ProcReturnType runMethodReturnType();
}