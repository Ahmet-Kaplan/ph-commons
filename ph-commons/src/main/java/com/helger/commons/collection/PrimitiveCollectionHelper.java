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
package com.helger.commons.collection;

import java.util.PriorityQueue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ext.CommonsArrayList;
import com.helger.commons.collection.ext.CommonsHashSet;
import com.helger.commons.collection.ext.CommonsLinkedHashSet;
import com.helger.commons.collection.ext.CommonsTreeSet;
import com.helger.commons.collection.ext.CommonsVector;
import com.helger.commons.collection.impl.NonBlockingStack;

/**
 * Collection helper for primitive data types (boolean, byte, char, double,
 * float, int, long and short).
 *
 * @author Philip Helger
 */
public final class PrimitiveCollectionHelper
{
  private PrimitiveCollectionHelper ()
  {}

  // The code below is generated by MainCreateCollectionHelperCode

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsArrayList <Boolean> newPrimitiveList (@Nullable final boolean... aValues)
  {
    final CommonsArrayList <Boolean> ret = CollectionHelper.newList ();
    if (aValues != null)
      for (final boolean aValue : aValues)
        ret.add (Boolean.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsArrayList <Byte> newPrimitiveList (@Nullable final byte... aValues)
  {
    final CommonsArrayList <Byte> ret = CollectionHelper.newList ();
    if (aValues != null)
      for (final byte aValue : aValues)
        ret.add (Byte.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsArrayList <Character> newPrimitiveList (@Nullable final char... aValues)
  {
    final CommonsArrayList <Character> ret = CollectionHelper.newList ();
    if (aValues != null)
      for (final char aValue : aValues)
        ret.add (Character.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsArrayList <Double> newPrimitiveList (@Nullable final double... aValues)
  {
    final CommonsArrayList <Double> ret = CollectionHelper.newList ();
    if (aValues != null)
      for (final double aValue : aValues)
        ret.add (Double.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsArrayList <Float> newPrimitiveList (@Nullable final float... aValues)
  {
    final CommonsArrayList <Float> ret = CollectionHelper.newList ();
    if (aValues != null)
      for (final float aValue : aValues)
        ret.add (Float.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsArrayList <Integer> newPrimitiveList (@Nullable final int... aValues)
  {
    final CommonsArrayList <Integer> ret = CollectionHelper.newList ();
    if (aValues != null)
      for (final int aValue : aValues)
        ret.add (Integer.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsArrayList <Long> newPrimitiveList (@Nullable final long... aValues)
  {
    final CommonsArrayList <Long> ret = CollectionHelper.newList ();
    if (aValues != null)
      for (final long aValue : aValues)
        ret.add (Long.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsArrayList <Short> newPrimitiveList (@Nullable final short... aValues)
  {
    final CommonsArrayList <Short> ret = CollectionHelper.newList ();
    if (aValues != null)
      for (final short aValue : aValues)
        ret.add (Short.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsVector <Boolean> newPrimitiveVector (@Nullable final boolean... aValues)
  {
    final CommonsVector <Boolean> ret = VectorHelper.newVector ();
    if (aValues != null)
      for (final boolean aValue : aValues)
        ret.add (Boolean.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsVector <Byte> newPrimitiveVector (@Nullable final byte... aValues)
  {
    final CommonsVector <Byte> ret = VectorHelper.newVector ();
    if (aValues != null)
      for (final byte aValue : aValues)
        ret.add (Byte.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsVector <Character> newPrimitiveVector (@Nullable final char... aValues)
  {
    final CommonsVector <Character> ret = VectorHelper.newVector ();
    if (aValues != null)
      for (final char aValue : aValues)
        ret.add (Character.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsVector <Double> newPrimitiveVector (@Nullable final double... aValues)
  {
    final CommonsVector <Double> ret = VectorHelper.newVector ();
    if (aValues != null)
      for (final double aValue : aValues)
        ret.add (Double.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsVector <Float> newPrimitiveVector (@Nullable final float... aValues)
  {
    final CommonsVector <Float> ret = VectorHelper.newVector ();
    if (aValues != null)
      for (final float aValue : aValues)
        ret.add (Float.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsVector <Integer> newPrimitiveVector (@Nullable final int... aValues)
  {
    final CommonsVector <Integer> ret = VectorHelper.newVector ();
    if (aValues != null)
      for (final int aValue : aValues)
        ret.add (Integer.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsVector <Long> newPrimitiveVector (@Nullable final long... aValues)
  {
    final CommonsVector <Long> ret = VectorHelper.newVector ();
    if (aValues != null)
      for (final long aValue : aValues)
        ret.add (Long.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsVector <Short> newPrimitiveVector (@Nullable final short... aValues)
  {
    final CommonsVector <Short> ret = VectorHelper.newVector ();
    if (aValues != null)
      for (final short aValue : aValues)
        ret.add (Short.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsHashSet <Boolean> newPrimitiveSet (@Nullable final boolean... aValues)
  {
    final CommonsHashSet <Boolean> ret = CollectionHelper.newSet ();
    if (aValues != null)
      for (final boolean aValue : aValues)
        ret.add (Boolean.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsHashSet <Byte> newPrimitiveSet (@Nullable final byte... aValues)
  {
    final CommonsHashSet <Byte> ret = CollectionHelper.newSet ();
    if (aValues != null)
      for (final byte aValue : aValues)
        ret.add (Byte.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsHashSet <Character> newPrimitiveSet (@Nullable final char... aValues)
  {
    final CommonsHashSet <Character> ret = CollectionHelper.newSet ();
    if (aValues != null)
      for (final char aValue : aValues)
        ret.add (Character.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsHashSet <Double> newPrimitiveSet (@Nullable final double... aValues)
  {
    final CommonsHashSet <Double> ret = CollectionHelper.newSet ();
    if (aValues != null)
      for (final double aValue : aValues)
        ret.add (Double.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsHashSet <Float> newPrimitiveSet (@Nullable final float... aValues)
  {
    final CommonsHashSet <Float> ret = CollectionHelper.newSet ();
    if (aValues != null)
      for (final float aValue : aValues)
        ret.add (Float.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsHashSet <Integer> newPrimitiveSet (@Nullable final int... aValues)
  {
    final CommonsHashSet <Integer> ret = CollectionHelper.newSet ();
    if (aValues != null)
      for (final int aValue : aValues)
        ret.add (Integer.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsHashSet <Long> newPrimitiveSet (@Nullable final long... aValues)
  {
    final CommonsHashSet <Long> ret = CollectionHelper.newSet ();
    if (aValues != null)
      for (final long aValue : aValues)
        ret.add (Long.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsHashSet <Short> newPrimitiveSet (@Nullable final short... aValues)
  {
    final CommonsHashSet <Short> ret = CollectionHelper.newSet ();
    if (aValues != null)
      for (final short aValue : aValues)
        ret.add (Short.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsLinkedHashSet <Boolean> newPrimitiveOrderedSet (@Nullable final boolean... aValues)
  {
    final CommonsLinkedHashSet <Boolean> ret = CollectionHelper.newOrderedSet ();
    if (aValues != null)
      for (final boolean aValue : aValues)
        ret.add (Boolean.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsLinkedHashSet <Byte> newPrimitiveOrderedSet (@Nullable final byte... aValues)
  {
    final CommonsLinkedHashSet <Byte> ret = CollectionHelper.newOrderedSet ();
    if (aValues != null)
      for (final byte aValue : aValues)
        ret.add (Byte.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsLinkedHashSet <Character> newPrimitiveOrderedSet (@Nullable final char... aValues)
  {
    final CommonsLinkedHashSet <Character> ret = CollectionHelper.newOrderedSet ();
    if (aValues != null)
      for (final char aValue : aValues)
        ret.add (Character.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsLinkedHashSet <Double> newPrimitiveOrderedSet (@Nullable final double... aValues)
  {
    final CommonsLinkedHashSet <Double> ret = CollectionHelper.newOrderedSet ();
    if (aValues != null)
      for (final double aValue : aValues)
        ret.add (Double.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsLinkedHashSet <Float> newPrimitiveOrderedSet (@Nullable final float... aValues)
  {
    final CommonsLinkedHashSet <Float> ret = CollectionHelper.newOrderedSet ();
    if (aValues != null)
      for (final float aValue : aValues)
        ret.add (Float.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsLinkedHashSet <Integer> newPrimitiveOrderedSet (@Nullable final int... aValues)
  {
    final CommonsLinkedHashSet <Integer> ret = CollectionHelper.newOrderedSet ();
    if (aValues != null)
      for (final int aValue : aValues)
        ret.add (Integer.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsLinkedHashSet <Long> newPrimitiveOrderedSet (@Nullable final long... aValues)
  {
    final CommonsLinkedHashSet <Long> ret = CollectionHelper.newOrderedSet ();
    if (aValues != null)
      for (final long aValue : aValues)
        ret.add (Long.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsLinkedHashSet <Short> newPrimitiveOrderedSet (@Nullable final short... aValues)
  {
    final CommonsLinkedHashSet <Short> ret = CollectionHelper.newOrderedSet ();
    if (aValues != null)
      for (final short aValue : aValues)
        ret.add (Short.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsTreeSet <Boolean> newPrimitiveSortedSet (@Nullable final boolean... aValues)
  {
    final CommonsTreeSet <Boolean> ret = CollectionHelper.newSortedSet ();
    if (aValues != null)
      for (final boolean aValue : aValues)
        ret.add (Boolean.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsTreeSet <Byte> newPrimitiveSortedSet (@Nullable final byte... aValues)
  {
    final CommonsTreeSet <Byte> ret = CollectionHelper.newSortedSet ();
    if (aValues != null)
      for (final byte aValue : aValues)
        ret.add (Byte.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsTreeSet <Character> newPrimitiveSortedSet (@Nullable final char... aValues)
  {
    final CommonsTreeSet <Character> ret = CollectionHelper.newSortedSet ();
    if (aValues != null)
      for (final char aValue : aValues)
        ret.add (Character.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsTreeSet <Double> newPrimitiveSortedSet (@Nullable final double... aValues)
  {
    final CommonsTreeSet <Double> ret = CollectionHelper.newSortedSet ();
    if (aValues != null)
      for (final double aValue : aValues)
        ret.add (Double.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsTreeSet <Float> newPrimitiveSortedSet (@Nullable final float... aValues)
  {
    final CommonsTreeSet <Float> ret = CollectionHelper.newSortedSet ();
    if (aValues != null)
      for (final float aValue : aValues)
        ret.add (Float.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsTreeSet <Integer> newPrimitiveSortedSet (@Nullable final int... aValues)
  {
    final CommonsTreeSet <Integer> ret = CollectionHelper.newSortedSet ();
    if (aValues != null)
      for (final int aValue : aValues)
        ret.add (Integer.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsTreeSet <Long> newPrimitiveSortedSet (@Nullable final long... aValues)
  {
    final CommonsTreeSet <Long> ret = CollectionHelper.newSortedSet ();
    if (aValues != null)
      for (final long aValue : aValues)
        ret.add (Long.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CommonsTreeSet <Short> newPrimitiveSortedSet (@Nullable final short... aValues)
  {
    final CommonsTreeSet <Short> ret = CollectionHelper.newSortedSet ();
    if (aValues != null)
      for (final short aValue : aValues)
        ret.add (Short.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static NonBlockingStack <Boolean> newPrimitiveStack (@Nullable final boolean... aValues)
  {
    final NonBlockingStack <Boolean> ret = StackHelper.newStack ();
    if (aValues != null)
      for (final boolean aValue : aValues)
        ret.add (Boolean.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static NonBlockingStack <Byte> newPrimitiveStack (@Nullable final byte... aValues)
  {
    final NonBlockingStack <Byte> ret = StackHelper.newStack ();
    if (aValues != null)
      for (final byte aValue : aValues)
        ret.add (Byte.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static NonBlockingStack <Character> newPrimitiveStack (@Nullable final char... aValues)
  {
    final NonBlockingStack <Character> ret = StackHelper.newStack ();
    if (aValues != null)
      for (final char aValue : aValues)
        ret.add (Character.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static NonBlockingStack <Double> newPrimitiveStack (@Nullable final double... aValues)
  {
    final NonBlockingStack <Double> ret = StackHelper.newStack ();
    if (aValues != null)
      for (final double aValue : aValues)
        ret.add (Double.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static NonBlockingStack <Float> newPrimitiveStack (@Nullable final float... aValues)
  {
    final NonBlockingStack <Float> ret = StackHelper.newStack ();
    if (aValues != null)
      for (final float aValue : aValues)
        ret.add (Float.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static NonBlockingStack <Integer> newPrimitiveStack (@Nullable final int... aValues)
  {
    final NonBlockingStack <Integer> ret = StackHelper.newStack ();
    if (aValues != null)
      for (final int aValue : aValues)
        ret.add (Integer.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static NonBlockingStack <Long> newPrimitiveStack (@Nullable final long... aValues)
  {
    final NonBlockingStack <Long> ret = StackHelper.newStack ();
    if (aValues != null)
      for (final long aValue : aValues)
        ret.add (Long.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static NonBlockingStack <Short> newPrimitiveStack (@Nullable final short... aValues)
  {
    final NonBlockingStack <Short> ret = StackHelper.newStack ();
    if (aValues != null)
      for (final short aValue : aValues)
        ret.add (Short.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static PriorityQueue <Boolean> newPrimitiveQueue (@Nullable final boolean... aValues)
  {
    final PriorityQueue <Boolean> ret = QueueHelper.newQueue ();
    if (aValues != null)
      for (final boolean aValue : aValues)
        ret.add (Boolean.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static PriorityQueue <Byte> newPrimitiveQueue (@Nullable final byte... aValues)
  {
    final PriorityQueue <Byte> ret = QueueHelper.newQueue ();
    if (aValues != null)
      for (final byte aValue : aValues)
        ret.add (Byte.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static PriorityQueue <Character> newPrimitiveQueue (@Nullable final char... aValues)
  {
    final PriorityQueue <Character> ret = QueueHelper.newQueue ();
    if (aValues != null)
      for (final char aValue : aValues)
        ret.add (Character.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static PriorityQueue <Double> newPrimitiveQueue (@Nullable final double... aValues)
  {
    final PriorityQueue <Double> ret = QueueHelper.newQueue ();
    if (aValues != null)
      for (final double aValue : aValues)
        ret.add (Double.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static PriorityQueue <Float> newPrimitiveQueue (@Nullable final float... aValues)
  {
    final PriorityQueue <Float> ret = QueueHelper.newQueue ();
    if (aValues != null)
      for (final float aValue : aValues)
        ret.add (Float.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static PriorityQueue <Integer> newPrimitiveQueue (@Nullable final int... aValues)
  {
    final PriorityQueue <Integer> ret = QueueHelper.newQueue ();
    if (aValues != null)
      for (final int aValue : aValues)
        ret.add (Integer.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static PriorityQueue <Long> newPrimitiveQueue (@Nullable final long... aValues)
  {
    final PriorityQueue <Long> ret = QueueHelper.newQueue ();
    if (aValues != null)
      for (final long aValue : aValues)
        ret.add (Long.valueOf (aValue));
    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static PriorityQueue <Short> newPrimitiveQueue (@Nullable final short... aValues)
  {
    final PriorityQueue <Short> ret = QueueHelper.newQueue ();
    if (aValues != null)
      for (final short aValue : aValues)
        ret.add (Short.valueOf (aValue));
    return ret;
  }
}
