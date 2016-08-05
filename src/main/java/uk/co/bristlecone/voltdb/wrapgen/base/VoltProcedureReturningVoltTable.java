package uk.co.bristlecone.voltdb.wrapgen.base;

import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public abstract class VoltProcedureReturningVoltTable extends VoltProcedure {
  public abstract VoltTable run(Object... arguments) throws VoltAbortException;
}
