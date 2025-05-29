package br.com.shorten_url.services;

import br.com.shorten_url.infra.exceptions.BadRequestException;
import br.com.shorten_url.infra.exceptions.NotFoundException;
import br.com.shorten_url.model.Shorten;
import br.com.shorten_url.model.requests.ShortenUrlRequest;
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

import static br.com.shorten_url.infra.exceptions.ExceptionMessages.CUSTOM_CODE_ALREADY_IN_USE;
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

    public ShortUrlResponse save(ShortenUrlRequest shortenUrlRequest) {

        String shortCode = this.getShortCode(shortenUrlRequest);

        Shorten shorten = new Shorten();

        shorten.setShortCode(shortCode);
        shorten.setTtl(buildTTL());
        shorten.setCreationDate(ZonedDateTime.now());
        shorten.setUrl(buildUrl(shortenUrlRequest.url()));

        repository.save(shorten);

        shorten = this.get(shortCode)
                .orElseThrow(() -> new NotFoundException(SHORT_CODE_NOT_FOUND));

        String shortUrl = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment(shorten.getShortCode())
                .toUriString();

        return new ShortUrlResponse(shorten.getUrl(), shortUrl, shorten.getCreationDate());
    }

    private String getShortCode(ShortenUrlRequest urlRequest) {

        if (urlRequest.custom() != null && this.get(urlRequest.custom()).isEmpty()) {

            return urlRequest.custom();
        }else if (urlRequest.custom() != null && this.get(urlRequest.custom()).isPresent()) {

            throw new BadRequestException(CUSTOM_CODE_ALREADY_IN_USE);
        }

        return this.shorten(urlRequest.url());
    }

    private String buildUrl(String url) {

        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        } else return "http://" + url;
    }

    public Optional<Shorten> get(String shortCode) {

        return Optional.ofNullable(repository.get(shortCode));
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
