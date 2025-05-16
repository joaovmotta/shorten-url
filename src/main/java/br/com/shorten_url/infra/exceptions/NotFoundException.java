package br.com.shorten_url.infra.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException{

    private static final long serialVersionUID = 1L;

    public NotFoundException(final String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(final ExceptionMessages exceptionMessagesEnum) {
        super(exceptionMessagesEnum.getCode(), exceptionMessagesEnum.getMessage(), HttpStatus.NOT_FOUND);
    }
}
