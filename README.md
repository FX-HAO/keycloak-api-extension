# keycloak-api-extension

This provides you many APIs as follows:

- validate-otp-api

## Build

To install the library one has to:

* Build and package the project:
  * `$ mvn package`

* Add the jar to the Keycloak server:
  * `$ cp target/keycloak-api-extension-*.jar _KEYCLOAK_HOME_/providers/`

## deploy modules

```
> $KEYCLOAK_HOME/bin/jboss-cli.sh --command="module add --name=com.hfx.keycloak-api-extension --resources=target/keycloak-api-extension-1.0.0-SNAPSHOT.jar --dependencies=org.keycloak.keycloak-core,org.keycloak.keycloak-common,org.hibernate,org.keycloak.keycloak-server-spi,org.keycloak.keycloak-server-spi-private,org.keycloak.keycloak-services,org.jboss.logging,javax.api,javax.ws.rs.api,org.jboss.resteasy.resteasy-jaxrs,org.apache.httpcomponents,org.apache.commons.lang"
> $KEYCLOAK_HOME/bin/jboss-cli.sh --file=cli/keycloak-api-extension-config.cli
```

## validate-otp-api

Validate OTP

`POST /{realm}/keycloak-api-extension/users/{id}/otp`

Parameter

| Type | Name | Description | Schema |
|---|---|---|---|
| Path | id (_required_) | User id | string |
| Path | realm (_required_) | realm name (not id!) | string |
| Body | otp (_required_) | otp | string |

Responses

| HTTP Code | Description | Schema |
|---|---|---|
| 204 | success | No Content |
