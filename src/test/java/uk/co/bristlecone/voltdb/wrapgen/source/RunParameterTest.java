package uk.co.bristlecone.voltdb.wrapgen.source;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.Test;

import uk.co.bristlecone.voltdb.wrapgen.source.RunParameter;

public class RunParameterTest {
  private static final String PARAMETER_TYPE = "parameterType";
  private static final String PARAMETER_NAME = "parameterName";

  @Test
  public void nameMethodWorksCorrectly() {
    RunParameter testee = RunParameter.of(PARAMETER_TYPE, PARAMETER_NAME);
    assertThat(testee.name(), is(equalTo(PARAMETER_NAME)));
  }

  @Test
  public void typeMethodWorksCorrectly() {
    RunParameter testee = RunParameter.of(PARAMETER_TYPE, PARAMETER_NAME);
    assertThat(testee.type(), is(equalTo(PARAMETER_TYPE)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void factoryThrowsOnNullType() {
    RunParameter.of(null, PARAMETER_NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void factoryThrowsOnNullName() {
    RunParameter.of(PARAMETER_TYPE, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void factoryThrowsOnEmptyType() {
    RunParameter.of("", PARAMETER_NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void factoryThrowsOnEmptyName() {
    RunParameter.of(PARAMETER_TYPE, "");
  }

  @Test
  public void equalsMethodWorks() {
    RunParameter a = RunParameter.of(PARAMETER_TYPE, PARAMETER_NAME);
    RunParameter b = RunParameter.of(PARAMETER_TYPE, PARAMETER_NAME);
    RunParameter c = RunParameter.of(PARAMETER_TYPE, "someOtherName");
    assertThat(a, is(equalTo(b)));
    assertThat(a, is(not(equalTo(c))));
  }
}
