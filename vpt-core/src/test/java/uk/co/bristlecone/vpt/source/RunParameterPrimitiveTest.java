package uk.co.bristlecone.vpt.source;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.Test;

public class RunParameterPrimitiveTest {
  private static final String PARAMETER_NAME = "paramName";

  @Test
  public void booleanFactorySetsTypeCorrectly() {
    final RunParameterPrimitive testee = RunParameterPrimitive.ofBoolean(PARAMETER_NAME);
    assertThat(testee.typeName(), is(equalTo("boolean")));
  }

  @Test
  public void shortFactorySetsTypeCorrectly() {
    final RunParameterPrimitive testee = RunParameterPrimitive.ofShort(PARAMETER_NAME);
    assertThat(testee.typeName(), is(equalTo("short")));

  }

  @Test
  public void intFactorySetsTypeCorrectly() {
    final RunParameterPrimitive testee = RunParameterPrimitive.ofInt(PARAMETER_NAME);
    assertThat(testee.typeName(), is(equalTo("int")));
  }

  @Test
  public void longFactorySetsTypeCorrectly() {
    final RunParameterPrimitive testee = RunParameterPrimitive.ofLong(PARAMETER_NAME);
    assertThat(testee.typeName(), is(equalTo("long")));
  }

  @Test
  public void charFactorySetsTypeCorrectly() {
    final RunParameterPrimitive testee = RunParameterPrimitive.ofChar(PARAMETER_NAME);
    assertThat(testee.typeName(), is(equalTo("char")));
  }

  @Test
  public void floatFactorySetsTypeCorrectly() {
    final RunParameterPrimitive testee = RunParameterPrimitive.ofFloat(PARAMETER_NAME);
    assertThat(testee.typeName(), is(equalTo("float")));
  }

  @Test
  public void doubleFactorySetsTypeCorrectly() {
    final RunParameterPrimitive testee = RunParameterPrimitive.ofDouble(PARAMETER_NAME);
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
    final RunParameterPrimitive testee = RunParameterPrimitive.ofBoolean(PARAMETER_NAME);
    assertThat(testee.isPrimitive(), is(equalTo(true)));

  }

  @Test
  public void variableNameWorksCorrectly() {
    final RunParameterPrimitive testee = RunParameterPrimitive.ofBoolean(PARAMETER_NAME);
    assertThat(testee.variableName(), is(equalTo(PARAMETER_NAME)));
  }

  @Test
  public void equalsMethodWorks() {
    final RunParameterPrimitive a = RunParameterPrimitive.ofInt(PARAMETER_NAME);
    final RunParameterPrimitive b = RunParameterPrimitive.ofInt(PARAMETER_NAME);
    final RunParameterPrimitive c = RunParameterPrimitive.ofInt("someOtherName");
    assertThat(a, is(equalTo(b)));
    assertThat(a, is(not(equalTo(c))));
  }
}
