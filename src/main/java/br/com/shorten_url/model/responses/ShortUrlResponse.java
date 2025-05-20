package br.com.shorten_url.model.responses;

import java.time.ZonedDateTime;

public record ShortUrlResponse(String url, String shortUrl, ZonedDateTime creationDate) {
}
