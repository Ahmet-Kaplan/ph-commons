/**
 * Copyright (C) 2014-2017 Philip Helger (www.helger.com)
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
package com.helger.commons.collection.multimap;

import org.junit.Test;

/**
 * Test class for class {@link MultiConcurrentHashMapVectorBased}.
 *
 * @author Philip Helger
 */
public final class MultiConcurrentHashMapVectorBasedTest extends AbstractMultiMapTestCase
{
  @Test
  public void testAll ()
  {
    MultiConcurrentHashMapVectorBased <String, String> aMultiMap = new MultiConcurrentHashMapVectorBased <> ();
    testEmpty (aMultiMap);
    aMultiMap = new MultiConcurrentHashMapVectorBased <> (getKey1 (), getValue1 ());
    testOne (aMultiMap);
    aMultiMap = new MultiConcurrentHashMapVectorBased <> (getKey1 (), getValueList1 ());
    testOne (aMultiMap);
    aMultiMap = new MultiConcurrentHashMapVectorBased <> (getMapList1 ());
    testOne (aMultiMap);
    aMultiMap = new MultiConcurrentHashMapVectorBased <> ();
    testList (aMultiMap);
  }
}
