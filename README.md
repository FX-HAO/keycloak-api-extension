# keycloak-phone-authenticator

To install the SMS Authenticator one has to:

* Build and package the project:
  * `$ mvn package`

* Add the jar to the Keycloak server:
  * `$ cp target/keycloak-phone-authenticator-*.jar _KEYCLOAK_HOME_/providers/`

## Configuration

Configure your REALM to use the phone number and verification code Authentication.
First create a new REALM (or select a previously created REALM).

Under Authentication > Flows:
* Copy the 'Direct Grant' flow to 'Direct grant with phone' flow
* Click on 'Actions > Add execution' on the 'Provide Phone Number' line
* Click on 'Actions > Add execution' on the 'Provide Verification Code' line
* Set both of 'Provide Phone Number' and 'Provide Verificaition Code' to 'REQUIRED'
