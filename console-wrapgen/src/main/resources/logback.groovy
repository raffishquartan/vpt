//
// Built on Thu Mar 09 06:33:32 UTC 2017 by logback-translator
// For more information on configuration files in Groovy
// please see http://logback.qos.ch/manual/groovy.html

// For assistance related to this tool or configuration files
// in general, please contact the logback user mailing list at
//    http://qos.ch/mailman/listinfo/logback-user

// For professional support please see
//   http://www.qos.ch/shop/products/professionalSupport

import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import ch.qos.logback.classic.filter.ThresholdFilter

appender("CONSOLE", ConsoleAppender) {
  filter(ThresholdFilter) {
    level = INFO
  }
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyyMMdd:HHmmss} [%thread] %5level %logger{36} :: %msg%n"
  }
}
appender("FILE", RollingFileAppender) {
  file = "logs/log.log"
  encoder(PatternLayoutEncoder) {
    pattern = "%d{yyyyMMdd:HHmmss} [%thread] %5level %logger{36} :: %msg%n"
  }
  rollingPolicy(SizeAndTimeBasedRollingPolicy) {
    fileNamePattern = "logs/log.%d{yyyyMMdd}.%i.txt"
    maxFileSize = "100MB"
    maxHistory = 60
    totalSizeCap = "20GB"
  }
}
root(DEBUG, ["FILE", "CONSOLE"])