package uk.co.bristlecone.voltdb.wrapgen.console;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.junit.Test;

import uk.co.bristlecone.voltdb.wrapgen.WrapgenRuntimeException;

public class ConfigurationTest {
  private static final Path EXP_SRC = Paths.get("src/main/java");
  private static final Path EXP_DEST = Paths.get("src/main/resources");
  private static final String EXP_PKG_BASE = "some.package.base.runner";
  private static final Pattern EXP_REGEX = Pattern.compile("some\\.package\\.base\\.voltdb\\.(.*)");
  private static final String[] ARGS_WITH_ALL_EXPECTED_OPTIONS = new String[] { "--source=" + EXP_SRC,
      "--destination=" + EXP_DEST, "--packagebase=" + EXP_PKG_BASE, "--regexsuffix=" + EXP_REGEX.pattern() };
  private static final String[] ARGS_WITH_UNEXPECTED_OPTION = new String[] { "--source=someSourceDir",
      "--destination=someDestinationDir", "--packagebase=somePackageBase", "--regexsuffix=someRegex", "--foo=bar" };
  private static final String[] ARGS_WITH_MISSING_OPTION = new String[] { "--source=someSourceDir",
      "--destination=someDestinationDir", "--packagebase=somePackageBase" };

  @Test
  public void sourceDirReturnsExpectedValue() {
    final Configuration testee = new Configuration(ARGS_WITH_ALL_EXPECTED_OPTIONS);
    assertThat(testee.sourceDir(), is(equalTo(EXP_SRC)));
  }

  @Test
  public void destDirReturnsExpectedValue() {
    final Configuration testee = new Configuration(ARGS_WITH_ALL_EXPECTED_OPTIONS);
    assertThat(testee.destDir(), is(equalTo(EXP_DEST)));
  }

  @Test
  public void packageBaseReturnsExpectedValue() {
    final Configuration testee = new Configuration(ARGS_WITH_ALL_EXPECTED_OPTIONS);
    assertThat(testee.packageBase(), is(equalTo(EXP_PKG_BASE)));
  }

  @Test
  public void regexSuffixReturnsExpectedValue() {
    final Configuration testee = new Configuration(ARGS_WITH_ALL_EXPECTED_OPTIONS);
    assertThat(testee.regexSuffix()
        .pattern(), is(equalTo(EXP_REGEX.pattern())));
  }

  @Test(expected = WrapgenRuntimeException.class)
  public void constructorThrowsOnUnexpectedOption() {
    new Configuration(ARGS_WITH_UNEXPECTED_OPTION);
  }

  @Test(expected = WrapgenRuntimeException.class)
  public void constructorThrowsOnMissingOption() {
    new Configuration(ARGS_WITH_MISSING_OPTION);
  }
}
