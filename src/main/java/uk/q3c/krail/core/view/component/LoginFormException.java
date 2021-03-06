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

package uk.q3c.krail.core.view.component;

import uk.q3c.krail.core.i18n.I18NKey;

/**
 * Created by David Sowerby on 02/06/15.
 */
public class LoginFormException extends RuntimeException {
    private final I18NKey msgKey;
    private final Object[] params;


    public LoginFormException(I18NKey msgKey, Object... params) {
        this.msgKey = msgKey;
        this.params = params;
    }

    public I18NKey getMsgKey() {
        return msgKey;
    }

    public Object[] getParams() {
        Object[] outArray = new Object[params.length];
        System.arraycopy(params, 0, outArray, 0, params.length);
        return outArray;
    }
}
