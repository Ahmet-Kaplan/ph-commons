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
package com.helger.commons.scopes.singleton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.helger.commons.mock.PHTestUtils;
import com.helger.commons.scopes.mock.ScopeTestRule;
import com.helger.commons.scopes.singleton.SessionSingleton;

/**
 * Test class for class {@link SessionSingleton}.<br>
 * Note: must reside here for Mock* stuff!
 *
 * @author Philip Helger
 */
public final class SessionSingletonWithScopeCtorTest
{
  @Rule
  public final TestRule m_aScopeRule = new ScopeTestRule ();

  @Test
  public void testBasic () throws Exception
  {
    assertTrue (SessionSingleton.getAllSessionSingletons ().isEmpty ());
    assertFalse (SessionSingleton.isSessionSingletonInstantiated (MockSessionSingletonWithScopeCtor.class));
    assertNull (SessionSingleton.getSessionSingletonIfInstantiated (MockSessionSingletonWithScopeCtor.class));

    final MockSessionSingletonWithScopeCtor a = MockSessionSingletonWithScopeCtor.getInstance ();
    assertNotNull (a);
    assertTrue (SessionSingleton.isSessionSingletonInstantiated (MockSessionSingletonWithScopeCtor.class));
    assertSame (a, SessionSingleton.getSessionSingletonIfInstantiated (MockSessionSingletonWithScopeCtor.class));
    assertNotNull (a.getScope ());
    assertEquals (0, a.get ());
    a.inc ();
    assertEquals (1, a.get ());
    assertSame (a, MockSessionSingletonWithScopeCtor.getInstance ());

    PHTestUtils.testDefaultSerialization (a);
  }
}