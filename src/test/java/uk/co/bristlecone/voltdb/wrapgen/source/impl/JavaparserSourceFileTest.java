package uk.co.bristlecone.voltdb.wrapgen.source.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.google.common.collect.ImmutableList;

import uk.co.bristlecone.voltdb.wrapgen.WrapgenRuntimeException;
import uk.co.bristlecone.voltdb.wrapgen.source.ProcReturnType;
import uk.co.bristlecone.voltdb.wrapgen.source.RunParameter;

public class JavaparserSourceFileTest {
  private static final String ARB_EXP_VOLT_PROCEDURE_NAME = "ExpectedProcedureName";
  private static final ProcReturnType ARB_EXP_RUN_RETURN_TYPE = ProcReturnType.SINGLE_VOLTTABLE;
  private static final List<RunParameter> ARB_EXP_PARAM_LIST = ImmutableList.of(RunParameter.of("Type", "varname"));
  private static final String ARB_EXP_PACKAGE_NAME = "ExpectedPackageName";

  // @formatter:off
  private static final String CLASS_VALID = ""
      + String.format("package %s;", ARB_EXP_PACKAGE_NAME)
      + String.format("public class %s extends VoltProcedure {", ARB_EXP_VOLT_PROCEDURE_NAME)
      + String.format("  public %s run(%s) {", ARB_EXP_RUN_RETURN_TYPE.toString(), ARB_EXP_PARAM_LIST.stream()
          .map(rp -> {
            return String.format("%s %s", rp.type(), rp.name());
          })
          .collect(Collectors.joining(", ")))
      + String.format("  }") 
      + String.format("}");
      
  private static final String CLASS_VALID_DEFAULT_PACKAGE = ""
      + String.format("public class %s extends VoltProcedure {", ARB_EXP_VOLT_PROCEDURE_NAME)
      + String.format("  public %s run(%s) {", ARB_EXP_RUN_RETURN_TYPE.toString(), ARB_EXP_PARAM_LIST.stream()
          .map(rp -> {
            return String.format("%s %s", rp.type(), rp.name());
          })
          .collect(Collectors.joining(", ")))
      + String.format("  }") 
      + String.format("}");
  
  private static final String CLASS_INCORRECT_SUPERCLASS = ""
      + String.format("package %s;", ARB_EXP_PACKAGE_NAME)
      + String.format("public class %s extends WrongSuperClass {", ARB_EXP_VOLT_PROCEDURE_NAME)
      + String.format("  public %s run(%s) {", ARB_EXP_RUN_RETURN_TYPE.toString(), ARB_EXP_PARAM_LIST.stream()
          .map(rp -> {
            return String.format("%s %s", rp.type(), rp.name());
          })
          .collect(Collectors.joining(", ")))
      + String.format("  }") 
      + String.format("}");

  private static final String CLASS_NO_RUN_METHOD = ""
      + String.format("package %s;", ARB_EXP_PACKAGE_NAME)
      + String.format("public class %s extends VoltProcedure {", ARB_EXP_VOLT_PROCEDURE_NAME)
      + String.format("  public %s invalidRun(%s) {", ARB_EXP_RUN_RETURN_TYPE.toString(), ARB_EXP_PARAM_LIST.stream()
          .map(rp -> {
            return String.format("%s %s", rp.type(), rp.name());
          })
          .collect(Collectors.joining(", ")))
      + String.format("  }") 
      + String.format("}");

  private static final String CLASS_INVALID_RUN_METHOD_RETURN_TYPE = ""
      + String.format("package %s;", ARB_EXP_PACKAGE_NAME)
      + String.format("public class %s extends VoltProcedure {", ARB_EXP_VOLT_PROCEDURE_NAME)
      + String.format("  public InvalidReturnType run(%s) {", ARB_EXP_PARAM_LIST.stream()
          .map(rp -> {
            return String.format("%s %s", rp.type(), rp.name());
          })
          .collect(Collectors.joining(", ")))
      + String.format("  }") 
      + String.format("}");

  @Test
  public void voltProcedureNameWorksCorrectly() {
    CompilationUnit testAst = JavaParser.parse(CLASS_VALID);
    JavaparserSourceFile testee = new JavaparserSourceFile(testAst, "dummy-test-ast");
    assertThat(testee.voltProcedureName(), is(equalTo(ARB_EXP_VOLT_PROCEDURE_NAME)));
  }

  @Test
  public void runMethodParametersWorksCorrectly() {
    CompilationUnit testAst = JavaParser.parse(CLASS_VALID);
    JavaparserSourceFile testee = new JavaparserSourceFile(testAst, "dummy-test-ast");
    assertThat(testee.runMethodParameters(), is(equalTo(ARB_EXP_PARAM_LIST)));
  }

  @Test
  public void runMethodReturnTypeWorksCorrectly() {
    CompilationUnit testAst = JavaParser.parse(CLASS_VALID);
    JavaparserSourceFile testee = new JavaparserSourceFile(testAst, "dummy-test-ast");
    assertThat(testee.runMethodReturnType(), is(equalTo(ARB_EXP_RUN_RETURN_TYPE)));
  }
  
  @Test
  public void packageNameWorksCorrectly() {
    CompilationUnit testAstWithPackage = JavaParser.parse(CLASS_VALID);
    JavaparserSourceFile testeeWithPackage = new JavaparserSourceFile(testAstWithPackage, "dummy-test-ast");
    assertThat(testeeWithPackage.packageName(), is(equalTo(ARB_EXP_PACKAGE_NAME)));
    
    CompilationUnit testAstWithoutPackage = JavaParser.parse(CLASS_VALID_DEFAULT_PACKAGE);
    JavaparserSourceFile testeeWithoutPackage = new JavaparserSourceFile(testAstWithoutPackage, "dummy-test-ast");
    assertThat(testeeWithoutPackage.packageName(), is(equalTo("")));
  }
  
  @Test(expected = WrapgenRuntimeException.class)
  public void classWithInvalidExtendsThrowsOnGettingProcedureName() {
    CompilationUnit testAst = JavaParser.parse(CLASS_INCORRECT_SUPERCLASS);
    JavaparserSourceFile testee = new JavaparserSourceFile(testAst, "dummy-test-ast");
    testee.voltProcedureName();
  }
  
  @Test(expected = WrapgenRuntimeException.class)
  public void classWithNoRunMethodThrowsOnGettingParameters() {
    CompilationUnit testAst = JavaParser.parse(CLASS_NO_RUN_METHOD);
    JavaparserSourceFile testee = new JavaparserSourceFile(testAst, "dummy-test-ast");
    testee.runMethodParameters();
  }
  
  @Test(expected = WrapgenRuntimeException.class)
  public void classWithInvalidRunMethodReturnTypeThrowsOnGettingRunReturnType() {
    CompilationUnit testAst = JavaParser.parse(CLASS_INVALID_RUN_METHOD_RETURN_TYPE);
    JavaparserSourceFile testee = new JavaparserSourceFile(testAst, "dummy-test-ast");
    testee.runMethodReturnType();
  }
  
  @Test
  public void isValidVoltProcedureWorksCorrectly() {
    assertThat(new JavaparserSourceFile(JavaParser.parse(CLASS_VALID), "dummy-test-ast").isValidVoltProcedure(), is(equalTo(true)));
    assertThat(new JavaparserSourceFile(JavaParser.parse(CLASS_INCORRECT_SUPERCLASS), "dummy-test-ast").isValidVoltProcedure(), is(equalTo(false)));
    assertThat(new JavaparserSourceFile(JavaParser.parse(CLASS_NO_RUN_METHOD), "dummy-test-ast").isValidVoltProcedure(), is(equalTo(false)));
    assertThat(new JavaparserSourceFile(JavaParser.parse(CLASS_INVALID_RUN_METHOD_RETURN_TYPE), "dummy-test-ast").isValidVoltProcedure(), is(equalTo(false)));
  }
}
