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

package net.javacrumbs.mock;

import static net.javacrumbs.mock.WebServiceMockMessageSupport.*;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.xml.transform.StringSource;


public class WebServiceMockMessageSupportTest extends AbstractTest {

	@Test
	public void testMessage()
	{
		MessageDiffMatcher requestMatcher = (MessageDiffMatcher) message(new StringSource(MESSAGE));
		assertNotNull(requestMatcher.getMessage());
	}
	@Test
	public void testWithMessage()
	{
		MessageResponseCreator responseCallback = (MessageResponseCreator) withMessage(new StringSource(MESSAGE));
		assertNotNull(responseCallback.getResponse());
	}
	
}