/*
 * Copyright 2005-2010 the original author or authors.
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

package net.javacrumbs.smock.jaxws.server;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import javax.xml.ws.WebServiceProvider;

import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.remoting.jaxws.AbstractJaxWsServiceExporter;
import org.springframework.util.StringUtils;
import org.springframework.ws.test.server.RequestCreator;
import org.springframework.ws.test.server.ResponseActions;


public class MockWebServiceClient {
	private Map<String, Endpoint> endpointMap = new HashMap<String, Endpoint>();
	
	public MockWebServiceClient(String... webServicePackageNames) {
		scanForWebServices(webServicePackageNames);
	}

	private void scanForWebServices(String... webServicePackageNames) {
		GenericApplicationContext context = new GenericApplicationContext();
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context, false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(WebService.class));
		scanner.scan(webServicePackageNames);
		context.refresh();
		ServiceExporter serviceExporter = new ServiceExporter();
		serviceExporter.setBeanFactory(context);
		try {
			serviceExporter.afterPropertiesSet();
		} catch (Exception e) {
			new IllegalStateException("Unexpected exception",e);
		}
	}
	
	protected  String generateServiceName(Endpoint endpoint, String serviceName) {
		return StringUtils.hasText(serviceName)?serviceName:endpoint.getImplementor().getClass().getSimpleName()+"Service";
	}

	public ResponseActions sendRequest(RequestCreator requestCreator) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class ServiceExporter extends AbstractJaxWsServiceExporter
	{
		@Override
		protected void publishEndpoint(Endpoint endpoint, WebService annotation) {
			endpointMap.put(generateServiceName(endpoint,annotation.serviceName()), endpoint);
			
		}

		@Override
		protected void publishEndpoint(Endpoint endpoint, WebServiceProvider annotation) {
			endpointMap.put(generateServiceName(endpoint, annotation.serviceName()), endpoint);
		}
	}

	Map<String, Endpoint> getEndpointMap() {
		return endpointMap;
	}
}
