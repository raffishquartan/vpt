package uk.co.bristlecone.voltdb.wrapgen.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;

import org.junit.Test;

public class VoltRunnerJavaSourceTest {
  private static final String EXP_PACKAGE = "test.foo";
  private static final String EXP_BASE_PATH = String.format("test%sfoo", File.separator);
  private static final String EXP_CLASS = "TestRunner";

  private static final String EXP_FQ_NAME = String.format("%s.%s", EXP_PACKAGE, EXP_CLASS);
  private static final String EXP_FQ_PATH = String.format("%s%s%s", EXP_BASE_PATH, File.separator, EXP_CLASS);

  // @formatter:off
  private final static String EXAMPLE__VOLT_RUNNER_ANNOTATION_ON_DIFF_LINE = ""
      + String.format("// Hee's a very short initial comment\n")
      + String.format("package %s;\n", EXP_PACKAGE)
      + String.format("import java.util.List;\n")
      + String.format("@VoltRunner\n")
      + String.format("public class %s {\n", EXP_CLASS)
      + String.format("}");
  // @formatter:on

  // @formatter:off
  private final static String EXAMPLE__VOLT_RUNNER_ANNOTATION_ON_SAME_LINE = ""
      + String.format("// Hee's a very short initial comment\n")
      + String.format("package %s;\n", EXP_PACKAGE)
      + String.format("import java.util.List;\n")
      + String.format("@VoltRunner public class %s {\n", EXP_CLASS)
      + String.format("}");
  // @formatter:on

  // @formatter:off
  private final static String EXAMPLE__VOLT_RUNNER_DEFAULT_PACKAGE = ""
      + String.format("// Hee's a very short initial comment\n")
      + String.format("import java.util.List;\n")
      + String.format("@VoltRunner public class %s {\n", EXP_CLASS)
      + String.format("}");
  // @formatter:on

  @Test
  public void fullyQualifiedClassNameIsExtractedCorrectlyWhenVoltRunnerIsOnDifferentLine() {
    final VoltRunnerJavaSource testee = new VoltRunnerJavaSource(EXAMPLE__VOLT_RUNNER_ANNOTATION_ON_DIFF_LINE);
    assertThat(testee.fullyQualifiedClassName(), is(equalTo(String.format("%s.%s", EXP_PACKAGE, EXP_CLASS))));
  }

  @Test
  public void fullyQualifiedClassNameIsExtractedCorrectlyWhenVoltRunnerIsOnSameLine() {
    final VoltRunnerJavaSource testee = new VoltRunnerJavaSource(EXAMPLE__VOLT_RUNNER_ANNOTATION_ON_SAME_LINE);
    assertThat(testee.fullyQualifiedClassName(), is(equalTo(EXP_FQ_NAME)));
  }

  public void fullyQualifiedClassNameIsExtractedCorrectlyWhenVoltRunnerIsInDefaultPackage() {
    final VoltRunnerJavaSource testee = new VoltRunnerJavaSource(EXAMPLE__VOLT_RUNNER_DEFAULT_PACKAGE);
    assertThat(testee.fullyQualifiedClassName(), is(equalTo(EXP_CLASS)));
  }

  @Test
  public void fullyQualifiedClassPathIsExtractedCorrectly() {
    final VoltRunnerJavaSource testee = new VoltRunnerJavaSource(EXAMPLE__VOLT_RUNNER_ANNOTATION_ON_SAME_LINE);
    assertThat(testee.fullyQualifiedClassFilePath(), is(equalTo(EXP_FQ_PATH)));
  }
}
