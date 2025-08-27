package org.CarameloArea.HotPotatoGame.domain.exception;

import org.CarameloArea.HotPotatoGame.infrastructure.adapter.persistence.entity.PlayerEntity;
import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.exception.UnauthorizedAlertException;

import static org.CarameloArea.HotPotatoGame.util.EntityUtils.getBaseNameFromEntity;

public class InvalidCredentialsException extends UnauthorizedAlertException {

    public static final String ENTITY_NAME = getBaseNameFromEntity(PlayerEntity.class.getName());

    public InvalidCredentialsException() {
        super("Invalid credentials", ENTITY_NAME, "invalid-credentials");
    }
}