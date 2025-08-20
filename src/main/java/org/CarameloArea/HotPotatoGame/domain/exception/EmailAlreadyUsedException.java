package org.CarameloArea.HotPotatoGame.domain.exception;

import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.exception.ConflictAlertException;

public class EmailAlreadyUsedException extends ConflictAlertException {

    public static final String ENTITY_NAME = PlayerEntity.class.getName();

    public EmailAlreadyUsedException() {
        super("Email is already in use!", ENTITY_NAME, "email-exists");
    }
}
