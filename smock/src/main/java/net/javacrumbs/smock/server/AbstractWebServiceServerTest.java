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

package net.javacrumbs.smock.server;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.server.SoapMessageDispatcher;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.RequestCreator;
import org.springframework.ws.test.server.RequestCreators;
import org.springframework.ws.test.server.ResponseActions;
import org.springframework.ws.test.server.ResponseMatcher;
import org.springframework.ws.test.server.ResponseMatchers;
import org.springframework.ws.test.server.ResponseXPathExpectations;
import org.springframework.ws.transport.WebServiceMessageReceiver;
import org.springframework.xml.transform.ResourceSource;

/**
 * Simplifies usage of Spring WS testing framework. Wraps static methods of {@link RequestCreators}, {@link ResponseMatchers} and {@link MockWebServiceClient}. 
 * Moreover it automatically creates {@link MockWebServiceClient} instance. 
 * @author Lukas Krecan
 *
 */
public abstract class AbstractWebServiceServerTest implements ApplicationContextAware {

	protected  ApplicationContext applicationContext;
	protected  MockWebServiceClient mockWebServiceClient;


	/**
	 * Sends a request message by using the given {@link RequestCreator}. Typically called by using the default request
	 * creators provided by {@link RequestCreators}.
	 *
	 * @param requestCreator the request creator
	 * @return the response actions
	 * @see RequestCreators
	 */
	public ResponseActions sendRequest(RequestCreator requestCreator) {
		return mockWebServiceClient.sendRequest(requestCreator);
	}
	/**
	 * Create a request with the given {@link Source} XML as payload.
	 *
	 * @param payload the request payload
	 * @return the request creator
	 */
	public  RequestCreator withPayload(Source payload) {
		return RequestCreators.withPayload(payload);
	}

	/**
	 * Create a request with the given {@link Resource} XML as payload.
	 *
	 * @param payload the request payload
	 * @return the request creator
	 */
	public  RequestCreator withPayload(Resource payload) throws IOException {
		return RequestCreators.withPayload(payload);
	}

	/**
	 * Expects the given {@link Source} XML payload.
	 *
	 * @param payload the XML payload
	 * @return the response matcher
	 */
	public  ResponseMatcher payload(Source payload) {
		return ResponseMatchers.payload(payload);
	}

	/**
	 * Expects the given {@link Resource} XML payload.
	 *
	 * @param payload the XML payload
	 * @return the response matcher
	 */
	public ResponseMatcher payload(Resource payload) throws IOException {
		//TODO change to:  	return ResponseMatchers.payload(payload);
		return ResponseMatchers.payload(new ResourceSource(payload));
	}

	/**
	 * Expects the payload to validate against the given XSD schema(s).
	 *
	 * @param schema         the schema
	 * @param furtherSchemas further schemas, if necessary
	 * @return the response matcher
	 */
	public  ResponseMatcher validPayload(Resource schema, Resource... furtherSchemas) throws IOException {
		return ResponseMatchers.validPayload(schema, furtherSchemas);
	}

	/**
	 * Expects the given XPath expression to (not) exist or be evaluated to a value.
	 *
	 * @param xpathExpression the XPath expression
	 * @return the XPath expectations, to be further configured
	 */
	public  ResponseXPathExpectations xpath(String xpathExpression) {
		return ResponseMatchers.xpath(xpathExpression);
	}

	/**
	 * Expects the given XPath expression to (not) exist or be evaluated to a value.
	 *
	 * @param xpathExpression  the XPath expression
	 * @param namespaceMapping the namespaces
	 * @return the XPath expectations, to be further configured
	 */
	public  ResponseXPathExpectations xpath(String xpathExpression, Map<String, String> namespaceMapping) {
		return ResponseMatchers.xpath(xpathExpression, namespaceMapping);
	}


	// SOAP

	/**
	 * Expects the given SOAP header in the outgoing message.
	 *
	 * @param soapHeaderName the qualified name of the SOAP header to expect
	 * @return the request matcher
	 */
	public  ResponseMatcher soapHeader(QName soapHeaderName) {
		return ResponseMatchers.soapHeader(soapHeaderName);
	}

	/**
	 * Expects the response <strong>not</strong> to contain a SOAP fault.
	 *
	 * @return the response matcher
	 */
	public  ResponseMatcher noFault() {
		return ResponseMatchers.noFault();
	}

	/**
	 * Expects a {@code MustUnderstand} fault.
	 *
	 * @see org.springframework.ws.soap.SoapBody#addMustUnderstandFault(String, Locale)
	 */
	public  ResponseMatcher mustUnderstandFault() {
		return ResponseMatchers.mustUnderstandFault();
	}

	/**
	 * Expects a {@code MustUnderstand} fault with a particular fault string or reason.
	 *
	 * @param faultStringOrReason the SOAP 1.1 fault string or SOAP 1.2 reason text. If {@code null} the fault string or
	 * reason text will not be verified
	 * @see org.springframework.ws.soap.SoapBody#addMustUnderstandFault(String, Locale)
	 */
	public  ResponseMatcher mustUnderstandFault(String faultStringOrReason) {
		return ResponseMatchers.mustUnderstandFault(faultStringOrReason);
	}

	/**
	 * Expects a {@code Client} (SOAP 1.1) or {@code Sender} (SOAP 1.2) fault.
	 *
	 * @see org.springframework.ws.soap.SoapBody#addClientOrSenderFault(String, Locale)
	 */
	public  ResponseMatcher clientOrSenderFault() {
		return ResponseMatchers.clientOrSenderFault();
	}

	/**
	 * Expects a {@code Client} (SOAP 1.1) or {@code Sender} (SOAP 1.2) fault with a particular fault string or reason.
	 *
	 * @param faultStringOrReason the SOAP 1.1 fault string or SOAP 1.2 reason text. If {@code null} the fault string or
	 * reason text will not be verified
	 * @see org.springframework.ws.soap.SoapBody#addClientOrSenderFault(String, Locale)
	 */
	public  ResponseMatcher clientOrSenderFault(String faultStringOrReason) {
		return ResponseMatchers.clientOrSenderFault(faultStringOrReason);
	}

	/**
	 * Expects a {@code Server} (SOAP 1.1) or {@code Receiver} (SOAP 1.2) fault.
	 *
	 * @see org.springframework.ws.soap.SoapBody#addServerOrReceiverFault(String, java.util.Locale)
	 */
	public  ResponseMatcher serverOrReceiverFault() {
		return ResponseMatchers.serverOrReceiverFault();
	}

	/**
	 * Expects a {@code Server} (SOAP 1.1) or {@code Receiver} (SOAP 1.2) fault with a particular fault string or reason.
	 *
	 * @param faultStringOrReason the SOAP 1.1 fault string or SOAP 1.2 reason text. If {@code null} the fault string or
	 * reason text will not be verified
	 * @see org.springframework.ws.soap.SoapBody#addClientOrSenderFault(String, Locale)
	 */
	public  ResponseMatcher serverOrReceiverFault(String faultStringOrReason) {
		return ResponseMatchers.clientOrSenderFault(faultStringOrReason);
	}

	/**
	 * Expects a {@code VersionMismatch} fault.
	 *
	 * @see org.springframework.ws.soap.SoapBody#addVersionMismatchFault(String, java.util.Locale)
	 */
	public  ResponseMatcher versionMismatchFault() {
		return ResponseMatchers.versionMismatchFault();
	}

	/**
	 * Expects a {@code VersionMismatch} fault with a particular fault string or reason.
	 *
	 * @param faultStringOrReason the SOAP 1.1 fault string or SOAP 1.2 reason text. If {@code null} the fault string or
	 * reason text will not be verified
	 * @see org.springframework.ws.soap.SoapBody#addClientOrSenderFault(String, Locale)
	 */
	public  ResponseMatcher versionMismatchFault(String faultStringOrReason) {
		return ResponseMatchers.versionMismatchFault(faultStringOrReason);
	}
	/**
	 * Creates a {@code MockWebServiceClient} instance based on the given {@link ApplicationContext}.
	 *
	 * This factory method works in a similar fashion as the standard
	 * {@link org.springframework.ws.transport.http.MessageDispatcherServlet MessageDispatcherServlet}. That is:
	 * <ul>
	 * <li>If a {@link WebServiceMessageReceiver} is configured in the given application context, it will use that.
	 * If no message receiver is configured, it will create a default {@link SoapMessageDispatcher}.</li>
	 * <li>If a {@link WebServiceMessageFactory} is configured in the given application context, it will use that.
	 * If no message factory is configured, it will create a default {@link SaajSoapMessageFactory}.</li>
	 * </ul>
	 *
	 * @param applicationContext the application context to base the client on
	 * @return the created client
	 */
	public MockWebServiceClient createClient(ApplicationContext applicationContext) {
		return MockWebServiceClient.createClient(applicationContext);
	}



	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.mockWebServiceClient = createClient(applicationContext);
	}


	protected MockWebServiceClient getMockWebServiceClient() {
		return mockWebServiceClient;
	}
	protected void setMockWebServiceClient(MockWebServiceClient mockWebServiceClient) {
		this.mockWebServiceClient = mockWebServiceClient;
	} 


}
