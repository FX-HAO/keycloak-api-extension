package org.keycloak.examples.authenticator.rest;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.examples.authenticator.VerificationCodeRepresentation;
import org.keycloak.examples.authenticator.spi.VerificationCodeService;
import org.keycloak.models.KeycloakSession;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class VerificationCodeResource {
    private final KeycloakSession session;

    public VerificationCodeResource(KeycloakSession session) {
        this.session = session;
    }

    @GET
    @Path("")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public List<VerificationCodeRepresentation> getVerificationCodes() {
        return session.getProvider(VerificationCodeService.class).listVerificationCodes();
    }

    @POST
    @Path("")
    @NoCache
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createVerificationCode(VerificationCodeRepresentation rep) {
        session.getProvider(VerificationCodeService.class).addVerificationCode(rep);
        return Response.noContent().build();
    }
}
