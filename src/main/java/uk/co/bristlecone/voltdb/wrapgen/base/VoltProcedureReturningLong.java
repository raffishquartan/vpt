package uk.co.bristlecone.voltdb.wrapgen.base;

import org.voltdb.VoltProcedure;

public abstract class VoltProcedureReturningLong extends VoltProcedure {
  public abstract long run(Object... arguments) throws VoltAbortException;
}
