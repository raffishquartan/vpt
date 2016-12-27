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

  private static Function<String, String> PACKAGE_NAMER = (procPackage) -> "runner." + procPackage;
  private static Function<String, String> RUNNER_NAMER = (procName) -> procName + "Runner";

  private static final String EXP_RUNNER_PACKAGE_NAME = "runner." + PROC_DATA_PACKAGE_NAME;
  private static final String EXP_RUNNER_CLASS_NAME = PROC_DATA_CLASS_NAME + "Runner";

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
  public void builderPackageNameWorksCorrectly(@Mocked final ProcData mockProcData) {

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
  public void builderNameWorksCorrectly(@Mocked final ProcData mockProcData) {
    // @formatter:off
    new Expectations() {{
      mockProcData.name(); result = PROC_DATA_CLASS_NAME;
    }};
    // @formatter:on
    RunnerBuilderServices testee = new RunnerBuilderServices(mockProcData, PACKAGE_NAMER, RUNNER_NAMER);
    assertThat(testee.runnerName(), is(equalTo(EXP_RUNNER_CLASS_NAME)));
  }
}
