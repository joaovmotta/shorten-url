package br.com.shorten_url.infra.controllers;

import br.com.shorten_url.model.Shorten;
import br.com.shorten_url.model.requests.ShortenUrlRequest;
import br.com.shorten_url.services.ShortenService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shorten")
public class ShortenController {

    private final ShortenService shortenService;

    @Autowired
    public ShortenController(ShortenService shortenService) {
        this.shortenService = shortenService;
    }

    @PostMapping
    public ResponseEntity<?> shorten(@RequestBody ShortenUrlRequest shortenUrlRequest) {

        Shorten shorten = new Shorten(shortenUrlRequest);

        return ResponseEntity.ok(shortenService.save(shorten));
    }
}
