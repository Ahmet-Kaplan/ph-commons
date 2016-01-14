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
package com.helger.commons.filter;

import java.io.Serializable;
import java.util.function.Predicate;

/**
 * A generic filter interface for simple object selection. If you need an
 * additional parameter for determining whether to filter an object or not, you
 * may use the {@link IFilterWithParameter} instead. With Java 8 this is
 * basically a serializable {@link Predicate}.
 *
 * @author Philip
 * @param <DATATYPE>
 *        The type of object to filter.
 */
@FunctionalInterface
public interface IFilter <DATATYPE> extends Serializable, Predicate <DATATYPE>
{
  /* empty */
}
