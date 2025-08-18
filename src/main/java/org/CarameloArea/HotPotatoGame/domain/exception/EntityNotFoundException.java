package org.CarameloArea.HotPotatoGame.domain.exception;

import org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.exception.BadRequestAlertException;

public class EntityNotFoundException extends BadRequestAlertException {

    public EntityNotFoundException(String entity) {
        super(entity + " not found.", entity, "entity-not-found");
    }
}
