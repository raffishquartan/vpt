package uk.co.bristlecone.vpt.source;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.Test;

public class RunParameterClassTest {
  private static final String PARAMETER_PACKAGE = "test.package";
  private static final String PARAMETER_TYPE = "ClassName";
  private static final String PARAMETER_NAME = "paramName";

  @Test(expected = IllegalArgumentException.class)
  public void factoryThrowsOnNullPackage() {
    RunParameterClass.of(null, PARAMETER_TYPE, PARAMETER_NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void factoryThrowsOnNullType() {
    RunParameterClass.of(PARAMETER_PACKAGE, null, PARAMETER_NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void factoryThrowsOnNullName() {
    RunParameterClass.of(PARAMETER_PACKAGE, PARAMETER_TYPE, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void factoryThrowsOnEmptyType() {
    RunParameterClass.of(PARAMETER_PACKAGE, "", PARAMETER_NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void factoryThrowsOnEmptyName() {
    RunParameterClass.of(PARAMETER_PACKAGE, PARAMETER_TYPE, "");
  }

  @Test
  public void isPrimitiveReturnsFalse() {
    final RunParameterClass testee = RunParameterClass.of(PARAMETER_PACKAGE, PARAMETER_TYPE, PARAMETER_NAME);
    assertThat(testee.isPrimitive(), is(equalTo(false)));
  }

  @Test
  public void nameMethodWorksCorrectly() {
    final RunParameterClass testee = RunParameterClass.of(PARAMETER_PACKAGE, PARAMETER_TYPE, PARAMETER_NAME);
    assertThat(testee.variableName(), is(equalTo(PARAMETER_NAME)));
  }

  @Test
  public void typeMethodWorksCorrectly() {
    final RunParameterClass testee = RunParameterClass.of(PARAMETER_PACKAGE, PARAMETER_TYPE, PARAMETER_NAME);
    assertThat(testee.typeName(), is(equalTo(PARAMETER_TYPE)));
  }

  @Test
  public void packageNameMethodWorksCorrectly() {
    final RunParameterClass testee = RunParameterClass.of(PARAMETER_PACKAGE, PARAMETER_TYPE, PARAMETER_NAME);
    assertThat(testee.packageName(), is(equalTo(PARAMETER_PACKAGE)));
  }

  @Test
  public void equalsMethodWorks() {
    final RunParameterClass a = RunParameterClass.of(PARAMETER_PACKAGE, PARAMETER_TYPE, PARAMETER_NAME);
    final RunParameterClass b = RunParameterClass.of(PARAMETER_PACKAGE, PARAMETER_TYPE, PARAMETER_NAME);
    final RunParameterClass c = RunParameterClass.of(PARAMETER_PACKAGE, PARAMETER_TYPE, "someOtherName");
    assertThat(a, is(equalTo(b)));
    assertThat(a, is(not(equalTo(c))));
  }
}
