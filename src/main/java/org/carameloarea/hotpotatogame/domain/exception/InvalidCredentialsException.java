package org.carameloarea.hotpotatogame.domain.exception;

import org.carameloarea.hotpotatogame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.carameloarea.hotpotatogame.infrastructure.adapter.rest.exception.UnauthorizedAlertException;

import static org.carameloarea.hotpotatogame.util.EntityUtils.getBaseNameFromEntity;

public class InvalidCredentialsException extends UnauthorizedAlertException {

    public static final String ENTITY_NAME = getBaseNameFromEntity(PlayerEntity.class.getName());

    public InvalidCredentialsException() {
        super("Invalid credentials", ENTITY_NAME, "invalid-credentials");
    }
}