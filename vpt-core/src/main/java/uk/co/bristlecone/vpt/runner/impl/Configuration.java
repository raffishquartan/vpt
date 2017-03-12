package uk.co.bristlecone.vpt.runner.impl;

import java.nio.file.Path;
import java.util.regex.Pattern;

public interface Configuration {

  /**
   * @return the root directory to search for VoltProcedure-implementing classes to create @VoltRunner's for
   */
  Path sourceDir();

  /**
   * @return the root directory to write the FQCN of @VoltRunner's to, for procedures found under
   *         <code>sourceDir()</code>
   */
  Path destDir();

  /**
   * @return the base package name to use for runner's
   */
  String packageBase();

  /**
   * @return the regex to apply to procedure packages, the first matching group in this regex is appended to
   *         <code>packageBase()</code>
   */
  Pattern regexSuffix();

}