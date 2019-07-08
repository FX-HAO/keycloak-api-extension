package org.keycloak.examples.authenticator.spi.impl;

import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.examples.authenticator.VerificationCodeRepresentation;
import org.keycloak.examples.authenticator.jpa.VerificationCode;
import org.keycloak.examples.authenticator.spi.VerificationCodeService;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.utils.KeycloakModelUtils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final KeycloakSession session;

    public VerificationCodeServiceImpl(KeycloakSession session) {
        this.session = session;
        if (getRealm() == null) {
            throw new IllegalStateException("The service cannot accept a session without a realm in its context.");
        }
    }

    private EntityManager getEntityManager() {
        return session.getProvider(JpaConnectionProvider.class).getEntityManager();
    }

    protected RealmModel getRealm() {
        return session.getContext().getRealm();
    }

    @Override
    public List<VerificationCodeRepresentation> listVerificationCodes() {
        List<VerificationCode> verificationCodesEntities = getEntityManager().createNamedQuery("VerificationCode.findByRealm", VerificationCode.class)
                .setParameter("realmId", getRealm().getId())
                .getResultList();
        List<VerificationCodeRepresentation> result = new LinkedList<>();
        for (VerificationCode entity : verificationCodesEntities) {
            result.add(new VerificationCodeRepresentation(entity));
        }
        return result;
    }

    protected static String getCode() {
        // It will generate 4 digit random Number.
        // from 0 to 9999
        Random rnd = new Random();
        int number = rnd.nextInt(9999);

        // this will convert any number sequence into 4 character.
        return String.format("%04d", number);
    }

    @Override
    public VerificationCodeRepresentation addVerificationCode(VerificationCodeRepresentation veriCode) {
        VerificationCode entity = new VerificationCode();
        String id = veriCode.getId() == null ? KeycloakModelUtils.generateId() : veriCode.getId();
        entity.setId(id);
        entity.setPhoneNumber(veriCode.getPhoneNumber());
        entity.setCode(getCode());
        entity.setRealmId(getRealm().getId());
        Instant now = Instant.now();
        entity.setCreatedAt(Date.from(now));
        entity.setExpiresAt(Date.from(now.plusSeconds(60 * 5)));
        getEntityManager().persist(entity);

        return new VerificationCodeRepresentation((entity));
    }

    @Override
    public void close() {
    }
}
