/**
 * Copyright 2009-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.javacrumbs.smock.http.test.server.servlet;

import static net.javacrumbs.smock.common.server.CommonSmockServer.message;
import static net.javacrumbs.smock.common.server.CommonSmockServer.withMessage;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import net.javacrumbs.smock.http.server.servlet.CommonServletBasedMockWebServiceClient;
import net.javacrumbs.smock.http.test.server.servlet.test.TstWebService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

public abstract class AbstractServletBasedMockWebServiceClientTest {
	
	protected abstract CommonServletBasedMockWebServiceClient createMockClient(ApplicationContext context, ClientInterceptor[] interceptors);

	@Test
	public void testNormal()
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("servlet.xml");
		CommonServletBasedMockWebServiceClient client = createMockClient(context, null);
		client.sendRequestTo("/TestWebService", withMessage("request.xml")).andExpect(message("response.xml"));
		
		assertNotNull(TstWebService.getValue());
	}
	@Test
	public void testInterceptor()
	{
		ClientInterceptor interceptor = createMock(ClientInterceptor.class);
		expect(interceptor.handleRequest((MessageContext) anyObject())).andReturn(true);
		expect(interceptor.handleResponse((MessageContext) anyObject())).andReturn(true);
		
		replay(interceptor);
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("servlet.xml");
		CommonServletBasedMockWebServiceClient client = createMockClient(context, new ClientInterceptor[]{interceptor});
		client.sendRequestTo("/TestWebService", withMessage("request.xml")).andExpect(message("response.xml"));
		
		assertNotNull(TstWebService.getValue());
		
		verify(interceptor);
	}
}
