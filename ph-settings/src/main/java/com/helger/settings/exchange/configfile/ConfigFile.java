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
package com.helger.settings.exchange.configfile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ext.CommonsHashMap;
import com.helger.commons.collection.ext.ICommonsMap;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.system.SystemProperties;
import com.helger.commons.traits.IConvertibleByKeyTrait;
import com.helger.commons.ws.WSHelper;
import com.helger.settings.ISettings;

/**
 * A configuration file that consists of a readable resource that backed the
 * settings and the main {@link ISettings} object.
 *
 * @author Philip Helger
 */
@Immutable
public class ConfigFile implements IConvertibleByKeyTrait <String>
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (ConfigFile.class);

  private final IReadableResource m_aReadResource;
  private final ISettings m_aSettings;

  /**
   * Constructor for the settings that were read
   *
   * @param aRes
   *        The resource that was read. May be <code>null</code>.
   * @param aSettings
   *        The settings that were read. May be <code>null</code>.
   */
  protected ConfigFile (@Nullable final IReadableResource aRes, @Nullable final ISettings aSettings)
  {
    m_aReadResource = aRes;
    m_aSettings = aSettings;
  }

  /**
   * @return <code>true</code> if reading succeeded, <code>false</code> if
   *         reading failed (warning was already logged)
   */
  public boolean isRead ()
  {
    return m_aSettings != null;
  }

  /**
   * @return The resource from which the config file was read. May be
   *         <code>null</code> if reading failed.
   */
  @Nullable
  public IReadableResource getReadResource ()
  {
    return m_aReadResource;
  }

  /**
   * @return The underlying {@link ISettings} object. May be <code>null</code>
   *         if reading failed.
   */
  @Nullable
  public ISettings getSettings ()
  {
    return m_aSettings;
  }

  @Nullable
  public Object getValue (@Nullable final String sFieldName)
  {
    return m_aSettings == null ? null : m_aSettings.getValue (sFieldName);
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsMap <String, Object> getAllEntries ()
  {
    if (m_aSettings == null)
      return new CommonsHashMap<> ();
    return m_aSettings.getAllEntries ();
  }

  /**
   * This is a utility method, that applies all Java network/proxy system
   * properties which are present in this configuration file. It does it only
   * when the configuration file was read correctly.
   */
  public void applyAllNetworkSystemProperties ()
  {
    if (isRead ())
      for (final String sProperty : WSHelper.getAllJavaNetSystemProperties ())
      {
        final String sConfigFileValue = getAsString (sProperty);
        if (sConfigFileValue != null)
        {
          SystemProperties.setPropertyValue (sProperty, sConfigFileValue);
          s_aLogger.info ("Set Java network/proxy system property: " + sProperty + "=" + sConfigFileValue);
        }
      }
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ReadResource", m_aReadResource)
                                       .append ("Settings", m_aSettings)
                                       .toString ();
  }
}
