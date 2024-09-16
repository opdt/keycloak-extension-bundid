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

package de.ba.oiam.keycloak.bundid;

import org.junit.jupiter.api.Test;
import org.keycloak.dom.saml.v2.protocol.AuthnContextComparisonType;
import org.keycloak.dom.saml.v2.protocol.AuthnRequestType;
import org.keycloak.models.Constants;
import org.keycloak.sessions.AuthenticationSessionModel;
import org.mockito.Answers;
import org.mockito.Mockito;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class SamlAuthenticationPreprocessorTest {

    @Test
    void thatProvidersAreCorrectlyWired() {
        SamlAuthenticationPreprocessorImpl underTest = new SamlAuthenticationPreprocessorImpl();
        AuthnRequestType authnRequest = new AuthnRequestType("myId", null);
        authnRequest.setAssertionConsumerServiceURL(URI.create("http://localhost:8081/bla/broker/bundid/endpoint"));

        AuthenticationSessionModel clientSession = Mockito.mock(AuthenticationSessionModel.class, Answers.RETURNS_DEEP_STUBS);
        when(clientSession.getClientNote(Constants.REQUESTED_LEVEL_OF_AUTHENTICATION)).thenReturn("4");

        AuthnRequestType expectedResult = underTest.beforeSendingLoginRequest(authnRequest, clientSession);

        assertEquals(AuthnContextComparisonType.MINIMUM, expectedResult.getRequestedAuthnContext().getComparison());
        assertEquals(List.of(AuthnLevel.STORK4.getFullname()), expectedResult.getRequestedAuthnContext().getAuthnContextClassRef());
    }

    @Test
    void preprocessorIsSkippedIfIdpDoesntMatch() {
        SamlAuthenticationPreprocessorImpl underTest = new SamlAuthenticationPreprocessorImpl();
        AuthnRequestType authnRequest = new AuthnRequestType("myId", null);
        authnRequest.setAssertionConsumerServiceURL(URI.create("http://localhost:8081/bla/broker/muk/endpoint"));

        AuthenticationSessionModel clientSession = Mockito.mock(AuthenticationSessionModel.class, Answers.RETURNS_DEEP_STUBS);
        when(clientSession.getClientNote(Constants.REQUESTED_LEVEL_OF_AUTHENTICATION)).thenReturn("4");

        AuthnRequestType expectedResult = underTest.beforeSendingLoginRequest(authnRequest, clientSession);

        assertNull(expectedResult.getRequestedAuthnContext());
        assertNull(expectedResult.getExtensions());
    }
}