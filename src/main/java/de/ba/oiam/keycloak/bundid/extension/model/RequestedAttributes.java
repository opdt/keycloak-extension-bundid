/*
 * Copyright 2024. IT-Systemhaus der Bundesagentur fuer Arbeit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.ba.oiam.keycloak.bundid.extension.model;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class RequestedAttributes {

    @XmlElement(name = "RequestedAttribute", namespace = "https://www.akdb.de/request/2018/09")
    private List<RequestedAttribute> requestedAttributes = new ArrayList<>();

    // getters and setters

    public List<RequestedAttribute> getRequestedAttributes() {
        return requestedAttributes;
    }

    public void setRequestedAttributes(List<RequestedAttribute> requestedAttributes) {
        this.requestedAttributes = requestedAttributes;
    }
}
