/*
 * Copyright (C) 2013 David Sowerby
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package uk.q3c.util;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.q3c.krail.core.services.DefaultServicesMonitor;
import uk.q3c.krail.core.services.Service;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Generates AOP code for a Service implementation but does nothing useful
 *
 * @author David Sowerby
 */
public class AOPTestModule extends AbstractModule {

    private static final Logger log = LoggerFactory.getLogger(AOPTestModule.class);


    @Override
    protected void configure() {


        bindListener(new ServiceInterfaceMatcher(), new ServicesListener());
        bindInterceptor(Matchers.subclassesOf(Service.class), new FinalizeMethodMatcher(), new FinalizeMethodInterceptor());

    }


    /**
     * This listener is constructed using the {@link Service} interface to identify service implementation instances..
     * All instances of {@link Service} implementations are registered with the {@link DefaultServicesMonitor}
     *
     * @author David Sowerby
     */
    public class ServicesListener implements TypeListener {

        public ServicesListener() {
        }

        @Override
        public <I> void hear(final TypeLiteral<I> type, TypeEncounter<I> encounter) {
            InjectionListener<Object> listener = new InjectionListener<Object>() {
                @Override
                public void afterInjection(Object injectee) {
                    //this appears not to do anything but it forces construction  of the monitor
                    //before service.init publishes a bus message
                    // cast is safe - if not, the matcher is wrong
                    Service service = (Service) injectee;

                }
            };
            encounter.register(listener);
        }

    }

    private class FinalizeMethodMatcher extends AbstractMatcher<Method> {
        @Override
        public boolean matches(Method method) {
            return method.getName()
                         .equals("finalize");
        }
    }

    /**
     * Calls {@link Service#stop} before passing on the finalize() call
     */
    private class FinalizeMethodInterceptor implements MethodInterceptor {

        public FinalizeMethodInterceptor() {
        }

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            Service service = (Service) invocation.getThis();
            service.stop();
            return invocation.proceed();
        }
    }

    /**
     * Matches classes implementing {@link Service}
     */
    private class ServiceInterfaceMatcher extends AbstractMatcher<TypeLiteral<?>> {
        @Override
        public boolean matches(TypeLiteral<?> t) {
            Class<?> rawType = t.getRawType();
            Set<Class<?>> interfaces = ReflectionUtils.allInterfaces(rawType);

            for (Class<?> intf : interfaces) {
                if (intf.equals(Service.class)) {
                    return true;
                }
            }
            return false;
        }
    }

}