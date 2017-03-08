package uk.co.bristlecone.voltdb.wrapgen.console.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.ImmutableList;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import uk.co.bristlecone.voltdb.wrapgen.console.SourceFileProvider;
import uk.co.bristlecone.voltdb.wrapgen.source.SourceFile;

@RunWith(JMockit.class)
public class SourceFilePartitionerTest {

  @Test
  public void allSourceFilesReturnsExpectedStream(@Mocked final SourceFileProvider mockSFProvider,
      @Mocked final SourceFile validSF, @Mocked final SourceFile invalidSF, @Mocked final SourceFile notprocSF) {
    // @formatter:off
    new Expectations() {{
      mockSFProvider.freshSourceFileStream(); result = ImmutableList.of(validSF, invalidSF, notprocSF).stream();
    }};
    // @formatter:on

    final SourceFilePartitioner testee = new SourceFilePartitioner(mockSFProvider);
    assertThat(testee.freshStreamOfAllSourceFiles().collect(Collectors.toList()),
        containsInAnyOrder(validSF, invalidSF, notprocSF));
  }

  @Test
  public void notProcSourceFilesReturnsExpectedStream(@Mocked final SourceFileProvider mockSFProvider,
      @Mocked final SourceFile validSF, @Mocked final SourceFile invalidSF, @Mocked final SourceFile notprocSF) {
    // @formatter:off
    new Expectations() {{
      mockSFProvider.freshSourceFileStream(); result = ImmutableList.of(validSF, invalidSF, notprocSF).stream();
      validSF.isIntendedVoltProcedure(); result = true;
      invalidSF.isIntendedVoltProcedure(); result = true;
      notprocSF.isIntendedVoltProcedure(); result = false;
    }};
    // @formatter:on

    final SourceFilePartitioner testee = new SourceFilePartitioner(mockSFProvider);
    assertThat(testee.freshStreamOfNotProcSourceFiles().collect(Collectors.toList()), containsInAnyOrder(notprocSF));
  }

  @Test
  public void validProcSourceFilesReturnsExpectedStream(@Mocked final SourceFileProvider mockSFProvider,
      @Mocked final SourceFile validSF, @Mocked final SourceFile invalidSF, @Mocked final SourceFile notprocSF) {
    // @formatter:off
    new Expectations() {{
      mockSFProvider.freshSourceFileStream(); result = ImmutableList.of(validSF, invalidSF, notprocSF).stream();
      validSF.isValidVoltProcedure(); result = true;
      invalidSF.isValidVoltProcedure(); result = false;
      notprocSF.isValidVoltProcedure(); result = false;
    }};
    // @formatter:on

    final SourceFilePartitioner testee = new SourceFilePartitioner(mockSFProvider);
    assertThat(testee.freshStreamOfValidProcSourceFiles().collect(Collectors.toList()), containsInAnyOrder(validSF));
  }

  @Test
  public void invalidProcSourceFilesReturnsExpectedStream(@Mocked final SourceFileProvider mockSFProvider,
      @Mocked final SourceFile validSF, @Mocked final SourceFile invalidSF, @Mocked final SourceFile notprocSF) {
    // @formatter:off
    new Expectations() {{
      mockSFProvider.freshSourceFileStream(); result = ImmutableList.of(validSF, invalidSF, notprocSF).stream();
      validSF.isValidVoltProcedure(); result = true; validSF.isIntendedVoltProcedure(); result = true;
      invalidSF.isValidVoltProcedure(); result = false; invalidSF.isIntendedVoltProcedure(); result = true;
      notprocSF.isIntendedVoltProcedure(); result = false;
    }};
    // @formatter:on

    final SourceFilePartitioner testee = new SourceFilePartitioner(mockSFProvider);
    assertThat(testee.freshStreamOftreamInvalidProcSourceFiles().collect(Collectors.toList()),
        containsInAnyOrder(invalidSF));
  }
}
