package com.helger.jcodemodel.tests.basic.record;

import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import com.helger.jcodemodel.compile.annotation.TestJCM;
import com.helger.jcodemodel.exceptions.JCodeModelException;

@TestJCM
public class JRecordTestGen {

  public final String rootPackage=getClass().getPackageName();

  /**
   * Test: Basic record with two components Expected output:
   *
   * <pre>
   * package org.example;
   *
   * public record Point(int x, int y) {
   * }
   * </pre>
   *
   * @throws JCodeModelException
   *                             In case of error
   */
  public JCodeModel genBasicRecord () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package (rootPackage)._record ("BasicPoint");
    rec.recordComponent (cm.INT, "x");
    rec.recordComponent (cm.INT, "y");
    return cm;
  }

  /**
   * Test: Empty record (no components) Expected output:
   *
   * <pre>
   * public record Empty ()
   * {}
   * </pre>
   *
   * @throws JCodeModelException
   *         In case of error
   */
  public JCodeModel genEmptyRecord () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    cm._package(rootPackage)._record("Empty");
    return cm;
  }

  /**
   * Test: Record with object type components Expected output:
   *
   * <pre>
   * public record Person (String name, Integer age)
   * {}
   * </pre>
   *
   * @throws JCodeModelException
   *         In case of error
   */
  public JCodeModel genRecordWithObjectComponents () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package (rootPackage)._record ("Person");
    rec.recordComponent (cm.ref (String.class), "name");
    rec.recordComponent (cm.ref (Integer.class), "age");
    return cm;
  }

  /**
   * Test: Record implementing an interface Expected output:
   *
   * <pre>
   * public record NamedPoint (int x, int y, String name) implements Comparable &lt;NamedPoint&gt;
   * {}
   * </pre>
   *
   * @throws JCodeModelException
   *         In case of error
   */
  public JCodeModel genRecordImplementsInterface () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package (rootPackage)._record ("NamedPoint");
    rec.recordComponent (cm.INT, "x");
    rec.recordComponent (cm.INT, "y");
    rec.recordComponent (cm.ref (String.class), "name");
    rec._implements (cm.ref (Comparable.class).narrow (rec));
    JMethod cmp = rec.method(JMod.PUBLIC, cm.INT, "compareTo");
    cmp.param(rec, "other");
    cmp.body()._return(JExpr.lit(0));
    return cm;
  }

}
