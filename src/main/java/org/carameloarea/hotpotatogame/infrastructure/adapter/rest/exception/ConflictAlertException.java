package org.carameloarea.hotpotatogame.infrastructure.adapter.rest.exception;

import org.zalando.problem.Status;

public class ConflictAlertException extends AlertException {

    public ConflictAlertException(String defaultMessage, String entityName, String errorKey) {
        super(Status.CONFLICT, defaultMessage, entityName, errorKey);
    }
}
