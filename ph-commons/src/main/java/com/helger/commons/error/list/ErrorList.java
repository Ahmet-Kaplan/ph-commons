package com.helger.commons.error.list;

import java.util.Iterator;
import java.util.function.Predicate;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.ext.CommonsArrayList;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.error.IError;
import com.helger.commons.lang.ICloneable;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;

/**
 * Default implementation of {@link IErrorList}.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class ErrorList implements IErrorList, ICloneable <ErrorList>
{
  private final ICommonsList <IError> m_aList;

  /**
   * Default constructor.
   */
  public ErrorList ()
  {
    m_aList = new CommonsArrayList<> ();
  }

  /**
   * Constructor taking a list of iterable objects.
   *
   * @param aList
   *        The list to be added. May be <code>null</code>.
   */
  public ErrorList (@Nullable final Iterable <? extends IError> aList)
  {
    m_aList = aList == null ? new CommonsArrayList<> () : new CommonsArrayList<> (aList);
  }

  /**
   * Copy constructor.
   *
   * @param aErrorList
   *        The error list to copy from. May be <code>null</code>.
   */
  public ErrorList (@Nonnull final ErrorList aErrorList)
  {
    m_aList = aErrorList == null ? new CommonsArrayList<> () : aErrorList.m_aList.getClone ();
  }

  public boolean isEmpty ()
  {
    return m_aList.isEmpty ();
  }

  @Nonnegative
  public int getSize ()
  {
    return m_aList.size ();
  }

  @Nonnull
  public Iterator <IError> iterator ()
  {
    return m_aList.iterator ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <IError> getAllItems ()
  {
    return m_aList.getClone ();
  }

  @Nonnull
  public ErrorList getSubList (@Nullable final Predicate <? super IError> aFilter)
  {
    if (aFilter == null)
      return getClone ();

    final ErrorList ret = new ErrorList ();
    m_aList.findAll (aFilter, ret.m_aList::add);
    return ret;
  }

  @Nonnull
  public ErrorList add (@Nonnull final IError aError)
  {
    ValueEnforcer.notNull (aError, "Error");
    m_aList.add (aError);
    return this;
  }

  public void addAll (@Nullable final Iterable <? extends IError> aErrorList)
  {
    if (aErrorList != null)
      for (final IError aFormError : aErrorList)
        add (aFormError);
  }

  public void addAll (@Nullable final IError... aErrorList)
  {
    if (aErrorList != null)
      for (final IError aFormError : aErrorList)
        add (aFormError);
  }

  @Nonnull
  public EChange remove (@Nullable final IError aError)
  {
    if (aError == null)
      return EChange.UNCHANGED;
    return m_aList.removeObject (aError);
  }

  @Nonnull
  public EChange removeAll (@Nullable final Iterable <? extends IError> aErrors)
  {
    EChange ret = EChange.UNCHANGED;
    if (aErrors != null)
      for (final IError aError : aErrors)
        ret = ret.or (remove (aError));
    return ret;
  }

  @Nonnull
  public EChange removeAll (@Nullable final IError... aErrors)
  {
    EChange ret = EChange.UNCHANGED;
    if (aErrors != null)
      for (final IError aError : aErrors)
        ret = ret.or (remove (aError));
    return ret;
  }

  @Nonnull
  public EChange removeIf (@Nonnull final Predicate <? super IError> aFilter)
  {
    return EChange.valueOf (m_aList.removeIf (aFilter));
  }

  @Nonnull
  public EChange clear ()
  {
    return m_aList.removeAll ();
  }

  @Nonnull
  public ErrorList getClone ()
  {
    return new ErrorList (this);
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("List", m_aList).toString ();
  }
}
