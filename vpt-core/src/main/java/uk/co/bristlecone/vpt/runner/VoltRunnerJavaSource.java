package uk.co.bristlecone.vpt.runner;

import java.io.File;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.bristlecone.vpt.VptRuntimeException;

/**
 * Represents the source code for a single VoltRunner, e.g. as returned by a RunnerBuilder. NB: The source code provided
 * via the constructor is not validated or checked in any way. No errors will be raised at construction if it does not
 * specify a valid runner.
 *
 * @author christo
 */
public class VoltRunnerJavaSource {
  private final String source;

  public VoltRunnerJavaSource(final String source) {
    this.source = source;
  }

  /**
   * @return the source code for the VoltRunner
   */
  public String source() {
    return source;
  }

  /**
   * @return the fully qualified class name, e.g. com.companyname.packagename.VoltRunnerName
   * @throws VptRuntimeException in some cases if the runner source does not specify a valid runner class
   */
  public String fullyQualifiedClassName() {
    final String packageName = extractPackageNameFromSource();
    final String packagePrefix = packageName.length() > 0 ? String.format("%s.", packageName) : "";
    return String.format("%s%s", packagePrefix, extractClassNameFromSource());
  }

  /**
   * @return the fully qualified class filepath, e.g. com/companyname/packagename/VoltRunnerName.java
   * @throws VptRuntimeException in some cases if the runner source does not specify a valid runner class
   */
  public String fullyQualifiedClassFilePath() {
    return fullyQualifiedClassName().replace('.', File.separatorChar) + ".java";
  }

  private String extractClassNameFromSource() {
    final Pattern p = Pattern.compile("@VoltRunner\\s+public\\s+class\\s+(\\w+)\\s+\\{");
    final Matcher m = p.matcher(source);
    m.find();
    return Optional.ofNullable(m.group(1))
        .orElseThrow(() -> new VptRuntimeException("No @VoltRunner public class found in VoltRunnerJavaSource"));
  }

  private String extractPackageNameFromSource() {
    final Pattern p = Pattern.compile("package\\s+(\\w+(\\.\\w+)*);", Pattern.MULTILINE);
    final Matcher m = p.matcher(source);
    m.find();
    return Optional.ofNullable(m.group(1)).orElse(""); // default package
  }
}
