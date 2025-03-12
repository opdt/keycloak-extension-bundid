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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DisplayInformationVersion {

    @XmlElement(name = "OrganizationDisplayName", namespace = "https://www.akdb.de/request/2018/09/classic-ui/v1")
    private DisplayInformationValue organizationDisplayName;

    @XmlElement(name = "OnlineServiceId", namespace = "https://www.akdb.de/request/2018/09/classic-ui/v1")
    private DisplayInformationValue onlineServiceId;

    public void setOnlineServiceId(DisplayInformationValue onlineServiceId) {
        this.onlineServiceId = onlineServiceId;
    }

    public void setOrganizationDisplayName(DisplayInformationValue organizationDisplayName) {
        this.organizationDisplayName = organizationDisplayName;
    }

    public DisplayInformationValue getOnlineServiceId() {
        return onlineServiceId;
    }

    public DisplayInformationValue getOrganizationDisplayName() {
        return organizationDisplayName;
    }
}
