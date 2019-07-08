package org.keycloak.examples.authenticator.spi;

import org.keycloak.provider.Provider;

import java.util.Map;

public interface SmsService<T> extends Provider {
    public void send(String phoneNumber, Map<String, ? super T> params);
}
