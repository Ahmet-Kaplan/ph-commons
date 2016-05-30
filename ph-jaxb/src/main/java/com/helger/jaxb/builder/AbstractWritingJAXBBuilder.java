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
package com.helger.jaxb.builder;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.validation.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.OverrideOnDemand;
import com.helger.commons.lang.GenericReflection;

/**
 * Abstract builder base class for writing and validating JAXB documents.
 *
 * @author Philip Helger
 * @param <JAXBTYPE>
 *        The JAXB implementation class to be handled
 * @param <IMPLTYPE>
 *        The implementation class implementing this abstract class.
 */
@NotThreadSafe
public abstract class AbstractWritingJAXBBuilder <JAXBTYPE, IMPLTYPE extends AbstractWritingJAXBBuilder <JAXBTYPE, IMPLTYPE>>
                                                 extends AbstractJAXBBuilder <IMPLTYPE>
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (AbstractWritingJAXBBuilder.class);

  public AbstractWritingJAXBBuilder (@Nonnull final IJAXBDocumentType aDocType)
  {
    super (aDocType);
  }

  /**
   * Create the main marshaller with the contained settings.
   *
   * @return The Marshaller and never <code>null</code>.
   * @throws JAXBException
   *         In case creation fails
   */
  @Nonnull
  @OverrideOnDemand
  protected Marshaller createMarshaller () throws JAXBException
  {
    final JAXBContext aJAXBContext = getJAXBContext ();

    // create a Marshaller
    final Marshaller aMarshaller = aJAXBContext.createMarshaller ();

    // Validating (if possible)
    final Schema aSchema = getSchema ();
    if (aSchema != null)
      aMarshaller.setSchema (aSchema);

    return aMarshaller;
  }

  /**
   * Customize the marshaller
   *
   * @param aMarshaller
   *        The marshaller to customize. Never <code>null</code>.
   */
  @OverrideOnDemand
  protected void customizeMarshaller (@Nonnull final Marshaller aMarshaller)
  {}

  @OverrideOnDemand
  protected void handleWriteException (@Nonnull final JAXBException ex)
  {
    if (ex instanceof MarshalException)
      s_aLogger.error ("Marshal exception writing object", ex);
    else
      s_aLogger.warn ("JAXB Exception writing object", ex);
  }

  @Nonnull
  protected <T> JAXBElement <T> createJAXBElement (@Nonnull final T aValue)
  {
    return new JAXBElement <> (new QName (m_aDocType.getNamespaceURI (), m_aDocType.getLocalName ()),
                               GenericReflection.uncheckedCast (aValue.getClass ()),
                               null,
                               aValue);
  }
}
