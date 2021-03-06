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
package uk.q3c.krail.core.shiro;

import com.mycila.testing.junit.MycilaJunitRunner;
import com.mycila.testing.plugin.guice.GuiceContext;
import com.vaadin.ui.Notification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import uk.q3c.krail.core.i18n.DescriptionKey;
import uk.q3c.krail.core.user.notify.UserNotifier;

import static org.mockito.Mockito.verify;

@RunWith(MycilaJunitRunner.class)
@GuiceContext({})
public class DefaultUnauthorisedExceptionHandlerTest {

    @Mock
    UserNotifier notifier;

    DefaultUnauthorizedExceptionHandler handler;

    @Before
    public void setup() {
        handler = new DefaultUnauthorizedExceptionHandler(notifier);
    }

    @Test
    public void notify_() {

        // given

        // when
        handler.invoke();
        // then
        verify(notifier).notifyError(DescriptionKey.No_Permission, Notification.Type.ERROR_MESSAGE);

    }
}
