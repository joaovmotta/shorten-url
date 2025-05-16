package br.com.shorten_url.infra.controllers;

import br.com.shorten_url.services.ShortenService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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


        httpServletResponse.setHeader("Location", this.shortenService.get(shortCode).getUrl());

        httpServletResponse.setStatus(302);
    }
}
