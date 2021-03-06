/*
 * Copyright (c) 2015. David Sowerby
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package uk.q3c.krail.core.shiro;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorHandler;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.annotation.RequiresUser;

public class ShiroVaadinModule extends AbstractModule {

    public ShiroVaadinModule() {
        super();
    }

    @Override
    protected void configure() {
        bindErrorHandler();
        bindUnauthenticatedHandler();
        bindUnauthorisedHandler();
        bindLoginExceptionsHandler();
        bindNotAUserHandler();
        bindNotAGuestHandler();

    }

    protected void bindNotAGuestHandler() {
        bind(NotAGuestExceptionHandler.class).to(DefaultNotAGuestExceptionHandler.class);
    }

    /**
     * the {@link DefaultErrorHandler} calls this handler in response to an attempted a failed {@link RequiresUser} check.  If you have
     * defined your own ErrorHandler you may of course do something different
     */
    protected void bindNotAUserHandler() {
        bind(NotAUserExceptionHandler.class).to(DefaultNotAUserExceptionHandler.class);
    }


    /**
     * error handler for the VaadinSession, handles Krail (and therefore Shiro) exceptions
     */
    protected void bindErrorHandler() {
        bind(ErrorHandler.class).to(KrailErrorHandler.class);
    }

    /**
     * the {@link DefaultErrorHandler} calls this handler in response to an attempted unauthorised action. If you have
     * defined your own ErrorHandler you may of course do something different
     */
    protected void bindUnauthorisedHandler() {
        bind(UnauthorizedExceptionHandler.class).to(DefaultUnauthorizedExceptionHandler.class);
    }

    /**
     * the {@link DefaultErrorHandler} calls this handler in response to an attempted unauthenticated action. If you
     * have defined your own ErrorHandler you may of course do something different
     */
    protected void bindUnauthenticatedHandler() {
        bind(UnauthenticatedExceptionHandler.class).to(DefaultUnauthenticatedExceptionHandler.class);
    }

    /**
     * The login process may raise a number of {@link ShiroException}s. This handler is called to manage those
     * exceptions gracefully.
     */
    protected void bindLoginExceptionsHandler() {
        bind(LoginExceptionHandler.class).to(DefaultLoginExceptionHandler.class);
    }

    @Provides
    KrailSecurityManager providesSecurityManager() {
        return (KrailSecurityManager) SecurityUtils.getSecurityManager();
    }

}
