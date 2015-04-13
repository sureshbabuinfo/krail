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

package uk.q3c.krail.core.navigate;

import uk.q3c.krail.core.view.KrailView;

/**
 * A rule which is applied before a request change to navigation state (that is, a change of view) is made.
 * <p>
 * Created by David Sowerby on 14/03/15.
 */
public interface ViewChangeRule {

    /**
     * Test for whether a change of view should be allowed.
     *
     * @param navigator
     *         the navigator, useful for checking things like current navigation state
     * @param currentView
     *         the current view - useful for checking whether the view is dirty
     *
     * @return false if the change should be blocked (and the current navigation state retained), or true to allow the change
     */
    boolean changeIsAllowed(Navigator navigator, KrailView currentView);
}