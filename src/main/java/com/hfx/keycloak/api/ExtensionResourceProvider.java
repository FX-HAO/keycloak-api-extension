package com.hfx.keycloak.api;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class ExtensionResourceProvider implements RealmResourceProvider {

    private final KeycloakSession session;

    public ExtensionResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return new ExtensionResource(session);
    }

    @Override
    public void close() {
    }

}
