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
package com.helger.jcodemodel.tests.record;

import org.junit.Assert;
import org.junit.Test;

import com.helger.jcodemodel.exceptions.JCodeModelException;
import com.helger.jcodemodel.tests.basic.record.BasicPoint;
import com.helger.jcodemodel.tests.basic.record.Empty;
import com.helger.jcodemodel.tests.basic.record.JRecordTestGen;
import com.helger.jcodemodel.tests.basic.record.Person;

/**
 * Test class for Java record support. Java records (JEP 395, Java 16+) are a special kind of class
 * that acts as a transparent carrier for immutable data. Records automatically provide: - A
 * canonical constructor - Private final fields for each component - Public accessor methods for
 * each component (same name as component) - equals(), hashCode(), and toString() implementations
 */
public final class JRecordTest
{

  /**
   * tests {@link JRecordTestGen#genBasicRecord()}
   */
  @Test
  public void testBasicRecord () throws JCodeModelException
  {
    BasicPoint test = new BasicPoint(2, 3);
    Assert.assertTrue(test instanceof Record);
  }

  /**
   * tests {@link JRecordTestGen#genEmptyRecord()}
   */
  @Test
  public void testEmptyRecord() throws JCodeModelException
  {
    Empty test = new Empty();
    Assert.assertTrue(test instanceof Record);
  }

  /**
   * tests {@link JRecordTestGen#genRecordWithObjectComponents()}
   */
  @Test
  public void testRecordWithObjectComponents () throws JCodeModelException
  {
    Person test = new Person("John", 42);
    Assert.assertTrue(test instanceof Record);
  }
}
