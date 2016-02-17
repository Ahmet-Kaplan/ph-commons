package com.helger.commons.collection.ext;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Nonnull;

public class CommonsList <T> extends ArrayList <T> implements ICommonsList <T>
{
  public CommonsList ()
  {}

  public CommonsList (final int nInitialCapacity)
  {
    super (nInitialCapacity);
  }

  public CommonsList (@Nonnull final Collection <? extends T> aCont)
  {
    super (aCont);
  }
}