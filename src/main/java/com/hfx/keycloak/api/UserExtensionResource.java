package com.hfx.keycloak.api;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.keycloak.credential.CredentialProvider;
import org.keycloak.credential.OTPCredentialProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.OTPCredentialModel;
import org.keycloak.services.ErrorResponse;
import org.keycloak.services.resources.admin.AdminEventBuilder;
import org.keycloak.services.resources.admin.permissions.AdminPermissionEvaluator;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    @Path("validate-otp")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public void validateOTP(@FormParam("otp") String otp) {
        if (!user.isEnabled()) {
            throw new UnauthorizedException("Invalid OTP");
        }

        OTPCredentialModel credential = getCredentialProvider(session).getDefaultCredential(session, realm, user);
        if (credential == null) {
            throw new UnauthorizedException("Invalid OTP");
        }
        String credentialId = credential.getId();
        boolean valid = getCredentialProvider(session).isValid(realm, user,
                new UserCredentialModel(credentialId, getCredentialProvider(session).getType(), otp));
        if (!valid) {
            throw new UnauthorizedException("Invalid OTP");
        }
        return;
    }

    @GET
    @Path("if-otp-exists")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public Response ifOTPExists() {
        OTPCredentialModel credential = getCredentialProvider(session).getDefaultCredential(session, realm, user);
        if (!user.isEnabled() || credential == null) {
            return ErrorResponse.exists("OTP does not exist!");
        }
        return Response.noContent().build();
    }

    public OTPCredentialProvider getCredentialProvider(KeycloakSession session) {
        return (OTPCredentialProvider)session.getProvider(CredentialProvider.class, "keycloak-otp");
    }
}
