package uk.co.bristlecone.voltdb.wrapgen.runner.proc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class ProcDataTest {
  private final static String TEST_NAME = "testName";
  private final static List<Parameter> TEST_PARAMETERS = new ImmutableList.Builder<Parameter>()
      .add(Parameter.of("testType", "testName"))
      .build();
  private final static ProcReturnType TEST_RETURN_TYPE = ProcReturnType.VOLTABLE_ARRAY;

  @Test
  public void builderInstantiatesClassCorrectly() {
    ProcData testee = new ProcData.Builder().setName(TEST_NAME)
        .setParameters(TEST_PARAMETERS)
        .setReturnType(TEST_RETURN_TYPE)
        .build();
    assertThat(testee.name(), is(equalTo(TEST_NAME)));
    assertThat(testee.parameters(), is(equalTo(TEST_PARAMETERS)));
    assertThat(testee.returnType(), is(equalTo(TEST_RETURN_TYPE)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void builderThrowsOnNullName() {
    new ProcData.Builder().setName(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void builderThrowsOnEmptyName() {
    new ProcData.Builder().setName("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void builderThrowsOnNullParameters() {
    new ProcData.Builder().setParameters(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void builderThrowsOnNullReturnType() {
    new ProcData.Builder().setReturnType(null);
  }
}