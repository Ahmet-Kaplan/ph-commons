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
package com.helger.commons.io.resourceprovider;

import java.io.InputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ext.CommonsArrayList;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.string.ToStringGenerator;

/**
 * A readable resource provider that chains multiple
 * {@link IReadableResourceProvider}.
 *
 * @author Philip Helger
 */
@Immutable
public class ReadableResourceProviderChain implements IReadableResourceProvider
{
  protected final ICommonsList <IReadableResourceProvider> m_aReadingResourceProviders;

  public ReadableResourceProviderChain (@Nonnull final IReadableResourceProvider... aResProviders)
  {
    ValueEnforcer.notEmptyNoNullValue (aResProviders, "ResourceProviders");

    m_aReadingResourceProviders = new CommonsArrayList<> (aResProviders);
  }

  public ReadableResourceProviderChain (@Nonnull final Iterable <? extends IReadableResourceProvider> aResProviders)
  {
    ValueEnforcer.notEmptyNoNullValue (aResProviders, "ResourceProviders");

    m_aReadingResourceProviders = new CommonsArrayList<> (aResProviders);
  }

  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public ICommonsList <IReadableResourceProvider> getAllContainedReadingResourceProviders ()
  {
    return m_aReadingResourceProviders.getClone ();
  }

  public final boolean supportsReading (@Nullable final String sName)
  {
    // Check if any provider can handle this resource
    return m_aReadingResourceProviders.containsAny (e -> e.supportsReading (sName));
  }

  @Nonnull
  @OverrideOnDemand
  public IReadableResource getReadableResource (@Nonnull final String sName)
  {
    // Use the first resource provider that supports the name
    for (final IReadableResourceProvider aResProvider : m_aReadingResourceProviders)
      if (aResProvider.supportsReading (sName))
        return aResProvider.getReadableResource (sName);
    throw new IllegalArgumentException ("Cannot handle reading '" +
                                        sName +
                                        "' by any of " +
                                        m_aReadingResourceProviders);
  }

  @Nullable
  public InputStream getInputStream (@Nonnull final String sName)
  {
    // Use the first resource provider that supports the name and returns a
    // non-null resource provider
    for (final IReadableResourceProvider aResProvider : m_aReadingResourceProviders)
    {
      final InputStream aIS = aResProvider.getInputStream (sName);
      if (aIS != null)
        return aIS;
    }
    return null;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final ReadableResourceProviderChain rhs = (ReadableResourceProviderChain) o;
    return m_aReadingResourceProviders.equals (rhs.m_aReadingResourceProviders);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aReadingResourceProviders).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("readableResProviders", m_aReadingResourceProviders).toString ();
  }
}
