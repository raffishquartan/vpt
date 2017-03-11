package uk.co.bristlecone.vpt.runner.console.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.bristlecone.vpt.WrapgenRuntimeException;

/**
 * Responsible for converting procedure package names into runner package names. Runner package names will be the
 * concatenation of <code>packageBase</code> and the 1'th group matched by <code>regexSuffix</code>.
 * <code>regexSuffix</code>'s 1'th matching group is assumed not to start with a <code>.</code> character but should be
 * the beginning of a valid package component.
 *
 * @author christo
 *
 */
public class PackageNamer {
  private final String packageBase;
  private final Pattern regexSuffix;

  public PackageNamer(final String packageBase, final Pattern regexSuffix) {
    this.packageBase = packageBase;
    this.regexSuffix = regexSuffix;
  }

  /**
   * Converts the package of a stored procedure into the package of the runner will be in.
   *
   * @param procPackage The package of the stored procedure
   * @return The package of the runner for this stored procedure
   */
  public String getPackage(final String procPackage) {
    final Matcher suffixMatcher = regexSuffix.matcher(procPackage);
    if(!suffixMatcher.matches()) {
      throw new WrapgenRuntimeException(String.format("%s failed to match proc package %s", regexSuffix, procPackage));
    }
    ;
    return String.format("%s.%s", packageBase, suffixMatcher.group(1));
  }
}
