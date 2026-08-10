package com.helger.jcodemodel.tests.format.parentheses;

import org.junit.Assert;
import org.junit.Test;

public class OperatorTest {

  /// same test, for the 3 classes
  @Test
  public void testMethods() {
    {
      Assert.assertEquals(1, OperatorParenthesesAlways.multIfSameOddityElseAdd(1, 1));
      Assert.assertEquals(4, OperatorParenthesesAlways.multIfSameOddityElseAdd(2, 2));
      Assert.assertEquals(5, OperatorParenthesesAlways.multIfSameOddityElseAdd(2, 3));
      Assert.assertEquals(8, OperatorParenthesesAlways.multIfSameOddityElseAdd(2, 4));
      Assert.assertEquals(7, OperatorParenthesesAlways.multIfSameOddityElseAdd(2, 5));
      Assert.assertEquals('0', OperatorParenthesesAlways.representBools(false, false));
      Assert.assertEquals('1', OperatorParenthesesAlways.representBools(false, true));
      Assert.assertEquals('2', OperatorParenthesesAlways.representBools(true, false));
      Assert.assertEquals('3', OperatorParenthesesAlways.representBools(true, true));
      Assert.assertEquals(null, OperatorParenthesesAlways.concat(null, null));
      Assert.assertEquals("a", OperatorParenthesesAlways.concat("a", null));
      Assert.assertEquals("b", OperatorParenthesesAlways.concat(null, "b"));
      Assert.assertEquals("ab", OperatorParenthesesAlways.concat("a", "b"));
      Assert.assertEquals(0xffffffff, OperatorParenthesesAlways.bitwiseImply(0, 1));
    }
    {
      Assert.assertEquals(1, OperatorParenthesesNoToken.multIfSameOddityElseAdd(1, 1));
      Assert.assertEquals(4, OperatorParenthesesNoToken.multIfSameOddityElseAdd(2, 2));
      Assert.assertEquals(5, OperatorParenthesesNoToken.multIfSameOddityElseAdd(2, 3));
      Assert.assertEquals(8, OperatorParenthesesNoToken.multIfSameOddityElseAdd(2, 4));
      Assert.assertEquals(7, OperatorParenthesesNoToken.multIfSameOddityElseAdd(2, 5));
      Assert.assertEquals('0', OperatorParenthesesNoToken.representBools(false, false));
      Assert.assertEquals('1', OperatorParenthesesNoToken.representBools(false, true));
      Assert.assertEquals('2', OperatorParenthesesNoToken.representBools(true, false));
      Assert.assertEquals('3', OperatorParenthesesNoToken.representBools(true, true));
      Assert.assertEquals(null, OperatorParenthesesNoToken.concat(null, null));
      Assert.assertEquals("a", OperatorParenthesesNoToken.concat("a", null));
      Assert.assertEquals("b", OperatorParenthesesNoToken.concat(null, "b"));
      Assert.assertEquals("ab", OperatorParenthesesNoToken.concat("a", "b"));
      Assert.assertEquals(0xffffffff, OperatorParenthesesNoToken.bitwiseImply(0, 1));
    }
    {
      Assert.assertEquals(1, OperatorParenthesesRequired.multIfSameOddityElseAdd(1, 1));
      Assert.assertEquals(4, OperatorParenthesesRequired.multIfSameOddityElseAdd(2, 2));
      Assert.assertEquals(5, OperatorParenthesesRequired.multIfSameOddityElseAdd(2, 3));
      Assert.assertEquals(8, OperatorParenthesesRequired.multIfSameOddityElseAdd(2, 4));
      Assert.assertEquals(7, OperatorParenthesesRequired.multIfSameOddityElseAdd(2, 5));
      Assert.assertEquals('0', OperatorParenthesesRequired.representBools(false, false));
      Assert.assertEquals('1', OperatorParenthesesRequired.representBools(false, true));
      Assert.assertEquals('2', OperatorParenthesesRequired.representBools(true, false));
      Assert.assertEquals('3', OperatorParenthesesRequired.representBools(true, true));
      Assert.assertEquals(null, OperatorParenthesesRequired.concat(null, null));
      Assert.assertEquals("a", OperatorParenthesesRequired.concat("a", null));
      Assert.assertEquals("b", OperatorParenthesesRequired.concat(null, "b"));
      Assert.assertEquals("ab", OperatorParenthesesRequired.concat("a", "b"));
      Assert.assertEquals(0xffffffff, OperatorParenthesesRequired.bitwiseImply(0, 1));
    }

  }

}
