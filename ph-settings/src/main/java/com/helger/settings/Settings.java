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
package com.helger.settings;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.annotation.ReturnsMutableObject;
import com.helger.commons.callback.CallbackList;
import com.helger.commons.collection.ext.CommonsHashMap;
import com.helger.commons.collection.ext.ICommonsMap;
import com.helger.commons.collection.ext.ICommonsSet;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;

/**
 * The default implementation of the {@link IMutableSettings} object.
 *
 * @author philip
 */
public class Settings implements IMutableSettings
{
  private final String m_sName;
  private final ICommonsMap <String, Object> m_aMap = new CommonsHashMap <> ();
  private final CallbackList <ISettingsAfterChangeCallback> m_aAfterChangeCallbacks = new CallbackList <> ();

  public Settings (@Nonnull @Nonempty final String sName)
  {
    m_sName = ValueEnforcer.notEmpty (sName, "Name");
  }

  @Nonnull
  @Nonempty
  public final String getName ()
  {
    return m_sName;
  }

  @Nonnull
  @ReturnsMutableObject ("design")
  public final CallbackList <ISettingsAfterChangeCallback> getAfterChangeCallbackList ()
  {
    return m_aAfterChangeCallbacks;
  }

  @Nonnegative
  public int getSize ()
  {
    return m_aMap.size ();
  }

  public boolean isEmpty ()
  {
    return m_aMap.isEmpty ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public final ICommonsSet <String> getAllFieldNames ()
  {
    return m_aMap.copyOfKeySet ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsMap <String, Object> getAllEntries ()
  {
    return m_aMap.getClone ();
  }

  public void forEach (@Nonnull final BiConsumer <? super String, ? super Object> aConsumer)
  {
    m_aMap.forEach (aConsumer);
  }

  public void forEach (@Nullable final BiPredicate <? super String, ? super Object> aFilter,
                       @Nonnull final BiConsumer <? super String, ? super Object> aConsumer)
  {
    m_aMap.forEach (aFilter, aConsumer);
  }

  public boolean containsField (@Nullable final String sFieldName)
  {
    return m_aMap.containsKey (sFieldName);
  }

  @Nullable
  public Object getValue (@Nullable final String sFieldName)
  {
    return m_aMap.get (sFieldName);
  }

  @Nullable
  public IMutableSettings getSettingsValue (@Nullable final String sFieldName)
  {
    return getConvertedValue (sFieldName, IMutableSettings.class);
  }

  public void restoreValue (@Nonnull @Nonempty final String sFieldName, @Nonnull final Object aNewValue)
  {
    ValueEnforcer.notEmpty (sFieldName, "FieldName");
    ValueEnforcer.notNull (aNewValue, "NewValue");

    m_aMap.put (sFieldName, aNewValue);
  }

  @Nonnull
  public EChange setValues (@Nonnull final ISettings aOtherSettings)
  {
    ValueEnforcer.notNull (aOtherSettings, "OtherSettings");

    EChange eChange = EChange.UNCHANGED;
    for (final String sFieldName : aOtherSettings.getAllFieldNames ())
      eChange = eChange.or (setValue (sFieldName, aOtherSettings.getValue (sFieldName)));
    return eChange;
  }

  @Nonnull
  public EChange removeValue (@Nullable final String sFieldName)
  {
    return m_aMap.removeObject (sFieldName);
  }

  @Nonnull
  public EChange clear ()
  {
    return m_aMap.removeAll ();
  }

  @Nonnull
  public EChange setValue (@Nonnull @Nonempty final String sFieldName, @Nullable final Object aNewValue)
  {
    ValueEnforcer.notEmpty (sFieldName, "FieldName");

    // Get the old value
    final Object aOldValue = getValue (sFieldName);
    if (EqualsHelper.equals (aOldValue, aNewValue))
      return EChange.UNCHANGED;

    // Value changed -> trigger update
    if (aNewValue == null)
      m_aMap.remove (sFieldName);
    else
      m_aMap.put (sFieldName, aNewValue);

    // Invoke callbacks
    m_aAfterChangeCallbacks.forEach (x -> x.onAfterSettingsChanged (sFieldName, aOldValue, aNewValue));
    return EChange.CHANGED;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final Settings rhs = (Settings) o;
    return m_sName.equals (rhs.m_sName) && EqualsHelper.equals (m_aMap, rhs.m_aMap);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sName).append (m_aMap).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("name", m_sName).append ("map", m_aMap).toString ();
  }
}
