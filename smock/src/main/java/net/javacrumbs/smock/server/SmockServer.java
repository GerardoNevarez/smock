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

import static org.springframework.ws.test.server.RequestCreators.*;
import static org.springframework.ws.test.server.ResponseMatchers.*;

import javax.xml.transform.Source;

import net.javacrumbs.smock.common.SmockCommon;

import org.springframework.ws.test.server.RequestCreator;
import org.springframework.ws.test.server.ResponseMatcher;

public class SmockServer extends SmockCommon {
	//TODO add all variants of methods (Source, Resource, String args)
    /**
     * Create a request with the given {@link Source} XML as payload.
     *
     * @param payload the request payload
     * @return the request creator
     */
    public static RequestCreator withContent(Source content) {
    	return withPayload(content);
    }
    /**
     * Create a request with the given {@link Source} XML as payload.
     *
     * @param payload the request payload
     * @return the request creator
     */
    public static RequestCreator withContent(String contentResource) {
    	return withPayload(fromResource(contentResource));
    }
    
    public static ResponseMatcher message(String messageResource)
    {
    	return payload(fromResource(messageResource));
    }
}
