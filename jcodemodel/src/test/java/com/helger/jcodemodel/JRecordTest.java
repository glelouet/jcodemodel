/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 * Portions Copyright 2013-2026 Philip Helger + contributors
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package com.helger.jcodemodel;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.jcodemodel.exceptions.JCodeModelException;
import com.helger.jcodemodel.util.CodeModelTestsHelper;

/**
 * Test class for Java record support. Java records (JEP 395, Java 16+) are a special kind of class
 * that acts as a transparent carrier for immutable data. Records automatically provide: - A
 * canonical constructor - Private final fields for each component - Public accessor methods for
 * each component (same name as component) - equals(), hashCode(), and toString() implementations
 */
public final class JRecordTest
{
  /**
   * Test: Basic record with two components Expected output:
   * 
   * <pre>
   * package org.example;
   *
   * public record Point (int x, int y)
   * {}
   * </pre>
   * 
   * @throws JCodeModelException
   *         In case of error
   */
  @Test
  public void testBasicRecord () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package ("org.example")._record ("Point");
    rec.recordComponent (cm.INT, "x");
    rec.recordComponent (cm.INT, "y");

    final String output = CodeModelTestsHelper.declare (rec);
    assertTrue (output.contains ("record Point(int x, int y)"));

    CodeModelTestsHelper.parseCodeModel (cm);
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
  @Test
  public void testEmptyRecord () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package ("org.example")._record ("Empty");

    final String output = CodeModelTestsHelper.declare (rec);
    assertTrue (output.contains ("record Empty()"));

    CodeModelTestsHelper.parseCodeModel (cm);
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
  @Test
  public void testRecordWithObjectComponents () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package ("org.example")._record ("Person");
    rec.recordComponent (cm.ref (String.class), "name");
    rec.recordComponent (cm.ref (Integer.class), "age");

    final String output = CodeModelTestsHelper.declare (rec);
    assertTrue (output.contains ("record Person(java.lang.String name, java.lang.Integer age)"));

    CodeModelTestsHelper.parseCodeModel (cm);
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
  @Test
  public void testRecordImplementsInterface () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package ("org.example")._record ("NamedPoint");
    rec.recordComponent (cm.INT, "x");
    rec.recordComponent (cm.INT, "y");
    rec.recordComponent (cm.ref (String.class), "name");
    rec._implements (cm.ref (Comparable.class).narrow (rec));

    final String output = CodeModelTestsHelper.declare (rec);
    assertTrue (output, output.contains ("record NamedPoint(int x, int y, java.lang.String name)"));
    assertTrue (output.contains ("implements java.lang.Comparable<org.example.NamedPoint>"));

    CodeModelTestsHelper.parseCodeModel (cm);
  }

  /**
   * Test: Generic record with type parameters Expected output:
   * 
   * <pre>
   * public record Pair &lt;T, U&gt; (T first, U second)
   * {}
   * </pre>
   * 
   * @throws JCodeModelException
   *         In case of error
   */
  @Test
  public void testGenericRecord () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package ("org.example")._record ("Pair");
    final JTypeVar t = rec.generify ("T");
    final JTypeVar u = rec.generify ("U");
    rec.recordComponent (t, "first");
    rec.recordComponent (u, "second");

    final String output = CodeModelTestsHelper.declare (rec);
    assertTrue (output.contains ("record Pair<T, U>(T first, U second)"));

    CodeModelTestsHelper.parseCodeModel (cm);
  }

  /**
   * Test: Record with annotated component Expected output:
   * 
   * <pre>
   * public record Person (@NonNull String name, int age)
   * {}
   * </pre>
   * 
   * @throws JCodeModelException
   *         In case of error
   */
  @Test
  public void testRecordWithAnnotatedComponent () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package ("org.example")._record ("Person");

    final JRecordComponent nameComponent = rec.recordComponent (cm.ref (String.class), "name");
    nameComponent.annotate (cm.ref (org.jspecify.annotations.NonNull.class));

    rec.recordComponent (cm.INT, "age");

    final String output = CodeModelTestsHelper.declare (rec);
    assertTrue (output.contains ("@org.jspecify.annotations.NonNull java.lang.String name"));

    CodeModelTestsHelper.parseCodeModel (cm);
  }

  /**
   * Test: Record with compact constructor (validation) Expected output:
   * 
   * <pre>
   * public record Range (int lo, int hi)
   * {
   *   public Range
   *   {
   *     if (lo > hi)
   *     {
   *       throw new IllegalArgumentException ();
   *     }
   *   }
   * }
   * </pre>
   * 
   * @throws JCodeModelException
   *         In case of error
   */
  @Test
  public void testRecordWithCompactConstructor () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package ("org.example")._record ("Range");
    rec.recordComponent (cm.INT, "lo");
    rec.recordComponent (cm.INT, "hi");

    // Compact constructor - no parameter list, just validation logic
    final JMethod compactCtor = rec.compactConstructor (JMod.PUBLIC);
    compactCtor.body ()
               ._if (JExpr.ref ("lo").gt (JExpr.ref ("hi")))
               ._then ()
               ._throw (JExpr._new (cm.ref (IllegalArgumentException.class)));

    final String output = CodeModelTestsHelper.declare (rec);
    // Compact constructor has no parentheses after the record name
    assertTrue (output.contains ("public Range {"));

    CodeModelTestsHelper.parseCodeModel (cm);
  }

  /**
   * Test: Record with explicit canonical constructor Expected output:
   * 
   * <pre>
   * public record Range (int lo, int hi)
   * {
   *   public Range (int lo, int hi)
   *   {
   *     if (lo > hi)
   *     {
   *       throw new IllegalArgumentException ();
   *     }
   *     this.lo = lo;
   *     this.hi = hi;
   *   }
   * }
   * </pre>
   * 
   * @throws JCodeModelException
   *         In case of error
   */
  @SuppressWarnings ("unused")
  @Test
  public void testRecordWithCanonicalConstructor () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package ("org.example")._record ("Range");
    final JRecordComponent loComp = rec.recordComponent (cm.INT, "lo");
    final JRecordComponent hiComp = rec.recordComponent (cm.INT, "hi");

    // Canonical constructor - must have same parameters as record components
    final JMethod ctor = rec.constructor (JMod.PUBLIC);
    final JVar loParam = ctor.param (cm.INT, "lo");
    final JVar hiParam = ctor.param (cm.INT, "hi");
    ctor.body ()._if (loParam.gt (hiParam))._then ()._throw (JExpr._new (cm.ref (IllegalArgumentException.class)));
    // This could be done nicer...
    ctor.body ().assign (JExpr._this ().ref ("lo"), loParam);
    ctor.body ().assign (JExpr._this ().ref ("hi"), hiParam);

    final String output = CodeModelTestsHelper.declare (rec);
    // Compact constructor has no parentheses after the record name
    assertTrue (output.contains ("this.lo = lo;"));
    assertTrue (output.contains ("this.hi = hi;"));

    CodeModelTestsHelper.parseCodeModel (cm);
  }

  /**
   * Test: Record with additional instance method Expected output:
   * 
   * <pre>
   * public record Point (int x, int y)
   * {
   *   public double distance ()
   *   {
   *     return Math.sqrt ((x * x) + (y * y));
   *   }
   * }
   * </pre>
   * 
   * @throws JCodeModelException
   *         In case of error
   */
  @Test
  public void testRecordWithMethod () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package ("org.example")._record ("Point");
    rec.recordComponent (cm.INT, "x");
    rec.recordComponent (cm.INT, "y");

    final JMethod method = rec.method (JMod.PUBLIC, cm.DOUBLE, "distance");
    method.body ()
          ._return (cm.ref (Math.class)
                      .staticInvoke ("sqrt")
                      .arg (JExpr.ref ("x").mul (JExpr.ref ("x")).plus (JExpr.ref ("y").mul (JExpr.ref ("y")))));

    CodeModelTestsHelper.parseCodeModel (cm);
  }

  /**
   * Test: Record with static field and method Expected output:
   * 
   * <pre>
   * public record Point (int x, int y)
   * {
   *   public static final Point ORIGIN = new Point (0, 0);
   *
   *   public static Point of (int x, int y)
   *   {
   *     return new Point (x, y);
   *   }
   * }
   * </pre>
   * 
   * @throws JCodeModelException
   *         In case of error
   */
  @Test
  public void testRecordWithStaticMembers () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package ("org.example")._record ("Point");
    rec.recordComponent (cm.INT, "x");
    rec.recordComponent (cm.INT, "y");

    // Static field
    rec.field (JMod.PUBLIC | JMod.STATIC | JMod.FINAL,
               rec,
               "ORIGIN",
               JExpr._new (rec).arg (JExpr.lit (0)).arg (JExpr.lit (0)));

    // Static factory method
    final JMethod factory = rec.method (JMod.PUBLIC | JMod.STATIC, rec, "of");
    final JVar xParam = factory.param (cm.INT, "x");
    final JVar yParam = factory.param (cm.INT, "y");
    factory.body ()._return (JExpr._new (rec).arg (xParam).arg (yParam));

    CodeModelTestsHelper.parseCodeModel (cm);
  }

  /**
   * Test: Nested record inside a class Expected output:
   * 
   * <pre>
   * public class Outer
   * {
   *   public record Inner (String value)
   *   {}
   * }
   * </pre>
   * 
   * @throws JCodeModelException
   *         In case of error
   */
  @Test
  public void testNestedRecord () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass outer = cm._package ("org.example")._class ("Outer");
    final JDefinedClass inner = outer._record (JMod.PUBLIC, "Inner");
    inner.recordComponent (cm.ref (String.class), "value");

    final String output = CodeModelTestsHelper.declare (outer);
    assertTrue (output.contains ("record Inner(java.lang.String value)"));

    CodeModelTestsHelper.parseCodeModel (cm);
  }

  /**
   * Test: Record with javadoc Expected output:
   * 
   * <pre>
   * /**
   *  * Represents a 2D point.
   *  *
   *  * @param x the x coordinate
   *  * @param y the y coordinate
   *  *\/
   * public record Point(int x, int y) {
   * }
   * </pre>
   * 
   * @throws JCodeModelException
   *         In case of error
   */
  @Test
  public void testRecordWithJavadoc () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package ("org.example")._record ("Point");

    final JRecordComponent xComp = rec.recordComponent (cm.INT, "x");
    final JRecordComponent yComp = rec.recordComponent (cm.INT, "y");

    rec.javadoc ().add ("Represents a 2D point.");
    rec.javadoc ().addParam (xComp).add ("the x coordinate");
    rec.javadoc ().addParam (yComp).add ("the y coordinate");

    final String output = CodeModelTestsHelper.declare (rec);
    assertTrue (output.contains ("@param x\n *     the x coordinate"));
    assertTrue (output.contains ("@param y\n *     the y coordinate"));

    CodeModelTestsHelper.parseCodeModel (cm);
  }

  /**
   * Test: Record with varargs component (last component can be varargs) Expected output:
   * 
   * <pre>
   * public record VarArgsRecord (String name, int... values)
   * {}
   * </pre>
   * 
   * @throws JCodeModelException
   *         In case of error
   */
  @Test
  public void testRecordWithVarargsComponent () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package ("org.example")._record ("VarArgsRecord");
    rec.recordComponent (cm.ref (String.class), "name");
    rec.recordComponentVararg (cm.INT, "values");

    final String output = CodeModelTestsHelper.declare (rec);
    assertTrue (output.contains ("int... values"));

    CodeModelTestsHelper.parseCodeModel (cm);
  }

  /**
   * Test: Record with array component Expected output:
   * 
   * <pre>
   * public record ArrayRecord (String [] names, int [] [] matrix)
   * {}
   * </pre>
   * 
   * @throws JCodeModelException
   *         In case of error
   */
  @Test
  public void testRecordWithArrayComponent () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package ("org.example")._record ("ArrayRecord");
    rec.recordComponent (cm.ref (String.class).array (), "names");
    rec.recordComponent (cm.INT.array ().array (), "matrix");

    final String output = CodeModelTestsHelper.declare (rec);
    assertTrue (output.contains ("String[] names"));
    assertTrue (output.contains ("int[][] matrix"));

    CodeModelTestsHelper.parseCodeModel (cm);
  }

  /**
   * Test: Record with bounded generic type parameter Expected output:
   * 
   * <pre>
   * public record NumberPair &lt;T extends Number&gt; (T first, T second)
   * {}
   * </pre>
   * 
   * @throws JCodeModelException
   *         In case of error
   */
  @Test
  public void testRecordWithBoundedTypeParameter () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package ("org.example")._record ("NumberPair");
    final JTypeVar t = rec.generify ("T", Number.class);
    rec.recordComponent (t, "first");
    rec.recordComponent (t, "second");

    final String output = CodeModelTestsHelper.declare (rec);
    assertTrue (output.contains ("<T extends java.lang.Number>"));
    assertTrue (output.contains ("(T first, T second)"));

    CodeModelTestsHelper.parseCodeModel (cm);
  }

  @Test (expected = IllegalStateException.class)
  public void testCantAddRecordComponentsToNonRecordClass () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass c = cm._package ("org.example")._class ("SomeClass");
    c.recordComponent (String.class, "foo");
  }

  @Test (expected = IllegalStateException.class)
  public void testCantAddCompactConstructorToNonRecordClass () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass c = cm._package ("org.example")._class ("SomeClass");
    c.compactConstructor (0);
  }

  @Test (expected = IllegalStateException.class)
  public void testCantAddRecordComponentVarargToNonRecordClass () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass c = cm._package ("org.example")._class ("SomeClass");
    c.recordComponentVararg (cm.INT, "values");
  }

  @Test (expected = IllegalStateException.class)
  public void testCantAddDuplicateCompactConstructor () throws JCodeModelException
  {
    final JCodeModel cm = new JCodeModel ();
    final JDefinedClass rec = cm._package ("org.example")._record ("Range");
    rec.recordComponent (cm.INT, "lo");
    rec.recordComponent (cm.INT, "hi");
    rec.compactConstructor (JMod.PUBLIC);
    // Should throw because one is already present
    rec.compactConstructor (JMod.PUBLIC);
  }
}
