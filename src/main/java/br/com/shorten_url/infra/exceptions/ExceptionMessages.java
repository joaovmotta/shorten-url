package br.com.shorten_url.infra.exceptions;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public enum ExceptionMessages {

    SHORT_CODE_NOT_FOUND(404001, "Short code not found.", NOT_FOUND),

    CUSTOM_CODE_ALREADY_IN_USE(400001, "Custom url already in use.", HttpStatus.BAD_REQUEST);

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;

    ExceptionMessages(Integer code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
