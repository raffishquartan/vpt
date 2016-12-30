package uk.co.bristlecone.voltdb.wrapgen.source;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.Test;

public class RunParameterPrimitiveTest {
  private static final String PARAMETER_NAME = "paramName";

  @Test
  public void booleanFactorySetsTypeCorrectly() {
    RunParameterPrimitive testee = RunParameterPrimitive.ofBoolean(PARAMETER_NAME);
    assertThat(testee.typeName(), is(equalTo("boolean")));
  }

  @Test
  public void shortFactorySetsTypeCorrectly() {
    RunParameterPrimitive testee = RunParameterPrimitive.ofShort(PARAMETER_NAME);
    assertThat(testee.typeName(), is(equalTo("short")));

  }

  @Test
  public void intFactorySetsTypeCorrectly() {
    RunParameterPrimitive testee = RunParameterPrimitive.ofInt(PARAMETER_NAME);
    assertThat(testee.typeName(), is(equalTo("int")));
  }

  @Test
  public void longFactorySetsTypeCorrectly() {
    RunParameterPrimitive testee = RunParameterPrimitive.ofLong(PARAMETER_NAME);
    assertThat(testee.typeName(), is(equalTo("long")));
  }

  @Test
  public void charFactorySetsTypeCorrectly() {
    RunParameterPrimitive testee = RunParameterPrimitive.ofChar(PARAMETER_NAME);
    assertThat(testee.typeName(), is(equalTo("char")));
  }

  @Test
  public void floatFactorySetsTypeCorrectly() {
    RunParameterPrimitive testee = RunParameterPrimitive.ofFloat(PARAMETER_NAME);
    assertThat(testee.typeName(), is(equalTo("float")));
  }

  @Test
  public void doubleFactorySetsTypeCorrectly() {
    RunParameterPrimitive testee = RunParameterPrimitive.ofDouble(PARAMETER_NAME);
    assertThat(testee.typeName(), is(equalTo("double")));
  }

  @Test(expected = IllegalArgumentException.class)
  public void intFactoryThrowsOnNullVariableName() {
    RunParameterPrimitive.ofBoolean(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void intFactoryThrowsOnEmptyVariableName() {
    RunParameterPrimitive.ofBoolean("");
  }

  @Test
  public void isPrimitiveReturnsTrue() {
    RunParameterPrimitive testee = RunParameterPrimitive.ofBoolean(PARAMETER_NAME);
    assertThat(testee.isPrimitive(), is(equalTo(true)));

  }

  @Test
  public void variableNameWorksCorrectly() {
    RunParameterPrimitive testee = RunParameterPrimitive.ofBoolean(PARAMETER_NAME);
    assertThat(testee.variableName(), is(equalTo(PARAMETER_NAME)));
  }

  @Test
  public void equalsMethodWorks() {
    RunParameterPrimitive a = RunParameterPrimitive.ofInt(PARAMETER_NAME);
    RunParameterPrimitive b = RunParameterPrimitive.ofInt(PARAMETER_NAME);
    RunParameterPrimitive c = RunParameterPrimitive.ofInt("someOtherName");
    assertThat(a, is(equalTo(b)));
    assertThat(a, is(not(equalTo(c))));
  }
}
