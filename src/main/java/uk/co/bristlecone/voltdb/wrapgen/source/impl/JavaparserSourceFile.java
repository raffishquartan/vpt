package uk.co.bristlecone.voltdb.wrapgen.source.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.printer.PrettyPrinterConfiguration;

import uk.co.bristlecone.voltdb.wrapgen.WrapgenRuntimeException;
import uk.co.bristlecone.voltdb.wrapgen.source.ProcReturnType;
import uk.co.bristlecone.voltdb.wrapgen.source.RunParameter;
import uk.co.bristlecone.voltdb.wrapgen.source.SourceFile;

/**
 * Implements the {@link SourceFile} interface using a <code>com.github.javaparser.ast.CompilationUnit</code>.
 * 
 * @author christo
 */
public class JavaparserSourceFile implements SourceFile {
  private CompilationUnit ast;
  private String filepath;

  public JavaparserSourceFile(CompilationUnit ast, String filepath) {
    this.ast = ast;
    this.filepath = filepath;
  }

  /*
   * (non-Javadoc)
   * 
   * @see uk.co.bristlecone.voltdb.wrapgen.source.SourceFile#isValidVoltProcedure()
   */
  @Override
  public boolean isValidVoltProcedure() {
    Optional<ClassOrInterfaceDeclaration> storedProc = getClassExtendingVoltProcedure();
    Optional<MethodDeclaration> runMethod = getRunMethod();

    if(!storedProc.isPresent()) {
      return false;
    }

    if(!runMethod.isPresent()) {
      return false;
    }

    Optional<String> returnType = getRunMethodReturnTypeAsString();
    if(!returnType.isPresent() || !ProcReturnType.isValidJavaType(getRunMethodReturnTypeAsString().get())) {
      return false;
    }

    // TODO - checks to do, e.g. throws the wrong type of chcekced exception

    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see uk.co.bristlecone.voltdb.wrapgen.source.SourceFile#voltProcedureName()
   */
  @Override
  public String voltProcedureName() {
    return getClassExtendingVoltProcedure().map(c -> c.getName())
        .map(sn -> sn.getIdentifier())
        .orElseThrow(
            () -> new WrapgenRuntimeException(String.format("No VoltProcedure-extending type found in %s", filepath)));
  }

  /*
   * (non-Javadoc)
   * 
   * @see uk.co.bristlecone.voltdb.wrapgen.source.SourceFile#runMethodParameters()
   */
  @Override
  public List<RunParameter> runMethodParameters() {
    return getRunMethod().map(m -> m.getParameters())
        .map(pl -> pl.stream()
            .map(p -> RunParameter.of(javaparserParameterToType(p), javaparserParameterToName(p))))
        .map(s -> s.collect(Collectors.toList()))
        .orElseThrow(() -> new WrapgenRuntimeException(
            String.format("Either no VoltProcedure-extending type found in %s, or no run method defined", filepath)));
  }

  /*
   * (non-Javadoc)
   * 
   * @see uk.co.bristlecone.voltdb.wrapgen.source.SourceFile#runMethodReturnType()
   */
  @Override
  public ProcReturnType runMethodReturnType() {
    return getRunMethodReturnTypeAsString().map(ProcReturnType::parseJavaType)
        .orElseThrow(() -> new WrapgenRuntimeException(
            String.format("Either no VoltProcedure-extending type found in %s, or no run method defined", filepath)));
  }

  /*
   * (non-Javadoc)
   * 
   * @see uk.co.bristlecone.voltdb.wrapgen.source.SourceFile#packageName()
   */
  @Override
  public String packageName() {
    return ast.getPackage()
        .map(p -> p.getPackageName())
        .orElse("");
  }

  /**
   * @return the <code>run</code> method's return type as Optional<String>
   */
  private Optional<String> getRunMethodReturnTypeAsString() {
    return getRunMethod().map(m -> m.getElementType())
        .map(e -> e.toString(new PrettyPrinterConfiguration().setPrintComments(false)));
  }

  /**
   * @return the first {@link ClassOrInterfaceDeclaration} type in this source file which extends a class called
   *         "VoltProcedure" (NB: Does not do full symbol resolution, so this method could be misled by another class of
   *         the same name in a different package).
   */
  private Optional<ClassOrInterfaceDeclaration> getClassExtendingVoltProcedure() {
    ClassOrInterfaceType voltProcedure = new ClassOrInterfaceType("VoltProcedure");
    return ast.getTypes()
        .stream()
        .filter(d -> d instanceof ClassOrInterfaceDeclaration)
        .map(d -> (ClassOrInterfaceDeclaration) d)
        .filter(d -> d.getExtends()
            .contains(voltProcedure))
        .findFirst();
  }

  /**
   * @return the first method overload for "run" in the class returned by
   *         {@link JavaparserSourceFile#getClassExtendingVoltProcedure}
   */
  private Optional<MethodDeclaration> getRunMethod() {
    return getClassExtendingVoltProcedure().flatMap(c -> c.getMethodsByName("run")
        .stream()
        .findFirst());
  }

  /**
   * @param param a JavaParser Parameter
   * @return the type of <code>param</code>, as a String
   */
  private static String javaparserParameterToType(Parameter param) {
    return param.getType()
        .toString();
  }

  /**
   * @param param a JavaParser Parameter
   * @return the variable name of <code>param</code>, as a String
   */
  private static String javaparserParameterToName(Parameter param) {
    return param.getNameAsString();
  }
}
