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

public enum AuthnLevel {
    STORK4(4, "STORK-QAA-Level-4"),
    STORK3(3, "STORK-QAA-Level-3"),
    STORK2(2, "STORK-QAA-Level-2"),
    STORK1(1, "STORK-QAA-Level-1"),
    ;

    private final int level;
    private final String fullname;

    private AuthnLevel(int level, String fullname) {
        this.level = level;
        this.fullname = fullname;
    }

    public int getLevel() {
        return level;
    }

    public String getFullname() {
        return this.fullname;
    }

    public static AuthnLevel fromAkdbTrustlevel(String akdbTrustLevel) {
        return switch (akdbTrustLevel) {
            case "UNTERGEORDNET" -> AuthnLevel.STORK1;
            case "NORMAL" -> AuthnLevel.STORK2;
            case "SUBSTANTIELL" -> AuthnLevel.STORK3;
            case "HOCH" -> AuthnLevel.STORK4;
            default -> null;
        };
    }

    public static AuthnLevel fromLoA(int loa) {
        if (loa > 4) {
            return STORK4;
        }

        for (final AuthnLevel al : AuthnLevel.values()) {
            if (al.getLevel() == loa) {
                return al;
            }
        }

        return STORK1;
    }
}
