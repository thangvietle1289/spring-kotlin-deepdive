/*
 * Copyright 2002-2017 the original author or authors.
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
package io.spring.deepdive

import io.spring.deepdive.model.User
import org.junit.Test
import org.junit.runner.RunWith

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.test.context.junit4.SpringRunner

import org.assertj.core.api.Assertions.assertThat

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiTests {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate


    @Test
    fun `Assert FindAll JSON API is parsed correctly and contains 11 elements`() {
        val users = restTemplate.exchange("/api/user/", HttpMethod.GET, null, object: ParameterizedTypeReference<List<User>>() {}).body
        assertThat(users).hasSize(11)
    }

    @Test
    fun `verify findOne JSON API`() {
        val user = this.restTemplate.getForObject("/api/user/MkHeck", User::class.java)
        assertThat(user.login).isEqualTo("MkHeck")
        assertThat(user.firstname).isEqualTo("Mark")
        assertThat(user.lastname).isEqualTo("Heckler")
        assertThat(user.description).startsWith("Spring Developer Advocate")
    }

}