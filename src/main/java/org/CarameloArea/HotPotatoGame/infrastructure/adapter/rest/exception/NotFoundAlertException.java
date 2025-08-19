package org.CarameloArea.HotPotatoGame.infrastructure.adapter.rest.exception;

import org.zalando.problem.Status;

public class NotFoundAlertException extends AlertException {

    public NotFoundAlertException(String defaultMessage, String entityName, String errorKey) {
        super(Status.NOT_FOUND, defaultMessage, entityName, errorKey);
    }
}

