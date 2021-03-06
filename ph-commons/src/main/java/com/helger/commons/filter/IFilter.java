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
package com.helger.commons.filter;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
  @Nonnull
  default IFilter <DATATYPE> and (@Nonnull final IFilter <? super DATATYPE> other)
  {
    Objects.requireNonNull (other);
    return (t) -> test (t) && other.test (t);
  }

  @Nonnull
  default IFilter <DATATYPE> or (@Nonnull final IFilter <? super DATATYPE> other)
  {
    Objects.requireNonNull (other);
    return (t) -> test (t) || other.test (t);
  }

  @Nonnull
  default IFilter <DATATYPE> negate ()
  {
    return (t) -> !test (t);
  }

  @Nonnull
  static <DATATYPE> IFilter <DATATYPE> all ()
  {
    return (t) -> true;
  }

  @Nonnull
  static <DATATYPE> IFilter <DATATYPE> none ()
  {
    return (t) -> false;
  }

  @Nonnull
  static <DATATYPE> IFilter <DATATYPE> notNull ()
  {
    return (t) -> t != null;
  }

  @Nonnull
  static <DATATYPE> IFilter <DATATYPE> isNull ()
  {
    return (t) -> t == null;
  }

  @Nullable
  static <DATATYPE> Predicate <DATATYPE> and (@Nullable final Predicate <DATATYPE> aFirst,
                                              @Nullable final Predicate <DATATYPE> aSecond)
  {
    if (aFirst != null)
    {
      if (aSecond != null)
        return aFirst.and (aSecond);
      return aFirst;
    }
    return aSecond;
  }

  @Nullable
  static <DATATYPE> Predicate <DATATYPE> or (@Nullable final Predicate <DATATYPE> aFirst,
                                             @Nullable final Predicate <DATATYPE> aSecond)
  {
    if (aFirst != null)
    {
      if (aSecond != null)
        return aFirst.or (aSecond);
      return aFirst;
    }
    return aSecond;
  }
}
