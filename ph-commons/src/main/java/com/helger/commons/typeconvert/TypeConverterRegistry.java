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
package com.helger.commons.typeconvert;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableObject;
import com.helger.commons.annotation.Singleton;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.collection.multimap.IMultiMapListBased;
import com.helger.commons.collection.multimap.MultiTreeMapArrayListBased;
import com.helger.commons.debug.GlobalDebug;
import com.helger.commons.lang.ClassHelper;
import com.helger.commons.lang.ClassHierarchyCache;
import com.helger.commons.lang.ServiceLoaderHelper;
import com.helger.commons.state.EContinue;
import com.helger.commons.wrapper.Wrapper;

/**
 * This class contains all the default type converters for the default types
 * that are required. The {@link TypeConverter} class uses this factory for
 * converting objects.
 *
 * @author Philip Helger
 */
@ThreadSafe
@Singleton
public final class TypeConverterRegistry implements ITypeConverterRegistry
{
  private static final class SingletonHolder
  {
    static final TypeConverterRegistry s_aInstance = new TypeConverterRegistry ();
  }

  private static final Logger s_aLogger = LoggerFactory.getLogger (TypeConverterRegistry.class);

  private static boolean s_bDefaultInstantiated = false;

  private final ReadWriteLock m_aRWLock = new ReentrantReadWriteLock ();

  // Use a weak hash map, because the key is a class
  @GuardedBy ("m_aRWLock")
  private final Map <Class <?>, Map <Class <?>, ITypeConverter>> m_aConverter = new WeakHashMap <Class <?>, Map <Class <?>, ITypeConverter>> ();
  @GuardedBy ("m_aRWLock")
  private final IMultiMapListBased <ITypeConverterRule.ESubType, ITypeConverterRule> m_aRules = new MultiTreeMapArrayListBased <ITypeConverterRule.ESubType, ITypeConverterRule> ();

  private TypeConverterRegistry ()
  {
    reinitialize ();
  }

  public static boolean isInstantiated ()
  {
    return s_bDefaultInstantiated;
  }

  @Nonnull
  public static TypeConverterRegistry getInstance ()
  {
    final TypeConverterRegistry ret = SingletonHolder.s_aInstance;
    s_bDefaultInstantiated = true;
    return ret;
  }

  @Nonnull
  @ReturnsMutableObject ("internal use only")
  private Map <Class <?>, ITypeConverter> _getOrCreateConverterMap (@Nonnull final Class <?> aClass)
  {
    Map <Class <?>, ITypeConverter> ret;
    m_aRWLock.readLock ().lock ();
    try
    {
      ret = m_aConverter.get (aClass);
    }
    finally
    {
      m_aRWLock.readLock ().unlock ();
    }

    if (ret == null)
    {
      m_aRWLock.writeLock ().lock ();
      try
      {
        // Try again in write lock
        ret = m_aConverter.get (aClass);
        if (ret == null)
        {
          // Weak hash map because key is a class
          ret = new WeakHashMap <Class <?>, ITypeConverter> ();
          m_aConverter.put (aClass, ret);
        }
      }
      finally
      {
        m_aRWLock.writeLock ().unlock ();
      }
    }
    return ret;
  }

  /**
   * Register a default type converter.
   *
   * @param aSrcClass
   *        A non-<code>null</code> source class to convert from. Must be an
   *        instancable class.
   * @param aDstClass
   *        A non-<code>null</code> destination class to convert to. Must be an
   *        instancable class. May not equal the source class.
   * @param aConverter
   *        The convert to use. May not be <code>null</code>.
   */
  private void _registerTypeConverter (@Nonnull final Class <?> aSrcClass,
                                       @Nonnull final Class <?> aDstClass,
                                       @Nonnull final ITypeConverter aConverter)
  {
    ValueEnforcer.notNull (aSrcClass, "SrcClass");
    if (!ClassHelper.isPublic (aSrcClass))
      throw new IllegalArgumentException ("Source " + aSrcClass + " is no public class!");
    ValueEnforcer.notNull (aDstClass, "DstClass");
    if (!ClassHelper.isPublic (aDstClass))
      throw new IllegalArgumentException ("Destination " + aDstClass + " is no public class!");
    if (aSrcClass.equals (aDstClass))
      throw new IllegalArgumentException ("Source and destination class are equal and therefore no converter is required.");
    ValueEnforcer.notNull (aConverter, "Converter");
    if (aConverter instanceof ITypeConverterRule)
      throw new IllegalArgumentException ("Type converter rules must be registered via registerTypeConverterRule");
    if (ClassHelper.areConvertibleClasses (aSrcClass, aDstClass))
      s_aLogger.warn ("No type converter needed between " +
                      aSrcClass +
                      " and " +
                      aDstClass +
                      " because types are convertible!");

    // The main class should not already be registered
    final Map <Class <?>, ITypeConverter> aSrcMap = _getOrCreateConverterMap (aSrcClass);
    if (aSrcMap.containsKey (aDstClass))
      throw new IllegalArgumentException ("A mapping from " + aSrcClass + " to " + aDstClass + " is already defined!");

    m_aRWLock.writeLock ().lock ();
    try
    {
      // Automatically register the destination class, and all parent
      // classes/interfaces
      for (final WeakReference <Class <?>> aCurWRDstClass : ClassHierarchyCache.getClassHierarchyIterator (aDstClass))
      {
        final Class <?> aCurDstClass = aCurWRDstClass.get ();
        if (aCurDstClass != null)
          if (!aSrcMap.containsKey (aCurDstClass))
          {
            if (aSrcMap.put (aCurDstClass, aConverter) != null)
              s_aLogger.warn ("Overwriting converter from " + aSrcClass + " to " + aCurDstClass);
            else
              if (s_aLogger.isDebugEnabled ())
                s_aLogger.debug ("Registered type converter from '" +
                                 aSrcClass.toString () +
                                 "' to '" +
                                 aCurDstClass.toString () +
                                 "'");
          }
      }
    }
    finally
    {
      m_aRWLock.writeLock ().unlock ();
    }
  }

  public void registerTypeConverter (@Nonnull final Class <?> aSrcClass,
                                     @Nonnull final Class <?> aDstClass,
                                     @Nonnull final ITypeConverter aConverter)
  {
    _registerTypeConverter (aSrcClass, aDstClass, aConverter);
  }

  public void registerTypeConverter (@Nonnull final Class <?> [] aSrcClasses,
                                     @Nonnull final Class <?> aDstClass,
                                     @Nonnull final ITypeConverter aConverter)
  {
    for (final Class <?> aSrcClass : aSrcClasses)
      _registerTypeConverter (aSrcClass, aDstClass, aConverter);
  }

  /**
   * Get the converter that can convert objects from aSrcClass to aDstClass.
   * Thereby no fuzzy logic is applied.
   *
   * @param aSrcClass
   *        Source class. May not be <code>null</code>.
   * @param aDstClass
   *        Destination class. May not be <code>null</code>.
   * @return <code>null</code> if no such type converter exists, the converter
   *         object otherwise.
   */
  @Nullable
  ITypeConverter getExactConverter (@Nullable final Class <?> aSrcClass, @Nullable final Class <?> aDstClass)
  {
    m_aRWLock.readLock ().lock ();
    try
    {
      final Map <Class <?>, ITypeConverter> aConverterMap = m_aConverter.get (aSrcClass);
      return aConverterMap == null ? null : aConverterMap.get (aDstClass);
    }
    finally
    {
      m_aRWLock.readLock ().unlock ();
    }
  }

  /**
   * Get the converter that can convert objects from aSrcClass to aDstClass
   * using the registered rules. The first match is returned.
   *
   * @param aSrcClass
   *        Source class. May not be <code>null</code>.
   * @param aDstClass
   *        Destination class. May not be <code>null</code>.
   * @return <code>null</code> if no such type converter exists, the converter
   *         object otherwise.
   */
  @Nullable
  ITypeConverter getRuleBasedConverter (@Nullable final Class <?> aSrcClass, @Nullable final Class <?> aDstClass)
  {
    if (aSrcClass == null || aDstClass == null)
      return null;

    m_aRWLock.readLock ().lock ();
    try
    {
      // Check all rules in the correct order
      for (final Map.Entry <ITypeConverterRule.ESubType, List <ITypeConverterRule>> aEntry : m_aRules.entrySet ())
        for (final ITypeConverterRule aRule : aEntry.getValue ())
          if (aRule.canConvert (aSrcClass, aDstClass))
            return aRule;

      return null;
    }
    finally
    {
      m_aRWLock.readLock ().unlock ();
    }
  }

  /**
   * Iterate all possible fuzzy converters from source class to destination
   * class.
   *
   * @param aSrcClass
   *        Source class.
   * @param aDstClass
   *        Destination class.
   * @param aCallback
   *        The callback to be invoked once a converter was found. Must return
   *        either {@link EContinue#CONTINUE} to continue iteration or
   *        {@link EContinue#BREAK} to break iteration at the current position.
   */
  private void _iterateFuzzyConverters (@Nonnull final Class <?> aSrcClass,
                                        @Nonnull final Class <?> aDstClass,
                                        @Nonnull final ITypeConverterCallback aCallback)
  {
    // For all possible source classes
    for (final WeakReference <Class <?>> aCurWRSrcClass : ClassHierarchyCache.getClassHierarchyIterator (aSrcClass))
    {
      final Class <?> aCurSrcClass = aCurWRSrcClass.get ();
      if (aCurSrcClass != null)
      {
        // Do we have a source converter?
        final Map <Class <?>, ITypeConverter> aConverterMap = m_aConverter.get (aCurSrcClass);
        if (aConverterMap != null)
        {
          // Check explicit destination classes
          final ITypeConverter aConverter = aConverterMap.get (aDstClass);
          if (aConverter != null)
          {
            // We found a match -> invoke the callback!
            if (aCallback.call (aCurSrcClass, aDstClass, aConverter).isBreak ())
              break;
          }
        }
      }
    }
  }

  /**
   * Get the converter that can convert objects from aSrcClass to aDstClass. If
   * no exact match is found, the super-classes and interface of source and
   * destination class are searched for matching type converters. The first
   * match is returned.
   *
   * @param aSrcClass
   *        Source class. May not be <code>null</code>.
   * @param aDstClass
   *        Destination class. May not be <code>null</code>.
   * @return <code>null</code> if no such type converter exists, the converter
   *         object otherwise.
   */
  @Nullable
  ITypeConverter getFuzzyConverter (@Nullable final Class <?> aSrcClass, @Nullable final Class <?> aDstClass)
  {
    if (aSrcClass == null || aDstClass == null)
      return null;

    m_aRWLock.readLock ().lock ();
    try
    {
      if (GlobalDebug.isDebugMode ())
      {
        // Perform a check, whether there is more than one potential converter
        // present!
        final List <String> aAllConverters = new ArrayList <String> ();
        _iterateFuzzyConverters (aSrcClass, aDstClass, new ITypeConverterCallback ()
        {
          @Nonnull
          public EContinue call (@Nonnull final Class <?> aCurSrcClass,
                                 @Nonnull final Class <?> aCurDstClass,
                                 @Nonnull final ITypeConverter aConverter)
          {
            final boolean bExact = aSrcClass.equals (aCurSrcClass) && aDstClass.equals (aCurDstClass);
            aAllConverters.add ("[" + aCurSrcClass.getName () + "->" + aCurDstClass.getName () + "]");
            return bExact ? EContinue.BREAK : EContinue.CONTINUE;
          }
        });
        if (aAllConverters.size () > 1)
          s_aLogger.warn ("The fuzzy type converter resolver returned more than 1 match for the conversion from " +
                          aSrcClass +
                          " to " +
                          aDstClass +
                          ": " +
                          aAllConverters);
      }

      // Iterate and find the first matching type converter
      final Wrapper <ITypeConverter> ret = new Wrapper <ITypeConverter> ();
      _iterateFuzzyConverters (aSrcClass, aDstClass, new ITypeConverterCallback ()
      {
        @Nonnull
        public EContinue call (@Nonnull final Class <?> aCurSrcClass,
                               @Nonnull final Class <?> aCurDstClass,
                               @Nonnull final ITypeConverter aConverter)
        {
          ret.set (aConverter);
          return EContinue.BREAK;
        }
      });
      return ret.get ();
    }
    finally
    {
      m_aRWLock.readLock ().unlock ();
    }
  }

  /**
   * Iterate all registered type converters. For informational purposes only.
   *
   * @param aCallback
   *        The callback invoked for all iterations.
   */
  public void iterateAllRegisteredTypeConverters (@Nonnull final ITypeConverterCallback aCallback)
  {
    // Create a copy of the map
    Map <Class <?>, Map <Class <?>, ITypeConverter>> aCopy;
    m_aRWLock.readLock ().lock ();
    try
    {
      aCopy = CollectionHelper.newMap (m_aConverter);
    }
    finally
    {
      m_aRWLock.readLock ().unlock ();
    }

    // And iterate the copy
    outer: for (final Map.Entry <Class <?>, Map <Class <?>, ITypeConverter>> aSrcEntry : aCopy.entrySet ())
    {
      final Class <?> aSrcClass = aSrcEntry.getKey ();
      for (final Map.Entry <Class <?>, ITypeConverter> aDstEntry : aSrcEntry.getValue ().entrySet ())
        if (aCallback.call (aSrcClass, aDstEntry.getKey (), aDstEntry.getValue ()).isBreak ())
          break outer;
    }
  }

  @Nonnegative
  public int getRegisteredTypeConverterCount ()
  {
    m_aRWLock.readLock ().lock ();
    try
    {
      int ret = 0;
      for (final Map <Class <?>, ITypeConverter> aMap : m_aConverter.values ())
        ret += aMap.size ();
      return ret;
    }
    finally
    {
      m_aRWLock.readLock ().unlock ();
    }
  }

  public void registerTypeConverterRule (@Nonnull final ITypeConverterRule aTypeConverterRule)
  {
    ValueEnforcer.notNull (aTypeConverterRule, "TypeConverterRule");

    m_aRWLock.writeLock ().lock ();
    try
    {
      m_aRules.putSingle (aTypeConverterRule.getSubType (), aTypeConverterRule);
    }
    finally
    {
      m_aRWLock.writeLock ().unlock ();
    }
  }

  @Nonnegative
  public long getRegisteredTypeConverterRuleCount ()
  {
    m_aRWLock.readLock ().lock ();
    try
    {
      return m_aRules.getTotalValueCount ();
    }
    finally
    {
      m_aRWLock.readLock ().unlock ();
    }
  }

  public void reinitialize ()
  {
    m_aRWLock.writeLock ().lock ();
    try
    {
      m_aConverter.clear ();
      m_aRules.clear ();
    }
    finally
    {
      m_aRWLock.writeLock ().unlock ();
    }

    // Register all custom type converter
    for (final ITypeConverterRegistrarSPI aSPI : ServiceLoaderHelper.getAllSPIImplementations (ITypeConverterRegistrarSPI.class))
      aSPI.registerTypeConverter (this);

    if (s_aLogger.isDebugEnabled ())
      s_aLogger.debug (getRegisteredTypeConverterCount () +
                       " type converters and " +
                       getRegisteredTypeConverterRuleCount () +
                       " rules registered");
  }
}