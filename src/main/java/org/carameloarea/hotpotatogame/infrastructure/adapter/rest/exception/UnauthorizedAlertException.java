package org.carameloarea.hotpotatogame.infrastructure.adapter.rest.exception;

import org.zalando.problem.Status;

@SuppressWarnings("java:S110")
public class UnauthorizedAlertException extends AlertException {

    public UnauthorizedAlertException(String defaultMessage, String entityName, String errorKey) {
        super(Status.UNAUTHORIZED, defaultMessage, entityName, errorKey);
    }
}