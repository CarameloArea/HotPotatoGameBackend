package org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.exception;

import org.zalando.problem.Status;

public class BadRequestAlertException extends AlertException {

    public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
        super(Status.BAD_REQUEST, defaultMessage, entityName, errorKey);
    }
}
