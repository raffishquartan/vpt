package uk.co.bristlecone.voltdb.wrapgen.runner.proc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.Test;

public class ParameterTest {
  private static final String PARAMETER_TYPE = "parameterType";
  private static final String PARAMETER_NAME = "parameterName";

  @Test
  public void nameMethodWorksCorrectly() {
    Parameter testee = Parameter.of(PARAMETER_TYPE, PARAMETER_NAME);
    assertThat(testee.name(), is(equalTo(PARAMETER_NAME)));
  }

  @Test
  public void typeMethodWorksCorrectly() {
    Parameter testee = Parameter.of(PARAMETER_TYPE, PARAMETER_NAME);
    assertThat(testee.type(), is(equalTo(PARAMETER_TYPE)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void factoryThrowsOnNullType() {
    Parameter.of(null, PARAMETER_NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void factoryThrowsOnNullName() {
    Parameter.of(PARAMETER_TYPE, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void factoryThrowsOnEmptyType() {
    Parameter.of("", PARAMETER_NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void factoryThrowsOnEmptyName() {
    Parameter.of(PARAMETER_TYPE, "");
  }

  @Test
  public void equalsMethodWorks() {
    Parameter a = Parameter.of(PARAMETER_TYPE, PARAMETER_NAME);
    Parameter b = Parameter.of(PARAMETER_TYPE, PARAMETER_NAME);
    Parameter c = Parameter.of(PARAMETER_TYPE, "someOtherName");
    assertThat(a, is(equalTo(b)));
    assertThat(a, is(not(equalTo(c))));
  }
}
