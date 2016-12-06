package uk.co.bristlecone.voltdb.wrapgen.source.impl;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;

import uk.co.bristlecone.voltdb.wrapgen.source.RunParameter;
import uk.co.bristlecone.voltdb.wrapgen.source.ProcReturnType;
import uk.co.bristlecone.voltdb.wrapgen.source.SourceFile;

/**
 * Implements the {@link SourceFile} interface using a <code>com.github.javaparser.ast.CompilationUnit</code>.
 * 
 * @author christo
 */
public class JavaParserSourceFile implements SourceFile {
  public JavaParserSourceFile(CompilationUnit ast) {

  }

  /*
   * (non-Javadoc)
   * 
   * @see uk.co.bristlecone.voltdb.wrapgen.source.SourceFile#isValidVoltProcedure()
   */
  @Override
  public boolean isValidVoltProcedure() {
    // TODO Auto-generated method stub
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see uk.co.bristlecone.voltdb.wrapgen.source.SourceFile#voltProcedureName()
   */
  @Override
  public String voltProcedureName() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see uk.co.bristlecone.voltdb.wrapgen.source.SourceFile#runMethodParameters()
   */
  @Override
  public List<Parameter> runMethodParameters() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see uk.co.bristlecone.voltdb.wrapgen.source.SourceFile#runMethodReturnType()
   */
  @Override
  public ProcReturnType runMethodReturnType() {
    // TODO Auto-generated method stub
    return null;
  }
}
