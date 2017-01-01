package uk.co.bristlecone.voltdb.wrapgen.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
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
  private static final String PROC_DATA_CLASS_JAVADOC = "Expected JavaDoc for the stored procedure class";
  private static final String EXP_CLASS_WITH_IDENTITY_NAMERS = ""
      + String.format("package %s;\n", PROC_DATA_PACKAGE_NAME)
      + String.format("\n")
      + String.format("import java.io.IOException;\n")
      + String.format("import java.time.Duration;\n")
      + String.format("import java.util.concurrent.CompletableFuture;\n")
      + String.format("import org.voltdb.client.Client;\n")
      + String.format("import org.voltdb.client.ClientResponse;\n")
      + String.format("import org.voltdb.client.NoConnectionsException;\n")
      + String.format("import org.voltdb.client.ProcedureCallback;\n")
      + String.format("import uk.co.bristlecone.voltdb.wrapgen.runner.VoltRunner;\n")
      + String.format("import uk.co.bristlecone.voltdb.wrapgen.runner.WrapgenUtil;\n")
      + String.format("\n")
      + String.format("/**\n")
      + String.format(" * An instance of this class can be used to run the <code>%s</code> ", PROC_DATA_CLASS_NAME)
        + String.format("VoltDB stored procedure. This stored procedure's JavaDoc is:\n")
      + String.format(" *\n")
      + String.format(" * <blockquote>%s</blockquote>\n", PROC_DATA_CLASS_JAVADOC)
      + String.format(" *\n")
      + String.format(" * <strong>Class is automatically generated. Manual edits will be overwritten.</strong>\n")
      + String.format(" *\n")
      + String.format(" * @author voltdb-wrapgen\n")
      + String.format(" */\n")
      + String.format("@VoltRunner\n")
      + String.format("public class %s {\n", PROC_DATA_CLASS_NAME)
      + String.format("  /**\n")
      + String.format("   * <strong>Class is automatically generated. Manual edits will be overwritten.</strong>\n")
      + String.format("   *\n")
      + String.format("   * For more information on the parameters and usage, see the underlying stored procedure: ")
        + String.format("{@link %s.%s}\n", PROC_DATA_PACKAGE_NAME, PROC_DATA_CLASS_NAME)
      + String.format("   *\n")
      + String.format("   * @param client The VoltDB client that is used to execute the %s stored procedure\n",
          PROC_DATA_CLASS_NAME)
      + String.format("   * @return A CompletableFuture for the result of the stored procedure call\n")
      + String.format("   * @throws when the underlying call to {@link Client#callProcedure} (or similar) throws\n")
      + String.format("   */\n")
      + String.format("  CompletableFuture<ClientResponse> run(final Client client)\n")
      + String.format("      throws NoConnectionsException, IOException {\n")
      + String.format("    CompletableFuture<ClientResponse> result = new CompletableFuture<ClientResponse>();\n")
      + String.format("    ProcedureCallback handler = WrapgenUtil.getHandler(result);\n")
      + String.format("    client.callProcedure(handler, \"%s\");\n", PROC_DATA_CLASS_NAME)
      + String.format("    return result;\n")
      + String.format("  }\n")
      + String.format("\n")
      + String.format("  /**\n")
      + String.format("   * <strong>Class is automatically generated. Manual edits will be overwritten.</strong>\n")
      + String.format("   *\n")
      + String.format("   * For more information on the parameters and usage, see the underlying stored procedure: ")
        + String.format("{@link %s.%s}\n", PROC_DATA_PACKAGE_NAME, PROC_DATA_CLASS_NAME)
      + String.format("   *\n")
      + String.format("   * @param client The VoltDB client that is used to execute the %s stored procedure\n",
          PROC_DATA_CLASS_NAME)
      + String.format("   * @return A CompletableFuture for the result of the stored procedure call\n")
      + String.format("   * @throws when the underlying call to {@link Client#callProcedure} (or similar) throws\n")
      + String.format("   */\n")
      + String.format("  CompletableFuture<ClientResponse> runWithTimeout(final Client client, final Duration timeout)\n")
      + String.format("      throws NoConnectionsException, IOException {\n")
      + String.format("    CompletableFuture<ClientResponse> result = new CompletableFuture<ClientResponse>();\n")
      + String.format("    ProcedureCallback handler = WrapgenUtil.getHandler(result);\n")
      + String.format("    client.callProcedure(handler, Math.toIntExact(timeout.toMillis()), \"%s\");\n", PROC_DATA_CLASS_NAME)
      + String.format("    return result;\n")
      + String.format("  }\n")
      + String.format("}\n");
  // @formatter:on

  @Test
  public void builderCreatesClassAsExpected(@Mocked final ProcData mockProcData) {
    // @formatter:off
    new Expectations() {{
      mockProcData.packageName(); result = PROC_DATA_PACKAGE_NAME;
      mockProcData.name(); result = PROC_DATA_CLASS_NAME;
      mockProcData.fullyQualifiedName(); result = String.format("%s.%s", PROC_DATA_PACKAGE_NAME, PROC_DATA_CLASS_NAME);
      mockProcData.classJavaDoc(); result = PROC_DATA_CLASS_JAVADOC;
    }};
    // @formatter:on

    final VoltRunnerJavaSource testee = new RunnerBuilder(mockProcData, Function.identity(), Function.identity())
        .build();

    assertThat(testee.source(), is(equalToIgnoringWhiteSpace(EXP_CLASS_WITH_IDENTITY_NAMERS)));
    // assertThat(testee.source(), is(equalTo(EXP_CLASS_WITH_IDENTITY_NAMERS)));
  }
}
