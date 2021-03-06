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

package uk.q3c.krail.core.persist.inmemory.option;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.mycila.testing.junit.MycilaJunitRunner;
import com.mycila.testing.plugin.guice.GuiceContext;
import com.mycila.testing.plugin.guice.ModuleProvider;
import com.vaadin.data.Container;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.q3c.krail.core.guice.vsscope.VaadinSessionScopeModule;
import uk.q3c.krail.core.option.InMemory;
import uk.q3c.krail.core.persist.common.common.ContainerType;
import uk.q3c.krail.core.persist.common.common.VaadinContainerProvider;
import uk.q3c.krail.core.persist.common.option.OptionEntity;
import uk.q3c.krail.core.persist.inmemory.common.InMemoryModule;
import uk.q3c.krail.testutil.option.TestOptionModule;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MycilaJunitRunner.class)
@GuiceContext({TestOptionModule.class, VaadinSessionScopeModule.class})
public class InMemoryContainerProviderTest_OptionOnly {

    @Inject
    @InMemory
    VaadinContainerProvider inMemoryContainerProvider;

    /**
     * This will fail if the INMemoryModule is not set to provide either the OptionDaoDelegate, PatternDao or both
     */
    @Test
    public void name() {
        //given

        //when
        Container container = inMemoryContainerProvider.get(OptionEntity.class, ContainerType.CACHED);
        //then
        assertThat(container).isNotNull();
    }

    @ModuleProvider
    protected AbstractModule moduleProvider() {
        return new InMemoryModule().provideOptionDao();
    }
}