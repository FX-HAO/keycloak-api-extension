package com.hfx.keycloak.api;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.credential.OTPCredentialProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.services.resources.admin.AdminEventBuilder;
import org.keycloak.services.resources.admin.permissions.AdminPermissionEvaluator;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

public class UserExtensionResource {

    protected RealmModel realm;

    private AdminPermissionEvaluator auth;

    private AdminEventBuilder adminEvent;

    private UserModel user;

    @Context
    protected KeycloakSession session;

    public UserExtensionResource(RealmModel realm, UserModel user, AdminPermissionEvaluator auth, AdminEventBuilder adminEvent) {
        this.realm = realm;
        this.user = user;
        this.auth = auth;
        this.adminEvent = adminEvent;
    }

    @POST
    @Path("otp")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public void getVerificationCodes(@FormParam("otp") String otp) {
        if (!user.isEnabled()) {
            throw new UnauthorizedException("Invalid OTP");
        }

        String credentialId = getCredentialProvider(session).getDefaultCredential(session, realm, user).getId();
        boolean valid = getCredentialProvider(session).isValid(realm, user,
                new UserCredentialModel(credentialId, getCredentialProvider(session).getType(), otp));
        if (!valid) {
            throw new UnauthorizedException("Invalid OTP");
        }
        return;
    }

    public OTPCredentialProvider getCredentialProvider(KeycloakSession session) {
        return (OTPCredentialProvider)session.getProvider(CredentialProvider.class, "keycloak-otp");
    }
}
