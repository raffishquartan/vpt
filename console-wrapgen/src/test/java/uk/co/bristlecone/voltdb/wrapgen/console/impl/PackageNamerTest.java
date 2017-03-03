package uk.co.bristlecone.voltdb.wrapgen.console.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.regex.Pattern;

import org.junit.Test;

import uk.co.bristlecone.voltdb.wrapgen.WrapgenRuntimeException;
import uk.co.bristlecone.voltdb.wrapgen.console.impl.PackageNamer;

public class PackageNamerTest {
  private static final String PACKAGE_BASE = "test.package.base.runner";
  private static final Pattern REGEX_SUFFIX = Pattern.compile("test\\.package\\.base\\.voltdb\\.(.*)");

  @Test
  public void getPackageWorksAsExpected() {
    PackageNamer testee = new PackageNamer(PACKAGE_BASE, REGEX_SUFFIX);
    assertThat(testee.getPackage("test.package.base.voltdb.users"), is(equalTo("test.package.base.runner.users")));
    assertThat(testee.getPackage("test.package.base.voltdb.backup"), is(equalTo("test.package.base.runner.backup")));
  }

  @Test(expected = WrapgenRuntimeException.class)
  public void getPackageThrowsIfProcPackageDoesntMatchRegexSuffix() {
    PackageNamer testee = new PackageNamer(PACKAGE_BASE, REGEX_SUFFIX);
    testee.getPackage("this.package.doesnt.match");
  }
}
