package uk.co.bristlecone.vpt.runner.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import uk.co.bristlecone.vpt.runner.impl.ProcData;
import uk.co.bristlecone.vpt.source.ProcReturnType;
import uk.co.bristlecone.vpt.source.RunParameter;
import uk.co.bristlecone.vpt.source.RunParameterClass;

public class ProcDataTest {
  private final static String TEST_NAME = "testName";
  private final static List<RunParameter> TEST_PARAMS = ImmutableList
      .of(RunParameterClass.of("test.pkg", "AClass", "aVar"));
  private final static ProcReturnType TEST_RETURN_TYPE = ProcReturnType.VOLTABLE_ARRAY;
  private final static String TEST_PACKAGE_NAME = "test.package";
  private final static String TEST_CLASS_JAVADOC = "Class JavaDoc goes here";

  @Test
  public void builderInstantiatesClassCorrectly() {
    final ProcData testee = new ProcData.Builder().setName(TEST_NAME).setParameters(TEST_PARAMS)
        .setReturnType(TEST_RETURN_TYPE).setPackageName(TEST_PACKAGE_NAME).setClassJavaDoc(TEST_CLASS_JAVADOC).build();
    assertThat(testee.name(), is(equalTo(TEST_NAME)));
    assertThat(testee.parameters(), is(equalTo(TEST_PARAMS)));
    assertThat(testee.returnType(), is(equalTo(TEST_RETURN_TYPE)));
    assertThat(testee.packageName(), is(equalTo(TEST_PACKAGE_NAME)));
    assertThat(testee.classJavaDoc(), is(equalTo(TEST_CLASS_JAVADOC)));
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

  @Test(expected = IllegalArgumentException.class)
  public void builderThrowsOnNullClassJavaDoc() {
    new ProcData.Builder().setClassJavaDoc(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void builderThrowsIfAnyFieldsAreNotSet() {
    new ProcData.Builder().build();
  }
}