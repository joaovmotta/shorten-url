package br.com.shorten_url.infra.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpException{

    private static final long serialVersionUID = 1L;

    public BadRequestException(final ExceptionMessages exceptionMessagesEnum) {
        super(exceptionMessagesEnum.getCode(), exceptionMessagesEnum.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
