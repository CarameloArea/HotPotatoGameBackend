package org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.exception;

import org.zalando.problem.Status;

public class UnauthorizedAlertException extends AlertException {

    public UnauthorizedAlertException(String defaultMessage, String entityName, String errorKey) {
        super(Status.UNAUTHORIZED, defaultMessage, entityName, errorKey);
    }
}