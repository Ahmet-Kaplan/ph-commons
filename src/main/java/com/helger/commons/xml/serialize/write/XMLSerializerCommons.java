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
package com.helger.commons.xml.serialize.write;

import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import com.helger.commons.string.StringHelper;
import com.helger.commons.xml.EXMLVersion;
import com.helger.commons.xml.IXMLIterationHandler;
import com.helger.commons.xml.XMLHelper;
import com.helger.commons.xml.namespace.ComparatorQName;

/**
 * org.w3c.dom.Node serializer that correctly handles HTML empty elements
 * (&lt;span&gt;&lt;/span&gt; vs. &lt;span /&gt;).
 *
 * @author Philip Helger
 */
public class XMLSerializerCommons extends AbstractXMLSerializer <Node>
{
  public XMLSerializerCommons ()
  {
    this (XMLWriterSettings.DEFAULT_XML_SETTINGS);
  }

  public XMLSerializerCommons (@Nonnull final IXMLWriterSettings aSettings)
  {
    super (aSettings);
  }

  @Override
  protected void emitNode (@Nonnull final IXMLIterationHandler aXMLWriter,
                           @Nullable final Node aPrevSibling,
                           @Nonnull final Node aNode,
                           @Nullable final Node aNextSibling)
  {
    final short nNodeType = aNode.getNodeType ();
    if (nNodeType == Node.ELEMENT_NODE)
      _writeElement (aXMLWriter, aPrevSibling, (Element) aNode, aNextSibling);
    else
      if (nNodeType == Node.TEXT_NODE)
        _writeText (aXMLWriter, (Text) aNode);
      else
        if (nNodeType == Node.CDATA_SECTION_NODE)
          _writeCDATA (aXMLWriter, (CDATASection) aNode);
        else
          if (nNodeType == Node.COMMENT_NODE)
            _writeComment (aXMLWriter, (Comment) aNode);
          else
            if (nNodeType == Node.ENTITY_REFERENCE_NODE)
              _writeEntityReference (aXMLWriter, (EntityReference) aNode);
            else
              if (nNodeType == Node.DOCUMENT_NODE)
                _writeDocument (aXMLWriter, (Document) aNode);
              else
                if (nNodeType == Node.DOCUMENT_TYPE_NODE)
                  _writeDocumentType (aXMLWriter, (DocumentType) aNode);
                else
                  if (nNodeType == Node.PROCESSING_INSTRUCTION_NODE)
                    _writeProcessingInstruction (aXMLWriter, (ProcessingInstruction) aNode);
                  else
                    throw new IllegalArgumentException ("Passed node type " + nNodeType + " is not yet supported");
  }

  private void _writeNodeList (@Nonnull final IXMLIterationHandler aXMLWriter, @Nonnull final NodeList aChildren)
  {
    final int nLastIndex = aChildren.getLength () - 1;
    for (int nIndex = 0; nIndex <= nLastIndex; ++nIndex)
    {
      emitNode (aXMLWriter,
                nIndex == 0 ? null : aChildren.item (nIndex - 1),
                aChildren.item (nIndex),
                nIndex == nLastIndex ? null : aChildren.item (nIndex + 1));
    }
  }

  private void _writeDocument (@Nonnull final IXMLIterationHandler aXMLWriter, @Nonnull final Document aDocument)
  {
    if (m_aSettings.getFormat ().isXML ())
    {
      String sXMLVersion = null;
      boolean bIsDocumentStandalone = false;
      try
      {
        sXMLVersion = aDocument.getXmlVersion ();
        bIsDocumentStandalone = aDocument.getXmlStandalone ();
      }
      catch (final Throwable t)
      {
        // Happens e.g. in dom4j 1.6.1:
        // AbstractMethodError: getXmlVersion and getXmlStandalone
      }
      final EXMLVersion eXMLVersion = EXMLVersion.getFromVersionOrDefault (sXMLVersion, m_aSettings.getXMLVersion ());
      aXMLWriter.onDocumentStart (eXMLVersion,
                                  m_aSettings.getCharset (),
                                  bIsDocumentStandalone || aDocument.getDoctype () == null);
    }

    _writeNodeList (aXMLWriter, aDocument.getChildNodes ());
  }

  private void _writeDocumentType (@Nonnull final IXMLIterationHandler aXMLWriter, @Nonnull final DocumentType aDocType)
  {
    if (m_aSettings.getSerializeDocType ().isEmit ())
      aXMLWriter.onDocumentType (aDocType.getName (), aDocType.getPublicId (), aDocType.getSystemId ());
  }

  private static void _writeProcessingInstruction (@Nonnull final IXMLIterationHandler aXMLWriter,
                                                   @Nonnull final ProcessingInstruction aPI)
  {
    aXMLWriter.onProcessingInstruction (aPI.getTarget (), aPI.getData ());
  }

  private static void _writeEntityReference (@Nonnull final IXMLIterationHandler aXMLWriter,
                                             @Nonnull final EntityReference aEntRef)
  {
    aXMLWriter.onEntityReference (aEntRef.getNodeName ());
  }

  private void _writeComment (@Nonnull final IXMLIterationHandler aXMLWriter, @Nonnull final Comment aComment)
  {
    if (m_aSettings.getSerializeComments ().isEmit ())
    {
      aXMLWriter.onComment (aComment.getData ());
    }
  }

  private static void _writeText (@Nonnull final IXMLIterationHandler aXMLWriter, @Nonnull final Text aText)
  {
    // DOM text is always escaped!
    aXMLWriter.onText (aText.getData (), true);
  }

  private static void _writeCDATA (@Nonnull final IXMLIterationHandler aXMLWriter, @Nonnull final Text aText)
  {
    aXMLWriter.onCDATA (aText.getData ());
  }

  private void _writeElement (@Nonnull final IXMLIterationHandler aXMLWriter,
                              @Nullable final Node aPrevSibling,
                              @Nonnull final Element aElement,
                              @Nullable final Node aNextSibling)
  {
    // use either local name or tag name (depending on namespace prefix)
    final String sTagName = aElement.getLocalName () != null ? aElement.getLocalName () : aElement.getTagName ();

    // May be null!
    final Document aDoc = aElement.getOwnerDocument ();
    final boolean bEmitNamespaces = m_aSettings.isEmitNamespaces ();
    final EXMLSerializeIndent eIndent = m_aSettings.getIndent ();
    final NodeList aChildNodeList = aElement.getChildNodes ();
    final boolean bHasChildren = aChildNodeList.getLength () > 0;

    final boolean bIsRootElement = aDoc != null && aElement.equals (aDoc.getDocumentElement ());
    final boolean bIndentPrev = aPrevSibling == null || !XMLHelper.isInlineNode (aPrevSibling) || bIsRootElement;
    final boolean bIndentNext = aNextSibling == null || !XMLHelper.isInlineNode (aNextSibling);
    final boolean bHasChildElement = bHasChildren && !XMLHelper.isInlineNode (aElement.getFirstChild ());

    // get all attributes (sorting is important because the order from
    // getAttributes is not guaranteed to be consistent!)
    final Map <QName, String> aAttrMap = new TreeMap <QName, String> (new ComparatorQName ());

    m_aNSStack.push ();

    handlePutNamespaceContextPrefixInRoot (aAttrMap);

    try
    {
      // resolve Namespace prefix
      String sElementNSPrefix = null;
      if (bEmitNamespaces)
      {
        final String sElementNamespaceURI = StringHelper.getNotNull (aElement.getNamespaceURI ());
        sElementNSPrefix = m_aNSStack.getElementNamespacePrefixToUse (sElementNamespaceURI, bIsRootElement, aAttrMap);
      }

      // Get all attributes
      final NamedNodeMap aAttrs = aElement.getAttributes ();
      for (int i = 0; i < aAttrs.getLength (); ++i)
      {
        final Attr aAttr = (Attr) aAttrs.item (i);
        final String sAttrNamespaceURI = StringHelper.getNotNull (aAttr.getNamespaceURI ());

        // Ignore all "xmlns" attributes as they are created manually. They are
        // only available when reading via DOM
        if (XMLConstants.XMLNS_ATTRIBUTE_NS_URI.equals (sAttrNamespaceURI))
          continue;

        final String sAttrName = aAttr.getLocalName () != null ? aAttr.getLocalName () : aAttr.getName ();
        final String sAttrValue = aAttr.getValue ();
        String sAttributeNSPrefix = null;
        if (bEmitNamespaces)
          sAttributeNSPrefix = m_aNSStack.getAttributeNamespacePrefixToUse (sAttrNamespaceURI,
                                                                            sAttrName,
                                                                            sAttrValue,
                                                                            aAttrMap);

        if (sAttributeNSPrefix != null)
          aAttrMap.put (new QName (sAttrNamespaceURI, sAttrName, sAttributeNSPrefix), sAttrValue);
        else
          aAttrMap.put (new QName (sAttrNamespaceURI, sAttrName), sAttrValue);
      }

      // Has indent only if enabled, and an indent string is not empty
      final boolean bHasIndent = eIndent.isIndent () && m_aIndent.length () > 0;

      // indent only if predecessor was an element
      if (bHasIndent && bIndentPrev)
        aXMLWriter.onContentElementWhitespace (m_aIndent);

      aXMLWriter.onElementStart (sElementNSPrefix, sTagName, aAttrMap, bHasChildren);

      // write child nodes (if present)
      if (bHasChildren)
      {
        // do we have enclosing elements?
        if (eIndent.isAlign () && bHasChildElement)
          aXMLWriter.onContentElementWhitespace (m_aSettings.getNewLineString ());

        // increment indent
        final String sIndent = m_aSettings.getIndentationString ();
        m_aIndent.append (sIndent);

        // recursively process child nodes
        _writeNodeList (aXMLWriter, aChildNodeList);

        // decrement indent
        m_aIndent.delete (m_aIndent.length () - sIndent.length (), m_aIndent.length ());

        // add closing tag
        if (bHasIndent && bHasChildElement)
          aXMLWriter.onContentElementWhitespace (m_aIndent);
      }

      aXMLWriter.onElementEnd (sElementNSPrefix, sTagName, bHasChildren);

      if (eIndent.isAlign () && bIndentNext)
        aXMLWriter.onContentElementWhitespace (m_aSettings.getNewLineString ());
    }
    finally
    {
      m_aNSStack.pop ();
    }
  }
}