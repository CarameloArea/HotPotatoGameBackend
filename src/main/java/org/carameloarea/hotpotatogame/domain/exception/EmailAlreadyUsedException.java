package org.carameloarea.hotpotatogame.domain.exception;

import org.carameloarea.hotpotatogame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.carameloarea.hotpotatogame.infrastructure.adapter.rest.exception.ConflictAlertException;

import static org.carameloarea.hotpotatogame.util.EntityUtils.getBaseNameFromEntity;

@SuppressWarnings("java:S110")
public class EmailAlreadyUsedException extends ConflictAlertException {

    public static final String ENTITY_NAME = getBaseNameFromEntity(PlayerEntity.class.getName());

    public EmailAlreadyUsedException() {
        super("Email is already in use!", ENTITY_NAME, "email-exists");
    }
}
