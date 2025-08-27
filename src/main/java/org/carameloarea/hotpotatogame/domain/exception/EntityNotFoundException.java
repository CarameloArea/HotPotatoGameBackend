package org.carameloarea.hotpotatogame.domain.exception;

import org.carameloarea.hotpotatogame.infrastructure.adapter.rest.exception.NotFoundAlertException;

public class EntityNotFoundException extends NotFoundAlertException {

    public EntityNotFoundException(String entity) {
        super(entity + " not found.", entity, "entity-not-found");
    }
}
