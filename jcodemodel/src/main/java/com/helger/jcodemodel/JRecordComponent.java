/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 * Portions Copyright 2013-2025 Philip Helger + contributors
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

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.Nonempty;
import com.helger.base.enforce.ValueEnforcer;

/**
 * A record component in a Java record declaration.
 * <p>
 * Record components are similar to constructor parameters but also define the fields and accessor
 * methods of the record. In the declaration {@code record Point(int x, int y)}, {@code x} and
 * {@code y} are record components.
 * <p>
 * Annotations on record components can be propagated to the generated field, accessor method, or
 * constructor parameter depending on the annotation's target.
 *
 * @since 4.2.0
 */
public class JRecordComponent implements IJAnnotatable, IJGenerable
{
  /**
   * The record that owns this component.
   */
  private final JDefinedClass m_aOwner;

  /**
   * Type of this component.
   */
  private final AbstractJType m_aType;

  /**
   * Name of this component.
   */
  private final String m_sName;

  /**
   * Whether this is a varargs component (only valid for the last component).
   */
  private final boolean m_bVararg;

  /**
   * Annotations on this component. Lazily created.
   */
  private List <JAnnotationUse> m_aAnnotations;

  /**
   * Constructor.
   *
   * @param aOwner
   *        The record that owns this component. May not be <code>null</code>.
   * @param aType
   *        Type of this component. May not be <code>null</code>.
   * @param sName
   *        Name of this component. May not be <code>null</code>.
   * @param bVararg
   *        Whether this is a varargs component.
   */
  JRecordComponent (@NonNull final JDefinedClass aOwner,
                    @NonNull final AbstractJType aType,
                    @NonNull @Nonempty final String sName,
                    final boolean bVararg)
  {
    ValueEnforcer.notNull (aOwner, "Owner");
    ValueEnforcer.notNull (aType, "Type");
    ValueEnforcer.isTrue (JJavaName.isJavaIdentifier (sName), () -> "Illegal component name '" + sName + "'");

    m_aOwner = aOwner;
    m_aType = aType;
    m_sName = sName;
    m_bVararg = bVararg;
  }

  /**
   * @return The record that owns this component. Never <code>null</code>.
   */
  @NonNull
  public JDefinedClass owner ()
  {
    return m_aOwner;
  }

  /**
   * @return The type of this component. Never <code>null</code>.
   */
  @NonNull
  public AbstractJType type ()
  {
    return m_aType;
  }

  /**
   * @return The name of this component. Neither <code>null</code> nor empty.
   */
  @NonNull
  @Nonempty
  public String name ()
  {
    return m_sName;
  }

  /**
   * @return <code>true</code> if this is a varargs component, <code>false</code> otherwise.
   */
  public boolean isVararg ()
  {
    return m_bVararg;
  }

  @Override
  @NonNull
  public JAnnotationUse annotate (@NonNull final AbstractJClass aClazz)
  {
    if (m_aAnnotations == null)
      m_aAnnotations = new ArrayList <> ();
    final JAnnotationUse a = new JAnnotationUse (aClazz);
    m_aAnnotations.add (a);
    return a;
  }

  @Override
  @NonNull
  public JAnnotationUse annotate (@NonNull final Class <? extends Annotation> aClazz)
  {
    return annotate (m_aType.owner ().ref (aClazz));
  }

  @NonNull
  public List <JAnnotationUse> annotationsMutable ()
  {
    if (m_aAnnotations == null)
      m_aAnnotations = new ArrayList <> ();
    return m_aAnnotations;
  }

  @Override
  @NonNull
  public List <JAnnotationUse> annotations ()
  {
    return Collections.unmodifiableList (annotationsMutable ());
  }

  @Override
  public void generate (@NonNull final IJFormatter f)
  {
    // Output annotations
    if (m_aAnnotations != null)
    {
      for (final JAnnotationUse annotation : m_aAnnotations)
      {
        f.generable (annotation).print (' ');
      }
    }

    // Output type and name
    if (m_bVararg)
    {
      // For varargs, output the component type followed by ...
      f.generable (m_aType.elementType ()).print ("...");
    }
    else
    {
      f.generable (m_aType);
    }
    f.print (' ').id (m_sName);
  }
}
