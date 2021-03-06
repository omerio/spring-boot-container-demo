/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.example.controller;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
@Scope("session")
public class HelloWorld implements Serializable {
    private Integer count = 0;

    @RequestMapping(value="/helloworld", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody HelloWorldResponse helloworld() {
        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostname = "unknown";
        }
        HelloWorldResponse response = new HelloWorldResponse();
        response.hostname = hostname;
        response.sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        response.count = count++;

        return response;
    }

    @RequestMapping("/version")
    public String version() {
        return "1.0";
    }

    public static class HelloWorldResponse {
        String hostname;
        String sessionId;
        int count;

        public String getHostname() {
            return hostname;
        }
        public void setHostname(String hostname) {
            this.hostname = hostname;
        }
        public String getSessionId() {
            return sessionId;
        }
        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
        public int getCount() {
            return count;
        }
        public void setCount(int count) {
            this.count = count;
        }
    }
}
