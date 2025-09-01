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

import java.lang.reflect.Modifier;

import com.helger.base.enforce.ValueEnforcer;

import jakarta.annotation.Nonnull;

/**
 * Modifier groups.
 */
public class JMods implements IJGenerable
{
  //
  // mask
  //
  private static final int VAR = JMod.FINAL;
  private static final int FIELD = JMod.PUBLIC |
                                   JMod.PRIVATE |
                                   JMod.PROTECTED |
                                   JMod.STATIC |
                                   JMod.FINAL |
                                   JMod.TRANSIENT |
                                   JMod.VOLATILE;
  private static final int METHOD = JMod.PUBLIC |
                                    JMod.PRIVATE |
                                    JMod.PROTECTED |
                                    JMod.FINAL |
                                    JMod.ABSTRACT |
                                    JMod.STATIC |
                                    JMod.NATIVE |
                                    JMod.SYNCHRONIZED |
                                    JMod.DEFAULT;
  private static final int CLASS = JMod.PUBLIC |
                                   JMod.PRIVATE |
                                   JMod.PROTECTED |
                                   JMod.STATIC |
                                   JMod.FINAL |
                                   JMod.ABSTRACT;
  private static final int INTERFACE = JMod.PUBLIC | JMod.PRIVATE | JMod.PROTECTED;

  /** bit-packed representation of modifiers. */
  private int m_nMods;

  protected JMods (final int nMods)
  {
    m_nMods = nMods;
  }

  /**
   * @return the bit-packed representation of modifiers.
   */
  public int getValue ()
  {
    return m_nMods;
  }

  private static void _check (final int nMods, final int nLegal, final String sWhat)
  {
    ValueEnforcer.isFalse ((nMods & ~nLegal) != 0,
                           () -> "Illegal modifiers for " + sWhat + ": " + new JMods (nMods).toString ());
    /* ## check for illegal combinations too */
  }

  @Nonnull
  public static JMods forVar (final int nMods)
  {
    _check (nMods, VAR, "variable");
    return new JMods (nMods);
  }

  @Nonnull
  public static JMods forField (final int nMods)
  {
    _check (nMods, FIELD, "field");
    return new JMods (nMods);
  }

  @Nonnull
  public static JMods forMethod (final int nMods)
  {
    _check (nMods, METHOD, "method");
    return new JMods (nMods);
  }

  @Nonnull
  public static JMods forClass (final int nMods)
  {
    _check (nMods, CLASS, "class");
    return new JMods (nMods);
  }

  @Nonnull
  public static JMods forInterface (final int nMods)
  {
    _check (nMods, INTERFACE, "interface");
    return new JMods (nMods);
  }

  public boolean isAbstract ()
  {
    return (m_nMods & JMod.ABSTRACT) != 0;
  }

  public boolean isFinal ()
  {
    return (m_nMods & JMod.FINAL) != 0;
  }

  public boolean isNative ()
  {
    return (m_nMods & JMod.NATIVE) != 0;
  }

  public boolean isPackagePrivate ()
  {
    return (m_nMods & JMod.PUBLIC|JMod.PRIVATE|JMod.PROTECTED) == 0;
  }

  public boolean isPublic ()
  {
    return (m_nMods & JMod.PUBLIC) != 0;
  }

  public boolean isPrivate ()
  {
    return (m_nMods & JMod.PRIVATE) != 0;
  }

  public boolean isProtected ()
  {
    return (m_nMods & JMod.PROTECTED) != 0;
  }

  public boolean isStatic ()
  {
    return (m_nMods & JMod.STATIC) != 0;
  }

  public boolean isSynchronized ()
  {
    return (m_nMods & JMod.SYNCHRONIZED) != 0;
  }

  private void _setFlag (final int bit, final boolean bNewValue)
  {
    m_nMods = (m_nMods & ~bit) | (bNewValue ? bit : 0);
  }

  public void setSynchronized (final boolean bNewValue)
  {
    _setFlag (JMod.SYNCHRONIZED, bNewValue);
  }

  public boolean isStrictFP ()
  {
    return (m_nMods & JMod.STRICTFP) != 0;
  }

  public void setStrictFP (final boolean bNewValue)
  {
    _setFlag (JMod.STRICTFP, bNewValue);
  }

  /**
   * @return <code>true</code> if this is a Java8 interface default method.
   */
  public boolean isDefault ()
  {
    return (m_nMods & JMod.DEFAULT) != 0;
  }

  /**
   * @param bNewValue
   *        <code>true</code> if this is a Java8 interface default method, <code>false</code>
   *        otherwise.
   */
  public void setDefault (final boolean bNewValue)
  {
    _setFlag (JMod.DEFAULT, bNewValue);
  }

  public void setPrivate ()
  {
    _setFlag (JMod.PUBLIC, false);
    _setFlag (JMod.PROTECTED, false);
    _setFlag (JMod.PRIVATE, true);
  }

  public void setPackagePrivate ()
  {
    _setFlag (JMod.PUBLIC, false);
    _setFlag (JMod.PROTECTED, false);
    _setFlag (JMod.PRIVATE, false);
  }

  public void setProtected ()
  {
    _setFlag (JMod.PUBLIC, false);
    _setFlag (JMod.PROTECTED, true);
    _setFlag (JMod.PRIVATE, false);
  }

  public void setPublic ()
  {
    _setFlag (JMod.PUBLIC, true);
    _setFlag (JMod.PROTECTED, false);
    _setFlag (JMod.PRIVATE, false);
  }

  public void setFinal (final boolean bNewValue)
  {
    _setFlag (JMod.FINAL, bNewValue);
  }

  @Override
  public void generate (@Nonnull final IJFormatter f)
  {
    // The order is taken from popular CodeStyle tools
    if ((m_nMods & JMod.PUBLIC) != 0)
      f.print ("public");

    if ((m_nMods & JMod.PROTECTED) != 0)
      f.print ("protected");

    if ((m_nMods & JMod.PRIVATE) != 0)
      f.print ("private");

    if ((m_nMods & JMod.ABSTRACT) != 0)
      f.print ("abstract");

    if ((m_nMods & JMod.STATIC) != 0)
      f.print ("static");

    if ((m_nMods & JMod.FINAL) != 0)
      f.print ("final");

    if ((m_nMods & JMod.TRANSIENT) != 0)
      f.print ("transient");

    if ((m_nMods & JMod.VOLATILE) != 0)
      f.print ("volatile");

    if ((m_nMods & JMod.SYNCHRONIZED) != 0)
      f.print ("synchronized");

    if ((m_nMods & JMod.NATIVE) != 0)
      f.print ("native");

    if ((m_nMods & JMod.STRICTFP) != 0)
      f.print ("strictfp");

    if ((m_nMods & JMod.DEFAULT) != 0)
      f.print ("default");
  }

  public enum ModifierMap
  {
    NONE (JMod.NONE, 0),
    PUBLIC (JMod.PUBLIC, Modifier.PUBLIC),
    PROTECTED (JMod.PROTECTED, Modifier.PROTECTED),
    PRIVATE (JMod.PRIVATE, Modifier.PRIVATE),
    FINAL (JMod.FINAL, Modifier.FINAL),
    STATIC (JMod.STATIC, Modifier.STATIC),
    ABSTRACT (JMod.ABSTRACT, Modifier.ABSTRACT),
    NATIVE (JMod.NATIVE, Modifier.NATIVE),
    SYNCHRONIZED (JMod.SYNCHRONIZED, Modifier.SYNCHRONIZED),
    TRANSIENT (JMod.TRANSIENT, Modifier.TRANSIENT),
    VOLATILE (JMod.VOLATILE, Modifier.VOLATILE),
    /*
     * default does not exist in the reflect : it's a public non-abstrct non-static method in an
     * interface
     */
    DEFAULT (JMod.DEFAULT, 0),
    STRICTFP (JMod.STRICTFP, Modifier.STRICT);

    public final int m_nJMod;
    public final int m_nModifier;

    ModifierMap (final int jmod, final int modifier)
    {
      m_nJMod = jmod;
      m_nModifier = modifier;
    }

    public boolean isPresentJMod (final int jmods)
    {
      return (m_nJMod & jmods) != 0;
    }

    public boolean isPresentModifiers (final int modifiers)
    {
      return m_nModifier != 0 && (m_nModifier & modifiers) != 0;
    }
  }

  public static int toModifier (final int jmod)
  {
    int ret = 0;
    for (final ModifierMap e : ModifierMap.values ())
    {
      if (e.isPresentJMod (jmod))
        ret |= e.m_nModifier;
    }
    return ret;
  }

  public static int fromModifier (final int modifier)
  {
    int ret = 0;
    for (final ModifierMap e : ModifierMap.values ())
    {
      if (e.isPresentModifiers (modifier))
        ret |= e.m_nJMod;
    }
    return ret;
  }
}
