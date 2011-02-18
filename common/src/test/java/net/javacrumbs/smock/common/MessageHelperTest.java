package net.javacrumbs.smock.common;



import static net.javacrumbs.smock.common.SmockCommon.fromResource;
import static org.junit.Assert.assertNotNull;

import javax.xml.transform.Source;

import org.junit.Test;
import org.springframework.ws.WebServiceMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class MessageHelperTest {
	private MessageHelper messageHelper = new MessageHelper();
	
	@Test
	public void testDeserializeToDom() throws Exception
	{
		Element element = messageHelper.deserialize(fromResource("xml/request1.xml"), Element.class);
		assertNotNull(element);
	}
	@Test
	public void testSerializeFromDom() throws Exception
	{
		Element element = XmlUtil.loadDocument(fromResource("xml/request1.xml")).getDocumentElement();
		WebServiceMessage message = messageHelper.serialize(element);
		assertNotNull(message);
	}
	@Test
	public void testDeserializeToSource() throws Exception
	{
		Source source = messageHelper.deserialize(fromResource("xml/request1.xml"), Source.class);
		assertNotNull(source);
	}
}