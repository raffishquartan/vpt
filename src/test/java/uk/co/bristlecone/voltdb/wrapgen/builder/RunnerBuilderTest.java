package uk.co.bristlecone.voltdb.wrapgen.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.function.Function;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class RunnerBuilderTest {

  // @formatter:off
  private static final String PROC_DATA_PACKAGE_NAME = "mock.package.name";
  private static final String PROC_DATA_CLASS_NAME = "MockStoredProcedureName";
  private static final String EXPECTED_CLASS_WITH_IDENTITY_NAMERS = "" 
      + String.format("package %s;\n", PROC_DATA_PACKAGE_NAME)
      + String.format("\n")
      + String.format("import uk.co.bristlecone.voltdb.wrapgen.runner.VoltRunner;\n")
      + String.format("\n")
      + String.format("/**\n")
      + String.format(" * An instance of this class can be used to run the <code>%s</code> ", PROC_DATA_CLASS_NAME)
        + String.format("VoltDB stored procedure. This stored procedure's JavaDoc is:\n")
      + String.format(" *\n")
      + String.format(" * <blockquote>null</blockquote>\n")
      + String.format(" *\n")
      + String.format(" * <strong>This class is automatically generated. Manual edits will be overwritten.</strong>\n")
      + String.format(" *\n")
      + String.format(" * @author voltdb-wrapgen\n")
      + String.format(" */\n")
      + String.format("@VoltRunner\n")
      + String.format("public class %s {\n", PROC_DATA_CLASS_NAME)
      + String.format("}\n");
  // @formatter:on

  @Test
  public void builderCreatesClassAsExpected(@Mocked final ProcData mockProcData) {
    // @formatter:off
    new Expectations() {{
      mockProcData.packageName(); result = PROC_DATA_PACKAGE_NAME;
      mockProcData.name(); result = PROC_DATA_CLASS_NAME;
    }};
    // @formatter:on

    VoltRunnerJavaSource testee = new RunnerBuilder(mockProcData, Function.identity(), Function.identity()).build();

    assertThat(testee.source(), is(equalTo(EXPECTED_CLASS_WITH_IDENTITY_NAMERS)));
  }
}
