package uk.co.bristlecone.vpt.source;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import uk.co.bristlecone.vpt.VptRuntimeException;

public class ProcReturnTypeTest {
  @Test
  public void isValidTypeWorksCorrectly() {
    assertThat(ProcReturnType.isValidJavaType("long"), is(equalTo(true)));
    assertThat(ProcReturnType.isValidJavaType("VoltTable"), is(equalTo(true)));
    assertThat(ProcReturnType.isValidJavaType("VoltTable[]"), is(equalTo(true)));

    assertThat(ProcReturnType.isValidJavaType("Long"), is(equalTo(false)));
    assertThat(ProcReturnType.isValidJavaType("voltable"), is(equalTo(false)));
    assertThat(ProcReturnType.isValidJavaType("volttable[]"), is(equalTo(false)));
    assertThat(ProcReturnType.isValidJavaType("int"), is(equalTo(false)));
    assertThat(ProcReturnType.isValidJavaType("void"), is(equalTo(false)));
    assertThat(ProcReturnType.isValidJavaType("Void"), is(equalTo(false)));
    assertThat(ProcReturnType.isValidJavaType("Foo"), is(equalTo(false)));
  }

  @Test
  public void parseJavaTypeWorksCorrectly() {
    assertThat(ProcReturnType.parseJavaType("long"), is(equalTo(ProcReturnType.LONG_PRIMITIVE)));
    assertThat(ProcReturnType.parseJavaType("VoltTable"), is(equalTo(ProcReturnType.SINGLE_VOLTTABLE)));
    assertThat(ProcReturnType.parseJavaType("VoltTable[]"), is(equalTo(ProcReturnType.VOLTABLE_ARRAY)));
  }

  @Test(expected = VptRuntimeException.class)
  public void parseJavaTypeThrowsOnUnknownType() {
    ProcReturnType.parseJavaType("InvalidType");
  }
}
