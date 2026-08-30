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

import org.junit.Assert;
import org.junit.Test;

import com.helger.jcodemodel.util.CodeModelTestsHelper;

public class JAtomIntTest
{
  @Test
  public void testRepresentation ()
  {
    // basic representation
    {
      JAtomInt i42 = new JAtomInt (42).separateEvery (0);
      Assert.assertEquals ("0b101010", CodeModelTestsHelper.toString (i42.binary ()));
      Assert.assertEquals ("42", CodeModelTestsHelper.toString (i42.decimal ()));
      Assert.assertEquals ("0x2a", CodeModelTestsHelper.toString (i42.hex ()));
      Assert.assertEquals ("052", CodeModelTestsHelper.toString (i42.octal ()));
    }
    {
      JAtomInt i0 = new JAtomInt (0).separateEvery (0);
      Assert.assertEquals ("0b0", CodeModelTestsHelper.toString (i0.binary ()));
      Assert.assertEquals ("0", CodeModelTestsHelper.toString (i0.decimal ()));
      Assert.assertEquals ("0x0", CodeModelTestsHelper.toString (i0.hex ()));
      Assert.assertEquals ("00", CodeModelTestsHelper.toString (i0.octal ()));
    }
    {
      JAtomInt iNeg2 = new JAtomInt (-2).separateEvery (0);
      Assert.assertEquals ("-0b10", CodeModelTestsHelper.toString (iNeg2.binary ()));
      Assert.assertEquals ("-2", CodeModelTestsHelper.toString (iNeg2.decimal ()));
      Assert.assertEquals ("-0x2", CodeModelTestsHelper.toString (iNeg2.hex ()));
      Assert.assertEquals ("-02", CodeModelTestsHelper.toString (iNeg2.octal ()));
    }

    // separators
    {
      JAtomInt ia = new JAtomInt (-1234567).separatorSize (2).separateEvery (2).decimal ();
      Assert.assertEquals ("-1__23__45__67", CodeModelTestsHelper.toString (ia));
    }
    {
      JAtomInt ia = new JAtomInt (-12345678).separatorSize (2).separateEvery (2).decimal ();
      Assert.assertEquals ("-12__34__56__78", CodeModelTestsHelper.toString (ia));
    }
    {
      JAtomInt ia = new JAtomInt (-10).separatorSize (1).separateEvery (3).binary ();
      Assert.assertEquals ("-0b1_010", CodeModelTestsHelper.toString (ia));
    }
    {
      JAtomInt ia = new JAtomInt (4).separatorSize (1).separateEvery (3).binary ();
      Assert.assertEquals ("0b100", CodeModelTestsHelper.toString (ia));
    }
    {
      JAtomInt ia = new JAtomInt (8).separatorSize (1).separateEvery (3).binary ();
      Assert.assertEquals ("0b1_000", CodeModelTestsHelper.toString (ia));
    }
  }

}
