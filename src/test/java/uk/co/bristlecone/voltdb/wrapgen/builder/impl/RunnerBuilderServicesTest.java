package uk.co.bristlecone.voltdb.wrapgen.builder.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.function.Function;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import uk.co.bristlecone.voltdb.wrapgen.builder.ProcData;

@RunWith(JMockit.class)
public class RunnerBuilderServicesTest {
  private static final String PROC_DATA_PACKAGE_NAME = "mock.package.name";
  private static final String PROC_DATA_CLASS_NAME = "MockStoredProcedureName";
  private static final String PROC_DATA_CLASS_JAVADOC = "Javadoc for MockStoredProcedureName";

  private static Function<String, String> PACKAGE_NAMER = (procPackage) -> "runner." + procPackage;
  private static Function<String, String> RUNNER_NAMER = (procName) -> procName + "Runner";

  private static final String EXP_RUNNER_PACKAGE_NAME = "runner." + PROC_DATA_PACKAGE_NAME;
  private static final String EXP_RUNNER_CLASS_NAME = PROC_DATA_CLASS_NAME + "Runner";
  // @formatter:off
  private static final String EXP_RUNNER_CLASS_JAVADOC = ""
      + "An instance of this class can be used to run the <code>"
      + PROC_DATA_CLASS_NAME
      + "</code> VoltDB stored procedure. This stored procedure's JavaDoc is:\n\n"
      + "<blockquote>"
      + PROC_DATA_CLASS_JAVADOC
      + "</blockquote>\n\n"
      + "<strong>This class is automatically generated. Manual edits will be overwritten.</strong>\n\n"
      + "@author voltdb-wrapgen\n";
  // @formatter:on

  @Test
  public void procPackageNameWorksCorrectly(@Mocked final ProcData mockProcData) {
    // @formatter:off
    new Expectations() {{
      mockProcData.packageName(); result = PROC_DATA_PACKAGE_NAME;
    }};
    // @formatter:on
    RunnerBuilderServices testee = new RunnerBuilderServices(mockProcData, PACKAGE_NAMER, RUNNER_NAMER);
    assertThat(testee.procPackageName(), is(equalTo(PROC_DATA_PACKAGE_NAME)));
  }

  @Test
  public void runnerPackageNameWorksCorrectly(@Mocked final ProcData mockProcData) {
    // @formatter:off
    new Expectations() {{
      mockProcData.packageName(); result = PROC_DATA_PACKAGE_NAME;
    }};
    // @formatter:on
    RunnerBuilderServices testee = new RunnerBuilderServices(mockProcData, PACKAGE_NAMER, RUNNER_NAMER);
    assertThat(testee.runnerPackageName(), is(equalTo(EXP_RUNNER_PACKAGE_NAME)));
  }

  @Test
  public void procNameWorksCorrectly(@Mocked final ProcData mockProcData) {
    // @formatter:off
    new Expectations() {{
      mockProcData.packageName(); result = PROC_DATA_PACKAGE_NAME;
    }};
    // @formatter:on
    RunnerBuilderServices testee = new RunnerBuilderServices(mockProcData, PACKAGE_NAMER, RUNNER_NAMER);
    assertThat(testee.runnerPackageName(), is(equalTo(EXP_RUNNER_PACKAGE_NAME)));
  }

  @Test
  public void runnerNameWorksCorrectly(@Mocked final ProcData mockProcData) {
    // @formatter:off
    new Expectations() {{
      mockProcData.name(); result = PROC_DATA_CLASS_NAME;
    }};
    // @formatter:on
    RunnerBuilderServices testee = new RunnerBuilderServices(mockProcData, PACKAGE_NAMER, RUNNER_NAMER);
    assertThat(testee.runnerName(), is(equalTo(EXP_RUNNER_CLASS_NAME)));
  }

  @Test
  public void runnerJavaDocWorksCorrectly(@Mocked final ProcData mockProcData) {
    // @formatter:off
    new Expectations() {{
      mockProcData.name(); result = PROC_DATA_CLASS_NAME;
      mockProcData.classJavaDoc(); result = PROC_DATA_CLASS_JAVADOC;
    }};
    // @formatter:on
    RunnerBuilderServices testee = new RunnerBuilderServices(mockProcData, PACKAGE_NAMER, RUNNER_NAMER);
    assertThat(testee.runnerJavaDoc(), is(equalTo(EXP_RUNNER_CLASS_JAVADOC)));
  }
}
