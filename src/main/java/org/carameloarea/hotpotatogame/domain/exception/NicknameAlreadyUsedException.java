package org.carameloarea.hotpotatogame.domain.exception;

import org.carameloarea.hotpotatogame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.carameloarea.hotpotatogame.infrastructure.adapter.rest.exception.ConflictAlertException;

import static org.carameloarea.hotpotatogame.util.EntityUtils.getBaseNameFromEntity;

@SuppressWarnings("java:S110")
public class NicknameAlreadyUsedException extends ConflictAlertException {

    public static final String ENTITY_NAME = getBaseNameFromEntity(PlayerEntity.class.getName());

    public NicknameAlreadyUsedException() {
        super("Nickname is already in use!", ENTITY_NAME, "nickname-exists");
    }
}