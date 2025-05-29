package br.com.shorten_url.infra.controllers;

import br.com.shorten_url.infra.exceptions.NotFoundException;
import br.com.shorten_url.model.Shorten;
import br.com.shorten_url.services.ShortenService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static br.com.shorten_url.infra.exceptions.ExceptionMessages.SHORT_CODE_NOT_FOUND;

@RestController
public class RedirectController {

    private final ShortenService shortenService;

    @Autowired
    public RedirectController(ShortenService shortenService) {
        this.shortenService = shortenService;
    }

    @GetMapping("/{shortCode}")
    public void redirect(HttpServletResponse httpServletResponse,
                         @PathVariable String shortCode) {

        Shorten shorten = this.shortenService.get(shortCode)
                .orElseThrow(() -> new NotFoundException(SHORT_CODE_NOT_FOUND));

        httpServletResponse.setHeader("Location", shorten.getUrl());

        httpServletResponse.setStatus(302);
    }
}
