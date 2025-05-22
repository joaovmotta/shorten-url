package br.com.shorten_url.services;

import br.com.shorten_url.infra.exceptions.NotFoundException;
import br.com.shorten_url.model.Shorten;
import br.com.shorten_url.model.responses.ShortUrlResponse;
import br.com.shorten_url.repositories.ShortenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Optional;

import static br.com.shorten_url.infra.exceptions.ExceptionMessages.SHORT_CODE_NOT_FOUND;

@Service
public class ShortenService {

    @Value("${url.ttl}")
    private Integer ttl;

    @Value("${app.base-url}")
    private String baseUrl;

    private final ShortenRepository repository;

    @Autowired
    public ShortenService(ShortenRepository repository) {
        this.repository = repository;
    }

    public ShortUrlResponse save(Shorten shorten) {

        String shortCode = this.shorten(shorten.getUrl());

        shorten.setShortCode(shortCode);
        shorten.setTtl(buildTTL());
        shorten.setCreationDate(ZonedDateTime.now());
        shorten.setUrl(buildUrl(shorten.getUrl()));

        repository.save(shorten);

        shorten = this.get(shortCode);

        String shortUrl = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment(shorten.getShortCode())
                .toUriString();

        return new ShortUrlResponse(shorten.getUrl(), shortUrl, shorten.getCreationDate());
    }

    private String buildUrl(String url){

        if(url.startsWith("http://") || url.startsWith("https://")){
            return url;
        }

        else return "http://" + url;
    }

    public Shorten get(String shortCode) {

        return Optional.ofNullable(repository.get(shortCode))
                .orElseThrow(() -> new NotFoundException(SHORT_CODE_NOT_FOUND));
    }

    private Long buildTTL() {

        return Instant.now()
                .plus(Duration.ofSeconds(ttl))
                .getEpochSecond();
    }

    private String shorten(String url) {

        MessageDigest digest;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] hashBytes = digest.digest(url.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(hashBytes).substring(0, 10);
    }

    private static String bytesToHex(byte[] bytes) {

        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
