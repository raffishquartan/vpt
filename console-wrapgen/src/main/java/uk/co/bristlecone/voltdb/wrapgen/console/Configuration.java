package uk.co.bristlecone.voltdb.wrapgen.console;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import uk.co.bristlecone.voltdb.wrapgen.WrapgenRuntimeException;

/**
 * Represents the configuration of a console-wrapgen invocation. Constructed based on the command line arguments passed
 * to the tool.
 *
 * @author christo
 */
public class Configuration {
  private final File sourceDir;
  private final File destDir;
  private final String packageBase;
  private final Pattern regexSuffix;

  public Configuration(final String[] commandLineArgs) {
    try {
      final CommandLine cmd = new DefaultParser().parse(getCommandLineOptions(), commandLineArgs, false);
      sourceDir = new File(cmd.getOptionValue("source"));
      destDir = new File(cmd.getOptionValue("destination"));
      packageBase = cmd.getOptionValue("packagebase");
      regexSuffix = Pattern.compile(cmd.getOptionValue("regexsuffix"));
      checkArgument(sourceDir.exists() && sourceDir.isDirectory(), "source must be a valid directory");
      checkArgument(destDir.exists() && destDir.isDirectory(), "destination must be a valid directory");
    } catch (final ParseException e) {
      throw new WrapgenRuntimeException("Error parsing command lines, see inner excception for details", e);
    }
  }

  private Options getCommandLineOptions() {
    // final org.apache.commons.
    final Option s = new Option("s", "source", true, "root directory of stored procedures");
    s.setRequired(true);
    final Option d = new Option("d", "destination", true, "root destination directory for runners");
    d.setRequired(true);
    final Option p = new Option("p", "packagebase", true, "runner package base");
    p.setRequired(true);
    final Option r = new Option("r", "regexsuffix", true, "runner package regular expression suffix selector");
    r.setRequired(true);
    return new Options().addOption(s)
        .addOption(d)
        .addOption(p)
        .addOption(r);
  }

  public File sourceDir() {
    return sourceDir;
  }

  public File destDir() {
    return destDir;
  }

  public String packageBase() {
    return packageBase;
  }

  public Pattern regexSuffix() {
    return regexSuffix;
  }
}
