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
package com.google.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.google.example")
public class Application {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        String gaeEnvironment = System.getenv("GAE_PARTITION");
        String dockerEnvironment = System.getenv("REDIS_NAME");

        if (gaeEnvironment != null) {
            if (!"dev".equals(gaeEnvironment)) {
                System.out.println("Enabling GAE, replication");
                app.setAdditionalProfiles("GAE", "replication");
            }
        } else if (dockerEnvironment != null) {
            System.out.println("Enabling docker, replication");
            app.setAdditionalProfiles("docker", "replication");
        } else {
            System.out.println("No replication");
        }
        app.run(args);
    }
}
