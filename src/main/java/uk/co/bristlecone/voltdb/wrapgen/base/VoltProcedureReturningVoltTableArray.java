package uk.co.bristlecone.voltdb.wrapgen.base;

import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure;

public abstract class VoltProcedureReturningVoltTableArray extends VoltProcedure {
  public abstract VoltTable[] run(Object... arguments) throws VoltAbortException;
}
