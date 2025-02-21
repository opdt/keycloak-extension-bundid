FROM jelastic/maven:3.9.5-openjdk-21 AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install -U


FROM quay.io/keycloak/keycloak AS keycloak

COPY --from=build /app/target/keycloak-extension-bundid-2.2.1-26.1-SNAPSHOT.jar /opt/keycloak/providers/keycloak-extension-bundid-2.2.1-26.1-SNAPSHOT.jar

