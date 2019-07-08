package org.keycloak.examples.authenticator.spi;

import org.keycloak.examples.authenticator.VerificationCodeRepresentation;
import org.keycloak.provider.Provider;

import java.util.List;

public interface VerificationCodeService extends Provider {
    List<VerificationCodeRepresentation> listVerificationCodes();

    VerificationCodeRepresentation addVerificationCode(VerificationCodeRepresentation veriCode);
}
