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
package net.javacrumbs.smock.common;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Source;

import net.javacrumbs.smock.common.client.ParametrizableResponseCreator;
import net.javacrumbs.smock.common.server.ParametrizableRequestCreator;

import org.springframework.util.Assert;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.WebServiceMessageFactory;


/**
 * {@link MessageCreator} that preprocesses response using {@link TemplateProcessor}.
 * @author Lukas Krecan
 *
 */
public class TemplateAwareMessageCreator extends MessageCreator  implements ParametrizableResponseCreator, ParametrizableRequestCreator{
	
	private final Map<String, Object> parameters;
	
	private final TemplateProcessor templateProcessor;

	public TemplateAwareMessageCreator(Source response, Map<String, Object> parameters, TemplateProcessor templateProcessor) {
		super(response);
		Assert.notNull(templateProcessor,"TemplateProcessor can not be null");
		this.parameters = new HashMap<String, Object>(parameters);
		this.templateProcessor = templateProcessor;
	}

	@Override
	protected Source preprocessSource(URI uri, WebServiceMessage input,	WebServiceMessageFactory messageFactory) {
		Source inputSource = input!=null?input.getPayloadSource():null;
		return templateProcessor.processTemplate(getSource(), inputSource, parameters);
	}
	
	public TemplateAwareMessageCreator withParameter(String name, Object value) {
		parameters.put(name, value);
		return this;
	}

	public TemplateAwareMessageCreator withParameters(Map<String, Object> additionalParameters) {
		parameters.putAll(additionalParameters);
		return this;
	}

}

