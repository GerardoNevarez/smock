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
package net.javacrumbs.smock.springws.server;

import net.javacrumbs.smock.common.InterceptingTemplate;

import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.transport.WebServiceMessageReceiver;

/**
 * Message receiver that intercpets messages using {@link ClientInterceptor}s. 
 * Code taken from {@link WebServiceTemplate}.
 * @author Lukas Krecan
 *
 */
class InterceptingMessageReceiver implements WebServiceMessageReceiver {

	private final WebServiceMessageReceiver wrappedMessageReceiver;
	
	private final InterceptingTemplate interceptingTemplate;
	
    public InterceptingMessageReceiver(WebServiceMessageReceiver wrappedMessageReceiver,ClientInterceptor[] interceptors) {
		this.wrappedMessageReceiver = wrappedMessageReceiver;
		this.interceptingTemplate = new InterceptingTemplate(interceptors);
	}

	public void receive(MessageContext messageContext) throws Exception {
       interceptingTemplate.interceptRequest(messageContext, wrappedMessageReceiver);
    }
	



}
