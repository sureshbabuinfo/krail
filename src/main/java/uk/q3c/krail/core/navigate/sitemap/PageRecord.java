/*
 * Copyright (c) 2014 David Sowerby
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
 * the specific language governing permissions and limitations under the License.
 */

package uk.q3c.krail.core.navigate.sitemap;

public class PageRecord {
    private String StandardPageKeyName;
    private String labelKeyName;
    private String segment;
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getStandardPageKeyName() {
        return StandardPageKeyName;
    }

    public void setStandardPageKeyName(String standardPageKeyName) {
        StandardPageKeyName = standardPageKeyName;
    }

    public String getLabelKeyName() {
        return labelKeyName;
    }

    public void setLabelKeyName(String labelKeyName) {
        this.labelKeyName = labelKeyName;
    }
}