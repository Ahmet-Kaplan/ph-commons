/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
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
package com.helger.commons.collection.pair;

import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.compare.PartComparatorComparable;

/**
 * Comparator comparing {@link IPair} objects by the first element
 *
 * @author Philip Helger
 * @param <DATA1TYPE>
 *        pair first type
 * @param <DATA2TYPE>
 *        pair second type
 */
@NotThreadSafe
public class ComparatorPairFirstComparable <DATA1TYPE extends Comparable <? super DATA1TYPE>, DATA2TYPE> extends
                                           PartComparatorComparable <IPair <DATA1TYPE, DATA2TYPE>, DATA1TYPE>
{
  public ComparatorPairFirstComparable ()
  {
    super (aObject -> aObject.getFirst ());
  }
}
