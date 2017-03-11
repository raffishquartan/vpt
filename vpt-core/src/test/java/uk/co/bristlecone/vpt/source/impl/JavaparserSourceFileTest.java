package uk.co.bristlecone.vpt.source.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.google.common.collect.ImmutableList;

import uk.co.bristlecone.vpt.VptRuntimeException;
import uk.co.bristlecone.vpt.source.ProcReturnType;
import uk.co.bristlecone.vpt.source.RunParameter;
import uk.co.bristlecone.vpt.source.RunParameterClass;
import uk.co.bristlecone.vpt.source.RunParameterPrimitive;

public class JavaparserSourceFileTest {
  private static final String ARB_EXP_VOLT_PROCEDURE_NAME = "ExpectedProcedureName";
  private static final ProcReturnType ARB_EXP_RUN_RETURN_TYPE = ProcReturnType.SINGLE_VOLTTABLE;
  private static final List<RunParameter> ARB_EXP_PARAM_LIST = ImmutableList
      .of(RunParameterPrimitive.ofInt("somePrimitiveInt"), RunParameterClass.of("java.lang", "String", "someString"));
  private static final String ARB_EXP_PACKAGE_NAME = "ExpectedPackageName";

  // @formatter:off
  private static final String CLASS_VALID = ""
      + String.format("package %s;", ARB_EXP_PACKAGE_NAME)
      + String.format("public class %s extends VoltProcedure {", ARB_EXP_VOLT_PROCEDURE_NAME)
      + String.format("  public %s run(%s) {", ARB_EXP_RUN_RETURN_TYPE.toString(), paramsCommaDelimited())
      + String.format("  }")
      + String.format("}");

  private static final String CLASS_VALID_DEFAULT_PACKAGE = ""
      + String.format("public class %s extends VoltProcedure {", ARB_EXP_VOLT_PROCEDURE_NAME)
      + String.format("  public %s run(%s) {", ARB_EXP_RUN_RETURN_TYPE.toString(), paramsCommaDelimited())
      + String.format("  }")
      + String.format("}");

  private static final String CLASS_INCORRECT_SUPERCLASS = ""
      + String.format("package %s;", ARB_EXP_PACKAGE_NAME)
      + String.format("public class %s extends WrongSuperClass {", ARB_EXP_VOLT_PROCEDURE_NAME)
      + String.format("  public %s run(%s) {", ARB_EXP_RUN_RETURN_TYPE.toString(), paramsCommaDelimited())
      + String.format("  }")
      + String.format("}");

  private static final String CLASS_NO_RUN_METHOD = ""
      + String.format("package %s;", ARB_EXP_PACKAGE_NAME)
      + String.format("public class %s extends VoltProcedure {", ARB_EXP_VOLT_PROCEDURE_NAME)
      + String.format("  public %s invalidRun(%s) {", ARB_EXP_RUN_RETURN_TYPE.toString(), paramsCommaDelimited())
      + String.format("  }")
      + String.format("}");

  private static final String CLASS_INVALID_RUN_METHOD_RETURN_TYPE = ""
      + String.format("package %s;", ARB_EXP_PACKAGE_NAME)
      + String.format("public class %s extends VoltProcedure {", ARB_EXP_VOLT_PROCEDURE_NAME)
      + String.format("  public InvalidReturnType run(%s) {", paramsCommaDelimited())
      + String.format("  }")
      + String.format("}");
  // @formatter:on

  /**
   * Need to use instead of including as part of String.format call assigned to private static final, as a fix for
   * <a href="https://bugs.openjdk.java.net/browse/JDK-8077605">https://bugs.openjdk.java.net/browse/JDK-8077605</a>,
   * see also <a href=
   * "https://www.reddit.com/r/learnprogramming/comments/32bfle/can_you_explain_this_strange_java8_error/">here</a>
   */
  private static String paramsCommaDelimited() {
    return ARB_EXP_PARAM_LIST.stream().map(rp -> String.format("%s %s", rp.typeName(), rp.variableName()))
        .collect(Collectors.joining(", "));
  }

  @Test
  public void voltProcedureNameWorksCorrectly() {
    final CompilationUnit testAst = JavaParser.parse(CLASS_VALID);
    final JavaparserSourceFile testee = new JavaparserSourceFile(testAst, "dummy-test-ast");
    assertThat(testee.voltProcedureName(), is(equalTo(ARB_EXP_VOLT_PROCEDURE_NAME)));
  }

  @Test
  public void runMethodParametersWorksCorrectly() {
    final CompilationUnit testAst = JavaParser.parse(CLASS_VALID);
    final JavaparserSourceFile testee = new JavaparserSourceFile(testAst, "dummy-test-ast");
    assertThat(testee.runMethodParameters(), containsInAnyOrder(ARB_EXP_PARAM_LIST.toArray(new RunParameter[] {})));
  }

  @Test
  public void runMethodReturnTypeWorksCorrectly() {
    final CompilationUnit testAst = JavaParser.parse(CLASS_VALID);
    final JavaparserSourceFile testee = new JavaparserSourceFile(testAst, "dummy-test-ast");
    assertThat(testee.runMethodReturnType(), is(equalTo(ARB_EXP_RUN_RETURN_TYPE)));
  }

  @Test
  public void packageNameWorksCorrectly() {
    final CompilationUnit testAstWithPackage = JavaParser.parse(CLASS_VALID);
    final JavaparserSourceFile testeeWithPackage = new JavaparserSourceFile(testAstWithPackage, "dummy-test-ast");
    assertThat(testeeWithPackage.packageName(), is(equalTo(ARB_EXP_PACKAGE_NAME)));

    final CompilationUnit testAstWithoutPackage = JavaParser.parse(CLASS_VALID_DEFAULT_PACKAGE);
    final JavaparserSourceFile testeeWithoutPackage = new JavaparserSourceFile(testAstWithoutPackage, "dummy-test-ast");
    assertThat(testeeWithoutPackage.packageName(), is(equalTo("")));
  }

  @Test(expected = VptRuntimeException.class)
  public void classWithInvalidExtendsThrowsOnGettingProcedureName() {
    final CompilationUnit testAst = JavaParser.parse(CLASS_INCORRECT_SUPERCLASS);
    final JavaparserSourceFile testee = new JavaparserSourceFile(testAst, "dummy-test-ast");
    testee.voltProcedureName();
  }

  @Test(expected = VptRuntimeException.class)
  public void classWithNoRunMethodThrowsOnGettingParameters() {
    final CompilationUnit testAst = JavaParser.parse(CLASS_NO_RUN_METHOD);
    final JavaparserSourceFile testee = new JavaparserSourceFile(testAst, "dummy-test-ast");
    testee.runMethodParameters();
  }

  @Test(expected = VptRuntimeException.class)
  public void classWithInvalidRunMethodReturnTypeThrowsOnGettingRunReturnType() {
    final CompilationUnit testAst = JavaParser.parse(CLASS_INVALID_RUN_METHOD_RETURN_TYPE);
    final JavaparserSourceFile testee = new JavaparserSourceFile(testAst, "dummy-test-ast");
    testee.runMethodReturnType();
  }

  @Test
  public void isValidVoltProcedureWorksCorrectly() {
    assertThat(new JavaparserSourceFile(JavaParser.parse(CLASS_VALID), "dummy-test-ast").isValidVoltProcedure(),
        is(equalTo(true)));
    assertThat(
        new JavaparserSourceFile(JavaParser.parse(CLASS_INCORRECT_SUPERCLASS), "dummy-test-ast").isValidVoltProcedure(),
        is(equalTo(false)));
    assertThat(new JavaparserSourceFile(JavaParser.parse(CLASS_NO_RUN_METHOD), "dummy-test-ast").isValidVoltProcedure(),
        is(equalTo(false)));
    assertThat(new JavaparserSourceFile(JavaParser.parse(CLASS_INVALID_RUN_METHOD_RETURN_TYPE), "dummy-test-ast")
        .isValidVoltProcedure(), is(equalTo(false)));
  }
}
