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
package net.javacrumbs.smock.http.server.servlet;

import net.javacrumbs.smock.common.server.AbstractCommonSmockServerTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.RequestCreator;
import org.springframework.ws.test.server.RequestCreators;
import org.springframework.ws.test.server.ResponseActions;

/**
 * Creates {@link MockWebServiceClient} and exposes Smock utility methods. You will want to use a subclass for CXF or Metro packages.
 * @author Lukas Krecan
 *
 */
public abstract class AbstractHttpSmockServerTest extends AbstractCommonSmockServerTest implements ApplicationContextAware{
	
	protected  CommonServletBasedMockWebServiceClient mockWebServiceClient;

	/**
	 * Sends a request message by using the given {@link RequestCreator}. Typically called by using the default request
	 * creators provided by {@link RequestCreators}.
	 *
	 * @param requestCreator the request creator
	 * @return the response actions
	 * @see RequestCreators
	 */
	public ResponseActions sendRequestTo(String uri, RequestCreator requestCreator) {
		return mockWebServiceClient.sendRequestTo(uri, requestCreator);
	}
	
	protected abstract CommonServletBasedMockWebServiceClient createClient(ApplicationContext applicationContext, ClientInterceptor[] interceptors);


	protected CommonServletBasedMockWebServiceClient getMockWebServiceClient() {
		return mockWebServiceClient;
	}
	
	protected void setMockWebServiceClient(CommonServletBasedMockWebServiceClient mockWebServiceClient) {
		this.mockWebServiceClient = mockWebServiceClient;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		setMockWebServiceClient(createClient(applicationContext, getInterceptors()));	
	}
	
	/**
	 * To be overriden by a subclasses that need to set interceptors.
	 * @return
	 */
	protected ClientInterceptor[] getInterceptors() {
		return null;
	}
}
