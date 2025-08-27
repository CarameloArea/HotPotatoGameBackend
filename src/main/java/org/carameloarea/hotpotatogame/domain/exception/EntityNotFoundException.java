package org.carameloarea.hotpotatogame.domain.exception;

import org.carameloarea.hotpotatogame.infrastructure.adapter.rest.exception.NotFoundAlertException;

@SuppressWarnings("java:S110")
public class EntityNotFoundException extends NotFoundAlertException {

    public EntityNotFoundException(String entity) {
        super(entity + " not found.", entity, "entity-not-found");
    }
}
