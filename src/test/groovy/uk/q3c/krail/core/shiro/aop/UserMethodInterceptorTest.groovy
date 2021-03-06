/*
 *
 *  * Copyright (c) 2016. David Sowerby
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 *  * the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 *  * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *  * specific language governing permissions and limitations under the License.
 *
 */

package uk.q3c.krail.core.shiro.aop

import com.google.inject.Provider
import org.apache.shiro.authz.annotation.RequiresUser
import org.apache.shiro.subject.Subject
import spock.lang.Specification
import uk.q3c.krail.core.shiro.SubjectProvider

/**
 *
 *
 *
 * Created by David Sowerby on 19 Jan 2016
 */

class UserMethodInterceptorTest extends Specification {

    UserMethodInterceptor interceptor

    SubjectProvider subjectProvider = Mock()
    Provider<SubjectProvider> subjectProviderProvider = Mock()
    Subject subject1 = Mock()
    Provider<AnnotationResolver> annotationResolverProvider = Mock()


    def setup() {
        subjectProviderProvider.get() >> subjectProvider
        interceptor = new UserMethodInterceptor(subjectProviderProvider, annotationResolverProvider)
    }


    def "requires guest status, and is user"() {
        given:
        RequiresUser annotation = new InterceptorTestClass().getAnnotation('requiresUser')
        subjectProvider.get() >> subject1
        subject1.getPrincipal() >> "anything"

        when:
        interceptor.assertAuthorized(annotation)

        then:
        noExceptionThrown()
    }

    def "requires guest status, and is not user"() {
        given:
        RequiresUser annotation = new InterceptorTestClass().getAnnotation('requiresUser')
        subjectProvider.get() >> subject1
        subject1.getPrincipal() >> null

        when:
        interceptor.assertAuthorized(annotation)

        then:
        thrown(RuntimeException)
    }

}
