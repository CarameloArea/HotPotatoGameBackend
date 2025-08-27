package org.CarameloArea.HotPotatoGame.domain.exception;

import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.exception.NotFoundAlertException;

public class EntityNotFoundException extends NotFoundAlertException {

    public EntityNotFoundException(String entity) {
        super(entity + " not found.", entity, "entity-not-found");
    }
}
