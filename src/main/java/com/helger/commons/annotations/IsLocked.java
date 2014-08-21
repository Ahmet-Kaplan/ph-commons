/**
 * Copyright (C) 2006-2014 phloc systems (www.phloc.com)
 * Copyright (C) 2014 Philip Helger (www.helger.com)
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
package com.helger.commons.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

/**
 * For methods to be overridden, it indicates, that the method is locked. When
 * using read-write locks (class ReadWriteLock), please selected the matching
 * lock type. When using exclusive locks (class Lock) use the lock type
 * <code>WRITE</code>.
 *
 * @author Philip Helger
 */
@Retention (RetentionPolicy.CLASS)
@Target ({ ElementType.METHOD })
@Documented
public @interface IsLocked
{
  public static enum ELockType
  {
    READ,
    WRITE
  }

  @Nonnull
  ELockType value();
}