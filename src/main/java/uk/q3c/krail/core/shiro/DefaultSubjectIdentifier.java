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

import com.google.inject.Inject;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import uk.q3c.krail.core.i18n.LabelKey;
import uk.q3c.krail.core.i18n.Translate;

/**
 * This default implementation assumes that the Subject's primary principal toString() method will return a username.
 * This is the case for a Subject created under Krail's default conditions. You may of course choose to change those
 * conditions, and you may then need to provide your own SubjectIdentifier implementation.
 *
 * @author David Sowerby 3 Oct 2013
 */
public class DefaultSubjectIdentifier implements SubjectIdentifier {

    private final SubjectProvider subjectProvider;
    private final Translate translate;

    @Inject
    public DefaultSubjectIdentifier(SubjectProvider subjectProvider, Translate translate) {
        super();
        this.subjectProvider = subjectProvider;
        this.translate = translate;

    }

    /**
     * If the Subject has authenticated, returns Subject.getPrincipal.toString()
     * <p>
     * If the Subject is remembered, returns Subject.getPrincipal.toString() with a "?" appended
     * <p>
     * If the Subject is neither authenticated or remembered, returns the I18N translated value of LabelKey.Guest
     * <p>
     *
     * @see uk.q3c.krail.core.shiro.SubjectIdentifier#subjectName()
     */
    @SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
    @Override
    public String subjectName() {
        Subject subject = subjectProvider.get();
        boolean authenticated = subject.isAuthenticated();
        boolean remembered = subject.isRemembered();
        String name = (authenticated) ? subject.getPrincipal()
                                               .toString() : translate.from(LabelKey.Guest);
        return (remembered) ? subject.getPrincipal() + "?" : name;

    }

    @Override
    public Object subjectIdentifier() {
        Subject subject = subjectProvider.get();
        return subject.getPrincipal();
    }

    /**
     * Assumes that the Shiro Principal returns a unique identifier which can return a String value.  If this is not the case, this method will need to be
     * overridden by a method which will provide a String uniquely identifying the user.
     *
     * @return
     */
    @Override
    public String userId() {
        Subject subject = subjectProvider.get();
        if (subject.getPrincipal() == null) {
            return "?";
        }
        String userId = subject.getPrincipal()
                               .toString();
        if (StringUtils.isEmpty(userId)) {
            return userId = "?";
        }
        return userId;
    }

}
