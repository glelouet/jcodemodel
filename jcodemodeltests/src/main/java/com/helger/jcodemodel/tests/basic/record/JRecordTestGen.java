package com.helger.jcodemodel.tests.basic.record;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import com.helger.jcodemodel.JRecordComponent;
import com.helger.jcodemodel.JTypeVar;
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

  /**
   * Test: Generic record with type parameters Expected output:
   *
   * <pre>
   * public record Pair&lt;T, U&gt;(T first, U second) {
   * }
   * </pre>
   *
   * @throws JCodeModelException
   *                             In case of error
   */
  public JCodeModel genGenericRecord() throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel();
    final JDefinedClass rec = cm._package(rootPackage)._record("Pair");
    final JTypeVar t = rec.generify("T");
    final JTypeVar u = rec.generify("U");
    rec.recordComponent(t, "first");
    rec.recordComponent(u, "second");
    return cm;
  }

  /**
   * Test: Record with annotated component Expected output:
   *
   * <pre>
   * public record Person(@NonNull String name, int age) {
   * }
   * </pre>
   *
   * @throws JCodeModelException
   *                             In case of error
   */
  public JCodeModel genRecordWithAnnotatedComponent() throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel();
    final JDefinedClass rec = cm._package(rootPackage)._record("AnnotatedPerson");
    final JRecordComponent nameComponent = rec.recordComponent(cm.ref(String.class), "name");
    nameComponent.annotate(RecordAnnotationExample.class);
    rec.recordComponent(cm.INT, "age");
    return cm;
  }

  /**
   * we need a specific record annotation to be kept
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.RECORD_COMPONENT)
  public @interface RecordAnnotationExample
  {
  }

}
