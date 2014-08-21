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
package com.helger.commons.callback;



/**
 * Simple callback interface to allow generic iteration with a typed callback
 * function. This is similar to {@link IThrowingRunnable} except that a
 * parameter is present.
 * 
 * @author Philip Helger
 * @param <PARAMTYPE>
 *        The type of the parameter that is required for executing the callback.
 */
public interface IThrowingRunnableWithParameter <PARAMTYPE>
{
  /**
   * The callback method that is invoked.
   * 
   * @param aCurrentObject
   *        The current object. May be <code>null</code> or non-
   *        <code>null</code> depending on the implementation.
   * @throws Exception
   *         In case something goes wrong
   */
  void run (PARAMTYPE aCurrentObject) throws Exception;
}