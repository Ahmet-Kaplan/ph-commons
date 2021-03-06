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
package com.helger.tree.singleton;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ext.ICommonsCollection;
import com.helger.commons.state.EChange;
import com.helger.tree.withid.DefaultTreeItemWithID;
import com.helger.tree.withid.unique.DefaultTreeWithGlobalUniqueID;
import com.helger.tree.withid.unique.ITreeWithGlobalUniqueID;

/**
 * Default proxy interface for {@link ITreeWithGlobalUniqueID}.
 *
 * @author Philip Helger
 * @param <KEYTYPE>
 *        Tree key type
 * @param <VALUETYPE>
 *        Tree value type
 */
public interface ITreeWithUniqueIDProxy <KEYTYPE, VALUETYPE> extends
                                        ITreeWithGlobalUniqueID <KEYTYPE, VALUETYPE, DefaultTreeItemWithID <KEYTYPE, VALUETYPE>>
{
  @Nonnull
  DefaultTreeWithGlobalUniqueID <KEYTYPE, VALUETYPE> getProxyTree ();

  default boolean hasChildren ()
  {
    return getProxyTree ().hasChildren ();
  }

  default boolean hasNoChildren ()
  {
    return getProxyTree ().hasNoChildren ();
  }

  @Nonnegative
  default int getChildCount ()
  {
    return getProxyTree ().getChildCount ();
  }

  @Nonnull
  @ReturnsMutableCopy
  default ICommonsCollection <? extends DefaultTreeItemWithID <KEYTYPE, VALUETYPE>> getAllChildren ()
  {
    return getProxyTree ().getAllChildren ();
  }

  default void forAllChildren (@Nonnull final Consumer <? super DefaultTreeItemWithID <KEYTYPE, VALUETYPE>> aConsumer)
  {
    getProxyTree ().forAllChildren (aConsumer);
  }

  default void forAllChildren (@Nonnull final Predicate <? super DefaultTreeItemWithID <KEYTYPE, VALUETYPE>> aFilter,
                               @Nonnull final Consumer <? super DefaultTreeItemWithID <KEYTYPE, VALUETYPE>> aConsumer)
  {
    getProxyTree ().forAllChildren (aFilter, aConsumer);
  }

  default <DSTTYPE> void forAllChildrenMapped (@Nonnull final Predicate <? super DefaultTreeItemWithID <KEYTYPE, VALUETYPE>> aFilter,
                                               @Nonnull final Function <? super DefaultTreeItemWithID <KEYTYPE, VALUETYPE>, ? extends DSTTYPE> aMapper,
                                               @Nonnull final Consumer <? super DSTTYPE> aConsumer)
  {
    getProxyTree ().forAllChildrenMapped (aFilter, aMapper, aConsumer);
  }

  @Nonnull
  default DefaultTreeItemWithID <KEYTYPE, VALUETYPE> getRootItem ()
  {
    return getProxyTree ().getRootItem ();
  }

  @Nullable
  default DefaultTreeItemWithID <KEYTYPE, VALUETYPE> getChildWithID (@Nullable final DefaultTreeItemWithID <KEYTYPE, VALUETYPE> aCurrent,
                                                                     @Nullable final KEYTYPE aID)
  {
    return getProxyTree ().getChildWithID (aCurrent, aID);
  }

  default boolean hasChildren (@Nullable final DefaultTreeItemWithID <KEYTYPE, VALUETYPE> aCurrent)
  {
    return getProxyTree ().hasChildren (aCurrent);
  }

  default boolean hasNoChildren (@Nullable final DefaultTreeItemWithID <KEYTYPE, VALUETYPE> aCurrent)
  {
    return getProxyTree ().hasNoChildren (aCurrent);
  }

  @Nonnegative
  default int getChildCount (@Nullable final DefaultTreeItemWithID <KEYTYPE, VALUETYPE> aCurrent)
  {
    return getProxyTree ().getChildCount (aCurrent);
  }

  @Nullable
  default ICommonsCollection <? extends DefaultTreeItemWithID <KEYTYPE, VALUETYPE>> getAllChildren (@Nullable final DefaultTreeItemWithID <KEYTYPE, VALUETYPE> aCurrent)
  {
    return getProxyTree ().getAllChildren (aCurrent);
  }

  @Nullable
  default DefaultTreeItemWithID <KEYTYPE, VALUETYPE> getItemWithID (@Nullable final KEYTYPE aDataID)
  {
    return getProxyTree ().getItemWithID (aDataID);
  }

  @Nonnegative
  default int getItemCount ()
  {
    return getProxyTree ().getItemCount ();
  }

  @Nonnull
  default ICommonsCollection <DefaultTreeItemWithID <KEYTYPE, VALUETYPE>> getAllItems ()
  {
    return getProxyTree ().getAllItems ();
  }

  default boolean isItemSameOrDescendant (@Nullable final KEYTYPE aParentItemID, @Nullable final KEYTYPE aChildItemID)
  {
    return getProxyTree ().isItemSameOrDescendant (aParentItemID, aChildItemID);
  }

  default boolean containsItemWithID (@Nullable final KEYTYPE aDataID)
  {
    return getProxyTree ().containsItemWithID (aDataID);
  }

  @Nullable
  default VALUETYPE getItemDataWithID (@Nullable final KEYTYPE aDataID)
  {
    return getProxyTree ().getItemDataWithID (aDataID);
  }

  @Nonnull
  @ReturnsMutableCopy
  default ICommonsCollection <VALUETYPE> getAllItemDatas ()
  {
    return getProxyTree ().getAllItemDatas ();
  }

  @Nonnull
  default EChange removeItemWithID (@Nullable final KEYTYPE aDataID)
  {
    return getProxyTree ().removeItemWithID (aDataID);
  }
}
