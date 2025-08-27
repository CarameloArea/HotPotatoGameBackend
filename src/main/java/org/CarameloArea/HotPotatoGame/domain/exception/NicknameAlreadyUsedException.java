package org.CarameloArea.HotPotatoGame.domain.exception;

import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.exception.ConflictAlertException;

public class NicknameAlreadyUsedException extends ConflictAlertException {

    public static final String ENTITY_NAME = PlayerEntity.class.getName();

    public NicknameAlreadyUsedException() {
        super("Nickname is already in use!", ENTITY_NAME, "nickname-exists");
    }
}