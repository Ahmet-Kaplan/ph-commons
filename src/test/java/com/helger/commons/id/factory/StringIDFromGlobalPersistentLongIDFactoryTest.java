/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.commons.id.factory;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.id.factory.GlobalIDFactory;
import com.helger.commons.id.factory.MemoryStaticLongIDFactory;
import com.helger.commons.id.factory.StringIDFromGlobalPersistentLongIDFactory;
import com.helger.commons.mock.CommonsTestHelper;

/**
 * Test class for class {@link StringIDFromGlobalPersistentLongIDFactory}.
 * 
 * @author Philip Helger
 */
public final class StringIDFromGlobalPersistentLongIDFactoryTest
{
  @Test
  public void testAll ()
  {
    GlobalIDFactory.setPersistentLongIDFactory (new MemoryStaticLongIDFactory ());
    final StringIDFromGlobalPersistentLongIDFactory x = new StringIDFromGlobalPersistentLongIDFactory ("idd");
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (x,
                                                                 new StringIDFromGlobalPersistentLongIDFactory ("idd"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (x,
                                                                     new StringIDFromGlobalPersistentLongIDFactory ("prefix"));
    assertTrue (x.getNewID ().startsWith ("idd"));

    try
    {
      new StringIDFromGlobalPersistentLongIDFactory (null);
      fail ();
    }
    catch (final NullPointerException ex)
    {}
  }
}